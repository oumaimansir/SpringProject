import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TechnicienService } from '../../services/technicien.service';
import { Technicien } from '../../models/technicien';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MachineService } from '../../services/machine.service';
import { Machine } from '../../models/machine';

@Component({
  selector: 'app-technicien-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './technicien-form.component.html',
  styleUrls: ['./technicien-form.component.scss']
})
export class TechnicienFormComponent implements OnInit {
  technicienForm: FormGroup;
   errorMessage: string | null = null;
  successMessage: string | null = null;
  technicienId: number | null = null;
  isEdit = false;
  machines: Machine[] = [];

  constructor(
    private fb: FormBuilder,
    private technicienService: TechnicienService,
    private machineService: MachineService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.technicienForm = this.fb.group({
      nom: ['', Validators.required],
      competences: [''],
      machineAssigneeNom: [''] // Dropdown for machine name
    });
  }

  ngOnInit(): void {
    // Load available machines for the dropdown
    this.loadMachines();

    // Check if we're in edit mode
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.technicienId = +id;
        this.loadTechnicien(this.technicienId);
      }
    });
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

  loadTechnicien(id: number): void {
    this.technicienService.getById(id).subscribe({
      next: (data) => {
        this.technicienForm.patchValue(data);
      },
      error: (err) => {
        this.errorMessage = 'Failed to load technicien: ' + err.message;
      }
    });
  }

  onSubmit(): void {
    if (this.technicienForm.valid) {
      const technicien: Technicien = this.technicienForm.value;
      if (this.isEdit && this.technicienId) {
        this.technicienService.update(this.technicienId, technicien).subscribe({
          next: () => {
            this.successMessage = 'Technicien updated successfully!';
            setTimeout(() => this.navigateToTechniciens(), 2000);
          },
          error: (err) => {
            this.errorMessage = 'Update failed: ' + err.message;
          }
        });
      } else {
        this.technicienService.create(technicien).subscribe({
          next: () => {
            this.successMessage = 'Technicien created successfully!';
            setTimeout(() => this.navigateToTechniciens(), 2000);
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

  assignMachine(): void {
    if (this.technicienId && this.technicienForm.valid) {
      const machineNom = this.technicienForm.get('machineAssigneeNom')?.value;
      if (machineNom) {
        this.technicienService.assignMachine(this.technicienId, machineNom).subscribe({
          next: () => {
            this.successMessage = 'Machine assigned successfully!';
            setTimeout(() => this.navigateToTechniciens(), 2000);
          },
          error: (err) => {
            this.errorMessage = 'Failed to assign machine: ' + err.message;
          }
        });
      } else {
        this.errorMessage = 'Please select a machine to assign.';
      }
    } else {
      this.errorMessage = 'Form is invalid or technicien ID is missing.';
    }
  }
  navigateToTechniciens(): void {
  this.router.navigate(['/techniciens']);
}
}