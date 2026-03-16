package mx.cua.uam.lockersapi.controller;

import mx.cua.uam.lockersapi.dto.TrimestreDTO;
import mx.cua.uam.lockersapi.service.TrimestreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trimestres")
public class TrimestreController {

    private final TrimestreService trimestreService;

    public TrimestreController(TrimestreService trimestreService) {
        this.trimestreService = trimestreService;
    }

    @GetMapping
    public ResponseEntity<List<TrimestreDTO>> obtenerTodos() {
        return ResponseEntity.ok(trimestreService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrimestreDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(trimestreService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TrimestreDTO> guardar(@RequestBody TrimestreDTO trimestreDTO) {
        TrimestreDTO nuevoTrimestre = trimestreService.guardar(trimestreDTO);
        return new ResponseEntity<>(nuevoTrimestre, HttpStatus.CREATED);
    }
}