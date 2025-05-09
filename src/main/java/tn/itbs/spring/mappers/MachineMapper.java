package tn.itbs.spring.mappers;

import org.mapstruct.Mapper;

import tn.itbs.spring.dto.MachineDTO;
import tn.itbs.spring.models.Machine;

@Mapper(componentModel = "spring")
public interface MachineMapper {
	MachineDTO toDTO(Machine entity);

    Machine toEntity(MachineDTO dto);
}
