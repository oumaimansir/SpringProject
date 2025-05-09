package tn.itbs.spring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import tn.itbs.spring.dto.TechnicienDTO;
import tn.itbs.spring.mappers.TechnicienMapper;
import tn.itbs.spring.models.Machine;
import tn.itbs.spring.models.Technicien;
import tn.itbs.spring.repositories.MachineRepository;
import tn.itbs.spring.repositories.TechnicienRepository;
@Service
public class TechnicienService {
	private final TechnicienRepository technicienRepository;
    private final MachineRepository machineRepository;
    private final TechnicienMapper technicienMapper;

    public TechnicienService(TechnicienRepository technicienRepository,
                             MachineRepository machineRepository,
                             TechnicienMapper technicienMapper) {
        this.technicienRepository = technicienRepository;
        this.machineRepository = machineRepository;
        this.technicienMapper = technicienMapper;
    }

    // CRUD: Create
    public TechnicienDTO createTechnicien(TechnicienDTO dto) {
        Machine machineAssignee = dto.getMachineAssigneeNom() != null
                ? machineRepository.findByNom(dto.getMachineAssigneeNom())
                        .orElseThrow(() -> new RuntimeException("Machine not found"))
                : null;

        Technicien entity = technicienMapper.toEntity(dto);
        entity.setMachineAssignee(machineAssignee);
        Technicien savedEntity = technicienRepository.save(entity);
        return technicienMapper.toDTO(savedEntity);
    }

    // CRUD: Read by ID
    public TechnicienDTO findById(Long id) {
        Technicien entity = technicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technicien not found"));
        return technicienMapper.toDTO(entity);
    }

    // CRUD: Read all
    public List<TechnicienDTO> findAll() {
        return technicienRepository.findAll().stream()
                .map(technicienMapper::toDTO)
                .collect(Collectors.toList());
    }

    // CRUD: Update
    public TechnicienDTO updateTechnicien(Long id, TechnicienDTO dto) {
        Technicien entity = technicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technicien not found"));
        Machine machineAssignee = dto.getMachineAssigneeNom() != null
                ? machineRepository.findByNom(dto.getMachineAssigneeNom())
                        .orElseThrow(() -> new RuntimeException("Machine not found"))
                : null;

        entity.setNom(dto.getNom());
        entity.setCompétences(dto.getCompétences());
        entity.setMachineAssignee(machineAssignee);
        Technicien updatedEntity = technicienRepository.save(entity);
        return technicienMapper.toDTO(updatedEntity);
    }

    // CRUD: Delete
    public void deleteTechnicien(Long id) {
        Technicien entity = technicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technicien not found"));
        technicienRepository.delete(entity);
    }

    // Business Logic: Assign Technicien to Machine
    public TechnicienDTO assignTechnicienToMachine(Long technicienId, String machineNom) {
        Technicien entity = technicienRepository.findById(technicienId)
                .orElseThrow(() -> new RuntimeException("Technicien not found"));
        Machine machine = machineRepository.findByNom(machineNom)
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        entity.setMachineAssignee(machine);
        Technicien updatedEntity = technicienRepository.save(entity);
        return technicienMapper.toDTO(updatedEntity);
    }
}
