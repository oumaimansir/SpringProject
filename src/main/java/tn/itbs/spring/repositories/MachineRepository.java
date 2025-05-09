package tn.itbs.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.spring.models.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long>{
	Optional<Machine> findByNom(String nom);
}
