package tn.itbs.spring.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class MachineDTO {
	 private Long id;
	    private String nom;
	    @JsonProperty("etat")
	    private String état;
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private LocalDate maintenanceProchaine;
	    
}
