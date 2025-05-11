package tn.itbs.spring.mappers;


import tn.itbs.spring.dto.OrdreFabricationDTO;
import tn.itbs.spring.models.OrdreFabrication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrdreFabricationMapper {
	@Mapping(target = "produitId", source = "produit.id")
	@Mapping(source = "produit.nom", target = "produitNom")
	@Mapping(target = "machineId", source = "machine.id")
	@Mapping(target = "machineNom", source = "machine.nom")
	@Mapping(target = "quantite", source = "quantite")
    OrdreFabricationDTO toDTO(OrdreFabrication entity);

    @Mapping(target = "produit", ignore = true)
    @Mapping(target = "machine", ignore = true)
    @Mapping(target = "quantite", source = "quantite")
    OrdreFabrication toEntity(OrdreFabricationDTO dto);
}
