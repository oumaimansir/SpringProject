package tn.itbs.spring.dto;
import lombok.Data;

@Data
public class ProduitDTO {
	private Long id;
    private String nom;
    private String type;
    private int stock;
    private String fournisseur;
}
