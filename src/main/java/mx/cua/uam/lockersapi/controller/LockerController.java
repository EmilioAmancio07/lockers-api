package mx.cua.uam.lockersapi.controller;

import mx.cua.uam.lockersapi.dto.LockerDTO;
import mx.cua.uam.lockersapi.service.LockerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lockers")
public class LockerController {

    private final LockerService lockerService;

    public LockerController(LockerService lockerService) {
        this.lockerService = lockerService;
    }

    @GetMapping
    public ResponseEntity<List<LockerDTO>> obtenerTodos() {
        return ResponseEntity.ok(lockerService.obtenerTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<LockerDTO>> obtenerDisponibles() {
        return ResponseEntity.ok(lockerService.obtenerDisponibles());
    }

    @PostMapping
    public ResponseEntity<LockerDTO> guardar(@RequestBody LockerDTO lockerDTO) {
        LockerDTO nuevoLocker = lockerService.guardar(lockerDTO);
        return new ResponseEntity<>(nuevoLocker, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LockerDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(lockerService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LockerDTO> actualizar(@PathVariable Integer id, @RequestBody LockerDTO lockerDTO) {
        try {
            return ResponseEntity.ok(lockerService.actualizar(id, lockerDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}