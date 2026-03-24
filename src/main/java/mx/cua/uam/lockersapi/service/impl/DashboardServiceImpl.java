package mx.cua.uam.lockersapi.service.impl;

import mx.cua.uam.lockersapi.dto.DashboardDTO;
import mx.cua.uam.lockersapi.entity.Alumno;
import mx.cua.uam.lockersapi.mapper.DashboardMapper;
import mx.cua.uam.lockersapi.repository.AlumnoRepository;
import mx.cua.uam.lockersapi.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final AlumnoRepository alumnoRepository;
    private final DashboardMapper dashboardMapper;
    private final RestTemplate restTemplate;

    public DashboardServiceImpl(AlumnoRepository alumnoRepository, DashboardMapper dashboardMapper) {
        this.alumnoRepository = alumnoRepository;
        this.dashboardMapper = dashboardMapper;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public DashboardDTO obtenerResumenAlumno(Integer idAlumno) {
        Alumno alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        DashboardDTO dashboard = dashboardMapper.toDTO(alumno);
        dashboard.setLockerAsignado("Locker Asignado (Verificar historial)");

        try {
            ObjectMapper mapper = new ObjectMapper();

            // API 1: Clima con Emojis Dinámicos
            String urlClima = "https://api.open-meteo.com/v1/forecast?latitude=19.3566&longitude=-99.2836&current_weather=true";
            String respuestaClima = restTemplate.getForObject(urlClima, String.class);
            JsonNode nodoClima = mapper.readTree(respuestaClima).path("current_weather");

            double temperatura = nodoClima.path("temperature").asDouble();
            int codigoClima = nodoClima.path("weathercode").asInt();
            dashboard.setClimaCuajimalpa(temperatura + " °C " + obtenerIconoClima(codigoClima));

            // API 2: Próximo Día Festivo en México (Nager.Date API)
            String urlFeriados = "https://date.nager.at/api/v3/NextPublicHolidays/MX";
            String respuestaFeriados = restTemplate.getForObject(urlFeriados, String.class);
            JsonNode nodoFeriados = mapper.readTree(respuestaFeriados);

            if (nodoFeriados.isArray() && !nodoFeriados.isEmpty()) {
                String nombreAsueto = nodoFeriados.get(0).path("localName").asText();
                String fechaAsueto = nodoFeriados.get(0).path("date").asText();
                dashboard.setProximoAsueto(nombreAsueto + " (" + fechaAsueto + ")");
            }

        } catch (Exception e) {
            dashboard.setClimaCuajimalpa("No disponible");
            dashboard.setProximoAsueto("No disponible");
        }

        // APIs 3 y 4: Imágenes
        String nombreSinEspacios = alumno.getNombre().replace(" ", "");
        dashboard.setAvatarUrl("https://api.dicebear.com/7.x/identicon/svg?seed=" + nombreSinEspacios);
        dashboard.setQrCodeUrl("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + dashboard.getMatricula());

        return dashboard;
    }

    // Traductor del código meteorológico a Emojis
    private String obtenerIconoClima(int code) {
        if (code == 0) return "☀️";
        if (code >= 1 && code <= 3) return "⛅";
        if (code >= 45 && code <= 48) return "🌫️";
        if (code >= 51 && code <= 67) return "🌧️";
        if (code >= 71 && code <= 77) return "❄️";
        if (code >= 95 && code <= 99) return "⛈️";
        return "🌡️";
    }
}