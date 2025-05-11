package tn.itbs.spring.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.spring.dto.TechnicienDTO;
import tn.itbs.spring.services.TechnicienService;

@RestController
@RequestMapping("/api/techniciens")
@CrossOrigin(origins = "http://localhost:4200")
public class TechnicienController {
	private final TechnicienService technicienService;

    public TechnicienController(TechnicienService technicienService) {
        this.technicienService = technicienService;
    }

    @PostMapping
    public ResponseEntity<TechnicienDTO> create(@Valid @RequestBody TechnicienDTO dto) {
        TechnicienDTO created = technicienService.createTechnicien(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnicienDTO> getById(@PathVariable Long id) {
        TechnicienDTO dto = technicienService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TechnicienDTO>> getAll() {
        List<TechnicienDTO> dtos = technicienService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechnicienDTO> update(@PathVariable Long id, @Valid @RequestBody TechnicienDTO dto) {
        TechnicienDTO updated = technicienService.updateTechnicien(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        technicienService.deleteTechnicien(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/assign-machine")
    public ResponseEntity<TechnicienDTO> assignMachine(@PathVariable Long id, @RequestParam String machineNom) {
        TechnicienDTO assigned = technicienService.assignTechnicienToMachine(id, machineNom);
        return ResponseEntity.ok(assigned);
    }
}
