package tn.itbs.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.spring.models.Technicien;

public interface TechnicienRepository extends JpaRepository<Technicien, Long>{
	Optional<Technicien> findByNom(String nom);
}
