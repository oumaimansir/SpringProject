package tn.itbs.spring.controllers;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.spring.dto.MaintenanceDTO;
import tn.itbs.spring.services.MaintenanceService;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {
	private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceDTO> create(@Valid @RequestBody MaintenanceDTO dto) {
        MaintenanceDTO created = maintenanceService.createMaintenance(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> getById(@PathVariable Long id) {
        MaintenanceDTO dto = maintenanceService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceDTO>> getAll() {
        List<MaintenanceDTO> dtos = maintenanceService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> update(@PathVariable Long id, @Valid @RequestBody MaintenanceDTO dto) {
        MaintenanceDTO updated = maintenanceService.updateMaintenance(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/plan")
    public ResponseEntity<MaintenanceDTO> planMaintenance(@Valid @RequestBody MaintenanceDTO dto) {
        MaintenanceDTO planned = maintenanceService.planMaintenance(dto);
        return ResponseEntity.ok(planned);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> trackStatus(@PathVariable Long id) {
        String status = maintenanceService.trackMaintenanceStatus(id);
        return ResponseEntity.ok(status);
    }
}
