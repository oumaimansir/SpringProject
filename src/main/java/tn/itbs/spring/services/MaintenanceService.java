package tn.itbs.spring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import tn.itbs.spring.dto.MaintenanceDTO;
import tn.itbs.spring.mappers.MaintenanceMapper;
import tn.itbs.spring.models.Machine;
import tn.itbs.spring.models.Maintenance;
import tn.itbs.spring.models.Technicien;
import tn.itbs.spring.repositories.MachineRepository;
import tn.itbs.spring.repositories.MaintenanceRepository;
import tn.itbs.spring.repositories.TechnicienRepository;
@Service
public class MaintenanceService {
	 private final MaintenanceRepository maintenanceRepository;
	    private final MachineRepository machineRepository;
	    private final TechnicienRepository technicienRepository;
	    private final MaintenanceMapper maintenanceMapper;

	    public MaintenanceService(MaintenanceRepository maintenanceRepository,
	                              MachineRepository machineRepository,
	                              TechnicienRepository technicienRepository,
	                              MaintenanceMapper maintenanceMapper) {
	        this.maintenanceRepository = maintenanceRepository;
	        this.machineRepository = machineRepository;
	        this.technicienRepository = technicienRepository;
	        this.maintenanceMapper = maintenanceMapper;
	    }

	    // CRUD: Create
	    public MaintenanceDTO createMaintenance(MaintenanceDTO dto) {
	    	System.out.println("machineId: " + dto.getMachineId() + ", type: " + dto.getMachineId().getClass());
	        System.out.println("technicienId: " + dto.getTechnicienId() + ", type: " + dto.getTechnicienId().getClass());
	        Machine machine = machineRepository.findById(dto.getMachineId())
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        Technicien technicien = technicienRepository.findById(dto.getTechnicienId())
	                .orElseThrow(() -> new RuntimeException("Technicien not found"));

	        Maintenance entity = maintenanceMapper.toEntity(dto);
	        entity.setMachine(machine);
	        entity.setTechnicien(technicien);
	        Maintenance savedEntity = maintenanceRepository.save(entity);
	        return maintenanceMapper.toDTO(savedEntity);
	    }

	    // CRUD: Read by ID
	    public MaintenanceDTO findById(Long id) {
	        Maintenance entity = maintenanceRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
	        return maintenanceMapper.toDTO(entity);
	    }

	    // CRUD: Read all
	    public List<MaintenanceDTO> findAll() {
	        return maintenanceRepository.findAll().stream()
	                .map(maintenanceMapper::toDTO)
	                .collect(Collectors.toList());
	    }

	    // CRUD: Update
	    public MaintenanceDTO updateMaintenance(Long id, MaintenanceDTO dto) {
	        Maintenance entity = maintenanceRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
	        Machine machine = machineRepository.findById(dto.getMachineId())
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        Technicien technicien = technicienRepository.findById(dto.getTechnicienId())
	                .orElseThrow(() -> new RuntimeException("Technicien not found"));

	        entity.setMachine(machine);
	        entity.setTechnicien(technicien);
	        entity.setDate(dto.getDate());
	        entity.setType(dto.getType());
	        Maintenance updatedEntity = maintenanceRepository.save(entity);
	        return maintenanceMapper.toDTO(updatedEntity);
	    }

	    // CRUD: Delete
	    public void deleteMaintenance(Long id) {
	        Maintenance entity = maintenanceRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
	        maintenanceRepository.delete(entity);
	    }

	    // Business Logic: Plan Maintenance
	    public MaintenanceDTO planMaintenance(MaintenanceDTO dto) {
	        // Similar to create, but can include additional validation (e.g., check machine maintenance schedule)
	        return createMaintenance(dto);
	    }

	    // Business Logic: Track Maintenance Status
	    public String trackMaintenanceStatus(Long id) {
	        Maintenance entity = maintenanceRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
	        return entity.getType(); // Or add a status field to Maintenance entity if needed
	    }
}
