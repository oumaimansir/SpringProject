package tn.itbs.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.spring.models.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long>{

}
