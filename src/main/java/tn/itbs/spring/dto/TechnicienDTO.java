package tn.itbs.spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TechnicienDTO {
	private Long id;
    private String nom;
    @JsonProperty("competences")
    private String compétences;
    private String machineAssigneeNom; // Use name instead of full Machine entity
}
