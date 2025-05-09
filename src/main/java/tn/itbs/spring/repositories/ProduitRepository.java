package tn.itbs.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.spring.models.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>{
	Optional<Produit> findByNom(String nom);
}
