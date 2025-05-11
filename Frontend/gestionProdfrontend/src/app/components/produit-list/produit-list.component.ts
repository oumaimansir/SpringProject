import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/produit';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './produit-list.component.html',
  styleUrls: ['./produit-list.component.scss']
})
export class ProduitListComponent implements OnInit {
  produits: Produit[] = [];

  constructor(private produitService: ProduitService) {}

  ngOnInit(): void {
    this.loadProduits();
  }

  loadProduits(): void {
    this.produitService.getAll().subscribe(data => {
      this.produits = data;
    });
  }

  deleteProduit(id: number): void {
    if (confirm('Are you sure you want to delete this produit?')) {
      this.produitService.delete(id).subscribe(() => {
        this.loadProduits();
      });
    }
  }
}