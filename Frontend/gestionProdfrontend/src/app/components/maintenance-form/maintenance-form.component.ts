import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MaintenanceService } from '../../services/maintenance.service';
import { Maintenance } from '../../models/maintenance';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Technicien } from '../../models/technicien';
import { Machine } from '../../models/machine';
import { TechnicienService } from '../../services/technicien.service';
import { MachineService } from '../../services/machine.service';

@Component({
  selector: 'app-maintenance-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './maintenance-form.component.html',
  styleUrls: ['./maintenance-form.component.scss']
})
export class MaintenanceFormComponent implements OnInit {
  maintenanceForm: FormGroup;
  isEdit = false;
  maintenanceId: number | null = null;
  techniciens: Technicien[] = [];
  machines: Machine[] = [];
  errorMessage: string | null = null; // Add error message property
  successMessage: string | null = null;
  maintenanceTypes: string[] = ['Preventive', 'Corrective', 'Emergency', 'Predictive'];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private maintenanceService: MaintenanceService,
    private technicienService: TechnicienService,
    private machineService: MachineService
  ) {
    this.maintenanceForm = this.fb.group({
      machineId: ['', Validators.required],
      technicienId: ['', Validators.required],
      date: ['', Validators.required],
      type: ['', Validators.required]
    });
  }

 ngOnInit(): void {
    this.technicienService.getAll().subscribe({
      next: (data) => {
        this.techniciens = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load techniciens: ' + err.message;
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
        this.maintenanceId = +id;
        this.maintenanceService.getById(this.maintenanceId).subscribe({
          next: (maintenance) => {
            this.maintenanceForm.patchValue({
              machineId: maintenance.machineId,
              technicienId: maintenance.technicienId,
              date: maintenance.date,
              type: maintenance.type
            });
          },
          error: (err) => {
            this.errorMessage = 'Failed to load maintenance: ' + err.message;
          }
        });
      }
    });
  }

  onSubmit(): void {
    console.log('onSubmit called');
    console.log('Form valid:', this.maintenanceForm.valid);
    console.log('Form value:', this.maintenanceForm.value);

    if (this.maintenanceForm.valid) {
      const maintenance: Maintenance = {
        id: this.maintenanceId || undefined,
        machineId: +this.maintenanceForm.value.machineId, // Ensure it's a number
        technicienId: +this.maintenanceForm.value.technicienId, // Ensure it's a number
        date: this.maintenanceForm.value.date,
        type: this.maintenanceForm.value.type
      };

      console.log('Submitting maintenance:', maintenance);

      if (this.isEdit) {
        this.maintenanceService.update(this.maintenanceId!, maintenance).subscribe({
          next: () => {
            console.log('Maintenance updated successfully');
            this.successMessage = 'Maintenance updated successfully!';
            setTimeout(() => {
              this.navigateToMaintenances();
            }, 2000);
          },
          error: (err) => {
            console.error('Update failed:', err);
            this.errorMessage = 'Failed to update maintenance: ' + err.message;
          }
        });
      } else {
        this.maintenanceService.create(maintenance).subscribe({
          next: (response) => {
            console.log('Maintenance created successfully:', response);
            this.successMessage = 'Maintenance created successfully!';
            setTimeout(() => {
              this.navigateToMaintenances();
            }, 2000);
          },
          error: (err) => {
            console.error('Create failed:', err);
            this.errorMessage = 'Failed to create maintenance: ' + err.message;
          }
        });
      }
    } else {
      console.log('Form is invalid');
      this.errorMessage = 'Please fill out all required fields.';
    }
  }
  navigateToMaintenances(): void {
    this.router.navigate(['/maintenances']);
  }
}