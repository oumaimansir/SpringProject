package tn.itbs.spring.mappers;


import tn.itbs.spring.dto.OrdreFabricationDTO;
import tn.itbs.spring.models.OrdreFabrication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrdreFabricationMapper {
	@Mapping(source = "produit.nom", target = "produitNom")
    @Mapping(source = "machine.nom", target = "machineNom")
    OrdreFabricationDTO toDTO(OrdreFabrication entity);

    @Mapping(target = "produit", ignore = true)
    @Mapping(target = "machine", ignore = true)
    @Mapping(source = "quantité", target = "quantite")
    OrdreFabrication toEntity(OrdreFabricationDTO dto);
}
