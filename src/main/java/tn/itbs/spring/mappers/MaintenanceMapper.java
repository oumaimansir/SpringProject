package tn.itbs.spring.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tn.itbs.spring.dto.MaintenanceDTO;
import tn.itbs.spring.models.Maintenance;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {
	@Mapping(source = "machine.id", target = "machineId")
    @Mapping(source = "technicien.id", target = "technicienId")
    MaintenanceDTO toDTO(Maintenance entity);

    @Mapping(target = "machine", ignore = true) // Handled by service
    @Mapping(target = "technicien", ignore = true) // Handled by service
    Maintenance toEntity(MaintenanceDTO dto);
}
