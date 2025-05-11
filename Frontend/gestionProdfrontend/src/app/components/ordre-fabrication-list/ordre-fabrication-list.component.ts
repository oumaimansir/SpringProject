import { Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrdreFabricationService } from '../../services/ordre-fabrication.service';
import { OrdreFabrication } from '../../models/ordre-fabrication';
import { RouterLink } from '@angular/router';
import { Produit } from '../../models/produit';
import { ProduitService } from '../../services/produit.service';
import { Machine } from '../../models/machine';
import { MachineService } from '../../services/machine.service';
@Component({
  selector: 'app-ordre-fabrication-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './ordre-fabrication-list.component.html',
  styleUrl: './ordre-fabrication-list.component.scss'
})
export class OrdreFabricationListComponent implements OnInit{
ordres: OrdreFabrication[] = [];
  produits: Produit[] = []; // Store list of products
  errorMessage: string | null = null;
machines: Machine[] = [];
  constructor(
    private ordreFabricationService: OrdreFabricationService,
    private machineService: MachineService,
    private produitService: ProduitService // Inject ProduitService
  ) {}

  ngOnInit(): void {
    this.loadMachines();
    this.loadProduits(); // Load products
    this.loadOrdres();
  }
 loadMachines(): void {
    this.machineService.getAll().subscribe({
      next: (data) => {
        this.machines = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load machines: ' + err.message;
      }
    });
  }

  loadProduits(): void {
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load produits: ' + err.message;
      }
    });
  }

  loadOrdres(): void {
    this.ordreFabricationService.getAll().subscribe({
      next: (data) => {
        this.ordres = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load ordres: ' + err.message;
      }
    });
  }

  getProduitName(produitId: number): string {
    const produit = this.produits.find(p => p.id === produitId);
    return produit ? produit.nom : 'N/A';
  }
 getMachineName(machineId: number): string {
    const machine: Machine | undefined = this.machines.find(m => m.id === machineId);
    return machine ? (machine.nom || machine.id.toString()) : 'N/A';
  }
  deleteOrdre(id: number): void {
    if (confirm('Are you sure you want to delete this order?')) {
      this.ordreFabricationService.delete(id).subscribe({
        next: () => {
          this.loadOrdres();
          this.errorMessage = null;
        },
        error: (err) => {
          this.errorMessage = 'Failed to delete ordre: ' + err.message;
        }
      });
    }
  }

  trackStatus(id: number): void {
    console.log('Tracking status for ordre ID:', id);
    this.ordreFabricationService.trackOrdreStatus(id).subscribe({
      next: (status) => {
        console.log('Status received:', status);
        alert(`Order Status: ${status}`);
      },
      error: (err) => {
        console.error('Failed to track status:', err);
        this.errorMessage = 'Failed to track status: ' + err.message;
      }
    });
  }
}
