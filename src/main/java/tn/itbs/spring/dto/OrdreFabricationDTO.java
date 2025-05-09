package tn.itbs.spring.dto;

import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

@Data
public class OrdreFabricationDTO {
	private Long id;
    private String produitNom; 
    @JsonProperty("quantite")
    @NotNull(message = "Quantité is required")
    private Integer quantité;
    private LocalDate date;
    private String machineNom; 
    private String statut;
}
