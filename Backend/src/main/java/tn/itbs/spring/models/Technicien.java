package tn.itbs.spring.models;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
@Entity
@Data
public class Technicien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String compétences;
    @ManyToOne
    private Machine machineAssignee;
    @OneToMany(mappedBy = "technicien", cascade = CascadeType.REMOVE)
    private List<Maintenance> maintenances;
    // Getters and Setters
}
