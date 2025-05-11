package tn.itbs.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.spring.dto.*;
import tn.itbs.spring.services.OrdreFabricationService;

@RestController
@RequestMapping("/api/ordres")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdreFabricationController {
	@Autowired
    private final OrdreFabricationService ordreFabricationService;

    public OrdreFabricationController(OrdreFabricationService ordreFabricationService) {
        this.ordreFabricationService = ordreFabricationService;
    }

    @PostMapping
    public ResponseEntity<OrdreFabricationDTO> create(@Valid @RequestBody OrdreFabricationDTO dto) {
        OrdreFabricationDTO ordre = ordreFabricationService.createOrdreFabrication(dto);
        return ResponseEntity.ok(ordre); // Return the DTO directly
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdreFabricationDTO> getById(@PathVariable Long id) {
        OrdreFabricationDTO dto = ordreFabricationService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<OrdreFabricationDTO>> getAll() {
        List<OrdreFabricationDTO> dtos = ordreFabricationService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdreFabricationDTO> update(@PathVariable Long id, @Valid @RequestBody OrdreFabricationDTO dto) {
        OrdreFabricationDTO updated = ordreFabricationService.updateOrdreFabrication(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ordreFabricationService.deleteOrdreFabrication(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/plan")
    public ResponseEntity<OrdreFabricationDTO> planOrdreFabrication(@PathVariable Long id,
                                                                    @RequestParam String machineNom,
                                                                    @RequestParam String statut) {
        OrdreFabricationDTO planned = ordreFabricationService.planOrdreFabrication(id, machineNom, statut);
        return ResponseEntity.ok(planned);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> trackStatus(@PathVariable Long id) {
        String status = ordreFabricationService.trackOrdreFabricationStatus(id);
        return ResponseEntity.ok(status);
    }
}
