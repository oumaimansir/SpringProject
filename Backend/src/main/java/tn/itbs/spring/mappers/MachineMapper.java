package tn.itbs.spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tn.itbs.spring.dto.MachineDTO;
import tn.itbs.spring.models.Machine;

@Mapper(componentModel = "spring")
public interface MachineMapper {
	@Mapping(target = "état", source = "état")
	MachineDTO toDTO(Machine entity);
	@Mapping(target = "état", source = "état")
    Machine toEntity(MachineDTO dto);
}
