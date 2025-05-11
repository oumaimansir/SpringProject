package tn.itbs.spring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tn.itbs.spring.dto.MachineDTO;
import tn.itbs.spring.dto.TechnicienDTO;
import tn.itbs.spring.mappers.MachineMapper;
import tn.itbs.spring.mappers.TechnicienMapper;
import tn.itbs.spring.models.Machine;
import tn.itbs.spring.models.Technicien;
import tn.itbs.spring.repositories.MachineRepository;
import tn.itbs.spring.repositories.TechnicienRepository;
@Service
public class MachineService {
	 private static final Logger logger = LoggerFactory.getLogger(MachineService.class);

	    private final MachineRepository machineRepository;
	    private final TechnicienRepository technicienRepository;
	    private final MachineMapper machineMapper;
	    private final TechnicienMapper technicienMapper;

	    public MachineService(MachineRepository machineRepository,
	                          TechnicienRepository technicienRepository,
	                          MachineMapper machineMapper,
	                          TechnicienMapper technicienMapper) {
	        this.machineRepository = machineRepository;
	        this.technicienRepository = technicienRepository;
	        this.machineMapper = machineMapper;
	        this.technicienMapper = technicienMapper;
	    }

	    // CRUD: Create
	    public MachineDTO createMachine(MachineDTO dto) {
	    	logger.info("Creating machine with DTO: nom={}, état={}, maintenanceProchaine={}",
	                dto.getNom(), dto.getÉtat(), dto.getMaintenanceProchaine());
	        Machine entity = machineMapper.toEntity(dto);
	        Machine savedEntity = machineRepository.save(entity);
	        logger.info("Saved machine: nom={}, état={}, maintenanceProchaine={}",
	                savedEntity.getNom(), savedEntity.getÉtat(), savedEntity.getMaintenanceProchaine());
	        return machineMapper.toDTO(savedEntity);
	    }

	    // CRUD: Read by ID
	    public MachineDTO findById(Long id) {
	        Machine entity = machineRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        return machineMapper.toDTO(entity);
	    }

	    // CRUD: Read all
	    public List<MachineDTO> findAll() {
	        return machineRepository.findAll().stream()
	                .map(machineMapper::toDTO)
	                .collect(Collectors.toList());
	    }

	    // CRUD: Update
	    public MachineDTO updateMachine(Long id, MachineDTO dto) {
	        Machine entity = machineRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        entity.setNom(dto.getNom());
	        entity.setÉtat(dto.getÉtat());
	        entity.setMaintenanceProchaine(dto.getMaintenanceProchaine());
	        Machine updatedEntity = machineRepository.save(entity);
	        return machineMapper.toDTO(updatedEntity);
	    }

	    // CRUD: Delete
	    public void deleteMachine(Long id) {
	        Machine entity = machineRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        machineRepository.delete(entity);
	    }

	    // Business Logic: Get Technicians Assigned to Machine
	    public List<TechnicienDTO> getAssignedTechnicians(Long machineId) {
	        Machine entity = machineRepository.findById(machineId)
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        List<Technicien> technicians = technicienRepository.findAll().stream()
	                .filter(technicien -> technicien.getMachineAssignee() != null && technicien.getMachineAssignee().getId().equals(machineId))
	                .collect(Collectors.toList());
	        return technicians.stream()
	                .map(technicienMapper::toDTO)
	                .collect(Collectors.toList());
	    }

	    // Business Logic: Assign Machine to Technicien
	    public MachineDTO assignMachineToTechnicien(Long machineId, Long technicienId) {
	        Machine entity = machineRepository.findById(machineId)
	                .orElseThrow(() -> new RuntimeException("Machine not found"));
	        Technicien technicien = technicienRepository.findById(technicienId)
	                .orElseThrow(() -> new RuntimeException("Technicien not found"));

	        technicien.setMachineAssignee(entity);
	        technicienRepository.save(technicien);
	        return machineMapper.toDTO(entity);
	    }
}
