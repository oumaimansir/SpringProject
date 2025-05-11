import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/produit';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-produit-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './produit-form.component.html',
  styleUrls: ['./produit-form.component.scss']
})
export class ProduitFormComponent implements OnInit {
  produitForm: FormGroup;
  isEdit = false;
  produitId: number | null = null;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  typeOptions: string[] = ['Electronics', 'Clothing', 'Food', 'Furniture']; 
  constructor(
    private fb: FormBuilder,
    private produitService: ProduitService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.produitForm = this.fb.group({
      nom: ['', Validators.required],
      type: ['', Validators.required],
      stock: [0, [Validators.required, Validators.min(0)]],
      fournisseur: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.produitId = +id;
        this.loadProduit(this.produitId);
      }
    });
  }

  loadProduit(id: number): void {
    this.produitService.getById(id).subscribe({
      next: (data) => {
        this.produitForm.patchValue(data);
      },
      error: (err) => {
        this.errorMessage = 'Failed to load produit: ' + err.message;
      }
    });
  }

  onSubmit(): void {
    if (this.produitForm.valid) {
      const produit: Produit = this.produitForm.value;
      if (this.isEdit && this.produitId) {
        this.produitService.update(this.produitId, produit).subscribe({
          next: () => {
            this.successMessage = 'Produit updated successfully!';
            setTimeout(() => this.navigateToProduits(), 2000);
          },
          error: (err) => {
            this.errorMessage = 'Update failed: ' + err.message;
          }
        });
      } else {
        this.produitService.create(produit).subscribe({
          next: () => {
            this.successMessage = 'Produit created successfully!';
            setTimeout(() => this.navigateToProduits(), 2000);
          },
          error: (err) => {
            this.errorMessage = 'Create failed: ' + err.message;
          }
        });
      }
    } else {
      this.errorMessage = 'Please fill out all required fields correctly.';
    }
  }

  navigateToProduits(): void {
  this.router.navigate(['/produits']);
}
}