package tn.itbs.spring.mappers;

import org.mapstruct.Mapper;

import tn.itbs.spring.dto.ProduitDTO;
import tn.itbs.spring.models.Produit;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
	ProduitDTO toDTO(Produit entity);

    Produit toEntity(ProduitDTO dto);
}
