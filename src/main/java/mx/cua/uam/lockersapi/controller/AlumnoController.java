package mx.cua.uam.lockersapi.controller;

import mx.cua.uam.lockersapi.dto.AlumnoDTO;
import mx.cua.uam.lockersapi.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> obtenerTodos() {
        return ResponseEntity.ok(alumnoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> obtenerPorId(@PathVariable Integer id) { // Cambiado a Integer
        return ResponseEntity.ok(alumnoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlumnoDTO> guardar(@RequestBody AlumnoDTO alumnoDTO) {
        AlumnoDTO nuevoAlumno = alumnoService.guardar(alumnoDTO);
        return new ResponseEntity<>(nuevoAlumno, HttpStatus.CREATED);
    }
}