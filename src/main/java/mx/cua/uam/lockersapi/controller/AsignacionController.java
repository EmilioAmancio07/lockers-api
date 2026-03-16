package mx.cua.uam.lockersapi.controller;

import mx.cua.uam.lockersapi.dto.AsignacionDTO;
import mx.cua.uam.lockersapi.service.AsignacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    private final AsignacionService asignacionService;

    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @GetMapping
    public ResponseEntity<List<AsignacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(asignacionService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<?> crearAsignacion(@RequestBody AsignacionDTO asignacionDTO) {
        try {
            AsignacionDTO nuevaAsignacion = asignacionService.crearAsignacion(asignacionDTO);
            return new ResponseEntity<>(nuevaAsignacion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<?> devolverLocker(@PathVariable Integer id) {
        try {
            AsignacionDTO asignacionActualizada = asignacionService.devolverLocker(id);
            return ResponseEntity.ok(asignacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}