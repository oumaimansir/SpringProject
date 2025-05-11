import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaintenanceService } from '../../services/maintenance.service';
import { Maintenance } from '../../models/maintenance';
import { RouterLink } from '@angular/router';
import { Machine } from '../../models/machine';
import { Technicien } from '../../models/technicien';
import { MachineService } from '../../services/machine.service';
import { TechnicienService } from '../../services/technicien.service';

@Component({
  selector: 'app-maintenance-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './maintenance-list.component.html',
  styleUrls: ['./maintenance-list.component.scss']
})
export class MaintenanceListComponent implements OnInit {
  maintenances: Maintenance[] = [];
  machines: Machine[] = [];
  techniciens: Technicien[] = [];
  errorMessage: string | null = null;
  constructor(private maintenanceService: MaintenanceService,
    private machineService: MachineService,
    private technicienService: TechnicienService
  ) {}

 ngOnInit(): void {
    this.loadMachines();
    this.loadTechniciens();
    this.loadMaintenances();
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

  loadTechniciens(): void {
    this.technicienService.getAll().subscribe({
      next: (data) => {
        this.techniciens = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load techniciens: ' + err.message;
      }
    });
  }

  loadMaintenances(): void {
    this.maintenanceService.getAll().subscribe({
      next: (data) => {
        this.maintenances = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load maintenances: ' + err.message;
      }
    });
  }

  getMachineName(machineId: number): string {
    const machine: Machine | undefined = this.machines.find(m => m.id === machineId);
    return machine ? (machine.nom || machine.id.toString()) : 'N/A';
  }

  getTechnicienName(technicienId: number): string {
    const technicien: Technicien | undefined = this.techniciens.find(t => t.id === technicienId);
    return technicien ? (technicien.nom || technicien.id.toString()) : 'N/A';
  }
  deleteMaintenance(id: number): void {
    if (confirm('Are you sure you want to delete this maintenance?')) {
      this.maintenanceService.delete(id).subscribe({
        next: () => {
          this.loadMaintenances();
          this.errorMessage = null;
        },
        error: (err) => {
          this.errorMessage = 'Failed to delete maintenance: ' + err.message;
        }
      });
    }
  
  }

  trackStatus(id: number): void {
    this.maintenanceService.trackMaintenanceStatus(id).subscribe(status => {
      alert(`Maintenance Status: ${status}`);
    });
  }
}