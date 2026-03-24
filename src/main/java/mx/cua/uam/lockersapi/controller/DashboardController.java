package mx.cua.uam.lockersapi.controller;

import mx.cua.uam.lockersapi.dto.DashboardDTO;
import mx.cua.uam.lockersapi.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*") // Resolviendo el problema de CORS que pide la rúbrica
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<DashboardDTO> obtenerDashboard(@PathVariable Integer id) {
        return ResponseEntity.ok(dashboardService.obtenerResumenAlumno(id));
    }
}