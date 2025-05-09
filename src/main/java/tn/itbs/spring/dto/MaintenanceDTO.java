package tn.itbs.spring.dto;
import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class MaintenanceDTO {
	private Long id;
	@JsonProperty("machineId")
    private Long machineId;
    @JsonProperty("technicienId")
    private Long technicienId; 
    private LocalDate date;
    private String type;
}
