import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrdreFabricationService } from '../../services/ordre-fabrication.service';
import { OrdreFabrication } from '../../models/ordre-fabrication';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Produit } from '../../models/produit';
import { ProduitService } from '../../services/produit.service';
import { Machine } from '../../models/machine';
import { MachineService } from '../../services/machine.service';

@Component({
  selector: 'app-ordre-fabrication-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './ordre-fabrication-form.component.html',
  styleUrl: './ordre-fabrication-form.component.scss'
})
export class OrdreFabricationFormComponent implements OnInit{
ordreForm: FormGroup;
  isEdit = false;
ordreId: number | null = null;
  produits: Produit[] = []; 
  machines: Machine[] = []; 
  errorMessage: string | null = null;
  successMessage: string | null = null;
  statutOptions: string[] = ['EN_ATTENTE', 'EN_COURS', 'TERMINE', 'ANNULE'];
constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private ordreFabricationService: OrdreFabricationService,
    private produitService: ProduitService ,
    private machineService: MachineService
  ) {
    this.ordreForm = this.fb.group({
      produitId: ['', Validators.required], // Change to produitId
      quantite: ['', [Validators.required, Validators.min(1)]],
      date: ['', Validators.required],
      machineId: ['', Validators.required],
      statut: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load produits: ' + err.message;
      }
    });
    this.machineService.getAll().subscribe({
      next: (data) => {
        this.machines = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load machines: ' + err.message;
      }
    });


    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.ordreId = +id;
        this.ordreFabricationService.getById(this.ordreId).subscribe({
          next: (ordre) => {
            this.ordreForm.patchValue({
              produitId: ordre.produitId, // Use produitId
              quantite: ordre.quantite,
              date: ordre.date,
              machineId: ordre.machineId,
              statut: ordre.statut
            });
          },
          error: (err) => {
            this.errorMessage = 'Failed to load ordre: ' + err.message;
          }
        });
      }
    });
  }

  onSubmit(): void {
    console.log('onSubmit called');
    console.log('Form valid:', this.ordreForm.valid);
    console.log('Form value:', this.ordreForm.value);

    if (this.ordreForm.valid) {
      const ordre: OrdreFabrication = {
        id: this.ordreId || undefined,
        produitId: +this.ordreForm.value.produitId, // Ensure it's a number
        quantite: this.ordreForm.value.quantite,
        date: this.ordreForm.value.date,
        machineId: +this.ordreForm.value.machineId,
        statut: this.ordreForm.value.statut
      };

      console.log('Submitting ordre:', ordre);

      if (this.isEdit) {
        this.ordreFabricationService.update(this.ordreId!, ordre).subscribe({
          next: () => {
            console.log('Ordre updated successfully');
            this.successMessage = 'Ordre updated successfully!';
            setTimeout(() => {
              this.navigateToOrdres();
            }, 2000);
          },
          error: (err) => {
            console.error('Update failed:', err);
            this.errorMessage = 'Failed to update ordre: ' + err.message;
          }
        });
      } else {
        this.ordreFabricationService.create(ordre).subscribe({
          next: (response) => {
            console.log('Ordre created successfully:', response);
            this.successMessage = 'Ordre created successfully!';
            setTimeout(() => {
              this.navigateToOrdres();
            }, 2000);
          },
          error: (err) => {
            console.error('Create failed:', err);
            this.errorMessage = 'Failed to create ordre: ' + err.message;
          }
        });
      }
    } else {
      console.log('Form is invalid');
      this.errorMessage = 'Please fill out all required fields.';
    }
  }
  navigateToOrdres(): void {
  this.router.navigate(['/ordres']);
}
}
