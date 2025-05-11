package tn.itbs.spring.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tn.itbs.spring.dto.MachineDTO;
import tn.itbs.spring.dto.TechnicienDTO;
import tn.itbs.spring.services.MachineService;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin(origins = "http://localhost:4200")
public class MachineController {
	@Autowired
	private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping
    public ResponseEntity<MachineDTO> create(@Valid @RequestBody MachineDTO dto) {
        MachineDTO created = machineService.createMachine(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getById(@PathVariable Long id) {
        MachineDTO dto = machineService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<MachineDTO>> getAll() {
        List<MachineDTO> dtos = machineService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MachineDTO> update(@PathVariable Long id, @Valid @RequestBody MachineDTO dto) {
        MachineDTO updated = machineService.updateMachine(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/technicians")
    public ResponseEntity<List<TechnicienDTO>> getAssignedTechnicians(@PathVariable Long id) {
        List<TechnicienDTO> technicians = machineService.getAssignedTechnicians(id);
        return ResponseEntity.ok(technicians);
    }

    @PutMapping("/{id}/assign-technician")
    public ResponseEntity<MachineDTO> assignTechnician(@PathVariable Long id, @RequestParam Long technicienId) {
        MachineDTO assigned = machineService.assignMachineToTechnicien(id, technicienId);
        return ResponseEntity.ok(assigned);
    }
}
