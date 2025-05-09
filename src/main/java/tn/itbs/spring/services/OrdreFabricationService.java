package tn.itbs.spring.services;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import tn.itbs.spring.dto.OrdreFabricationDTO;
import tn.itbs.spring.mappers.OrdreFabricationMapper;
import tn.itbs.spring.models.Machine;
import tn.itbs.spring.models.OrdreFabrication;
import tn.itbs.spring.models.Produit;
import tn.itbs.spring.repositories.MachineRepository;
import tn.itbs.spring.repositories.OrdreFabricationRepository;
import tn.itbs.spring.repositories.ProduitRepository;

@Service
public class OrdreFabricationService {
	private final OrdreFabricationRepository ordreFabricationRepository;
    private final ProduitRepository produitRepository;
    private final MachineRepository machineRepository;
    private final OrdreFabricationMapper ordreFabricationMapper;

    public OrdreFabricationService(OrdreFabricationRepository ordreFabricationRepository,
                                   ProduitRepository produitRepository,
                                   MachineRepository machineRepository,
                                   OrdreFabricationMapper ordreFabricationMapper) {
        this.ordreFabricationRepository = ordreFabricationRepository;
        this.produitRepository = produitRepository;
        this.machineRepository = machineRepository;
        this.ordreFabricationMapper = ordreFabricationMapper;
    }

    // CRUD: Create
    public OrdreFabricationDTO createOrdreFabrication(OrdreFabricationDTO dto) {
    	System.out.println("Service received quantité: " + dto.getQuantité());
        if (dto.getQuantité() == null || dto.getQuantité() <= 0) {
            throw new IllegalArgumentException("Quantité must be greater than 0");
        }
        Produit produit = produitRepository.findByNom(dto.getProduitNom())
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        Machine machine = machineRepository.findByNom(dto.getMachineNom())
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        OrdreFabrication entity = ordreFabricationMapper.toEntity(dto);
        entity.setProduit(produit);
        entity.setMachine(machine);
        OrdreFabrication savedEntity = ordreFabricationRepository.save(entity);
        return ordreFabricationMapper.toDTO(savedEntity);
    }

    // CRUD: Read by ID
    public OrdreFabricationDTO findById(Long id) {
        OrdreFabrication entity = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdreFabrication not found"));
        return ordreFabricationMapper.toDTO(entity);
    }

    // CRUD: Read all
    public List<OrdreFabricationDTO> findAll() {
        return ordreFabricationRepository.findAll().stream()
                .map(ordreFabricationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // CRUD: Update
    public OrdreFabricationDTO updateOrdreFabrication(Long id, OrdreFabricationDTO dto) {
        OrdreFabrication entity = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdreFabrication not found"));
        Produit produit = produitRepository.findByNom(dto.getProduitNom())
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        Machine machine = machineRepository.findByNom(dto.getMachineNom())
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        entity.setProduit(produit);
        entity.setMachine(machine);
        entity.setQuantite(dto.getQuantité());
        entity.setDate(dto.getDate());
        entity.setStatut(dto.getStatut());
        OrdreFabrication updatedEntity = ordreFabricationRepository.save(entity);
        return ordreFabricationMapper.toDTO(updatedEntity);
    }

    // CRUD: Delete
    public void deleteOrdreFabrication(Long id) {
        OrdreFabrication entity = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdreFabrication not found"));
        ordreFabricationRepository.delete(entity);
    }

    // Business Logic: Plan Order (assign machine and set status)
    public OrdreFabricationDTO planOrdreFabrication(Long id, String machineNom, String statut) {
        OrdreFabrication entity = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdreFabrication not found"));
        Machine machine = machineRepository.findByNom(machineNom)
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        entity.setMachine(machine);
        entity.setStatut(statut); // e.g., "PLANIFIE", "EN_COURS"
        OrdreFabrication updatedEntity = ordreFabricationRepository.save(entity);
        return ordreFabricationMapper.toDTO(updatedEntity);
    }

    // Business Logic: Track Order Status
    public String trackOrdreFabricationStatus(Long id) {
        OrdreFabrication entity = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdreFabrication not found"));
        return entity.getStatut();
    }

}
