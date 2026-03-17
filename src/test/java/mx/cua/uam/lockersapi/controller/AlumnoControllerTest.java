package mx.cua.uam.lockersapi.controller;

import mx.cua.uam.lockersapi.dto.AlumnoDTO;
import mx.cua.uam.lockersapi.service.AlumnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumnoController.class) // Solo probamos este controlador
public class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula las peticiones HTTP (Postman invisible)

    @MockBean
    private AlumnoService alumnoService; // Falsificamos el servicio para no tocar la BD

    @Test
    public void testObtenerTodos() throws Exception {
        // 1. PREPARACIÓN (Given): Creamos un alumno falso en memoria
        AlumnoDTO alumnoFalso = new AlumnoDTO();
        alumnoFalso.setId(1);
        alumnoFalso.setNombre("Carlos Martínez");
        alumnoFalso.setMatricula("2223012345");
        alumnoFalso.setLicenciatura("Tecnologías y Sistemas de la Información");

        // Le decimos al servicio falso qué responder cuando el controlador le pregunte
        when(alumnoService.obtenerTodos()).thenReturn(Arrays.asList(alumnoFalso));

        // 2. EJECUCIÓN Y VALIDACIÓN (When & Then): Simulamos el GET a /api/alumnos
        mockMvc.perform(get("/api/alumnos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Esperamos un 200 OK
                .andExpect(jsonPath("$[0].nombre").value("Carlos Martínez")) // Validamos el JSON
                .andExpect(jsonPath("$[0].matricula").value("2223012345"));
    }
}