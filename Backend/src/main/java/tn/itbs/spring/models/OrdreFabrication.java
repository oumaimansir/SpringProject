package tn.itbs.spring.models;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Entity
@Data
public class OrdreFabrication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Produit produit;
    @Column(name = "quantité")
    private int quantite;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    private Machine machine;
    private String statut;

    
    // Getters and Setters
}

