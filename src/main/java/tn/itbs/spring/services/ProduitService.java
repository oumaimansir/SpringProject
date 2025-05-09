package tn.itbs.spring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import tn.itbs.spring.dto.ProduitDTO;
import tn.itbs.spring.mappers.ProduitMapper;
import tn.itbs.spring.models.Produit;
import tn.itbs.spring.repositories.ProduitRepository;
@Service
public class ProduitService {
	private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    // CRUD: Create
    public ProduitDTO createProduit(ProduitDTO dto) {
        Produit entity = produitMapper.toEntity(dto);
        Produit savedEntity = produitRepository.save(entity);
        return produitMapper.toDTO(savedEntity);
    }

    // CRUD: Read by ID
    public ProduitDTO findById(Long id) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        return produitMapper.toDTO(entity);
    }

    // CRUD: Read all
    public List<ProduitDTO> findAll() {
        return produitRepository.findAll().stream()
                .map(produitMapper::toDTO)
                .collect(Collectors.toList());
    }

    // CRUD: Update
    public ProduitDTO updateProduit(Long id, ProduitDTO dto) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        entity.setNom(dto.getNom());
        entity.setType(dto.getType());
        entity.setStock(dto.getStock());
        entity.setFournisseur(dto.getFournisseur());
        Produit updatedEntity = produitRepository.save(entity);
        return produitMapper.toDTO(updatedEntity);
    }

    // CRUD: Delete
    public void deleteProduit(Long id) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        produitRepository.delete(entity);
    }
}
