package tn.itbs.spring.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import tn.itbs.spring.dto.ProduitDTO;
import tn.itbs.spring.services.ProduitService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
	 private final ProduitService produitService;

	    public ProduitController(ProduitService produitService) {
	        this.produitService = produitService;
	    }

	    @PostMapping
	    public ResponseEntity<ProduitDTO> create(@Valid @RequestBody ProduitDTO dto) {
	        ProduitDTO created = produitService.createProduit(dto);
	        return ResponseEntity.ok(created);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<ProduitDTO> getById(@PathVariable Long id) {
	        ProduitDTO dto = produitService.findById(id);
	        return ResponseEntity.ok(dto);
	    }

	    @GetMapping
	    public ResponseEntity<List<ProduitDTO>> getAll() {
	        List<ProduitDTO> dtos = produitService.findAll();
	        return ResponseEntity.ok(dtos);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<ProduitDTO> update(@PathVariable Long id, @Valid @RequestBody ProduitDTO dto) {
	        ProduitDTO updated = produitService.updateProduit(id, dto);
	        return ResponseEntity.ok(updated);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        produitService.deleteProduit(id);
	        return ResponseEntity.noContent().build();
	    }
}
