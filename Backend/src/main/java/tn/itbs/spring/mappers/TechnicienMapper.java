package tn.itbs.spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tn.itbs.spring.dto.TechnicienDTO;
import tn.itbs.spring.models.Technicien;

@Mapper(componentModel = "spring")
public interface TechnicienMapper {
    @Mapping(source = "machineAssignee.nom", target = "machineAssigneeNom")
    TechnicienDTO toDTO(Technicien entity);

    @Mapping(target = "machineAssignee", ignore = true) // Handled by service
    Technicien toEntity(TechnicienDTO dto);
}
