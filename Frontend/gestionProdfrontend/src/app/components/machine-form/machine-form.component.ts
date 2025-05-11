import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MachineService } from '../../services/machine.service';
import { Machine } from '../../models/machine';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-machine-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './machine-form.component.html',
  styleUrls: ['./machine-form.component.scss']
})
export class MachineFormComponent implements OnInit {
  machineForm: FormGroup;
  isEdit = false;
  machineId: number | null = null;
  errorMessage: string | null = null;
  successMessage: string | null = null;
 etatOptions: string[] = ['Operational', 'Under Maintenance', 'Out of Service'];
  constructor(
    private fb: FormBuilder,
    private machineService: MachineService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.machineForm = this.fb.group({
      nom: ['', Validators.required],
      etat: ['', Validators.required], // Changed 'état' to 'etat'
      maintenanceProchaine: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.machineId = +id;
        this.loadMachine(this.machineId);
      }
    });
  }

  loadMachine(id: number): void {
    this.machineService.getById(id).subscribe({
      next: (data) => {
        this.machineForm.patchValue(data);
      },
      error: (err) => {
        this.errorMessage = 'Failed to load machine: ' + err.message;
      }
    });
  }

  onSubmit(): void {
    if (this.machineForm.valid) {
      const machine: Machine = this.machineForm.value;
      if (this.isEdit && this.machineId) {
        this.machineService.update(this.machineId, machine).subscribe({
          next: () => {
            this.successMessage = 'Machine updated successfully!';
            setTimeout(() => this.navigateToMachines(), 2000);
          },
          error: (err) => {
            this.errorMessage = 'Update failed: ' + err.message;
          }
        });
      } else {
        this.machineService.create(machine).subscribe({
          next: () => {
            this.successMessage = 'Machine created successfully!';
            setTimeout(() => this.navigateToMachines(), 2000);
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


  navigateToMachines(): void {
    this.router.navigate(['/machines']);
  }
}