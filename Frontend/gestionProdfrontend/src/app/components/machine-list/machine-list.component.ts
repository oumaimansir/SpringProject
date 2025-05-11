import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MachineService } from '../../services/machine.service';
import { Machine } from '../../models/machine';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-machine-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './machine-list.component.html',
  styleUrls: ['./machine-list.component.scss']
})
export class MachineListComponent implements OnInit {
  machines: Machine[] = [];

  constructor(private machineService: MachineService) {}

  ngOnInit(): void {
    this.loadMachines();
  }

  loadMachines(): void {
  this.machineService.getAll().subscribe({
    next: (data) => {
      console.log('Received machines:', data);
      this.machines = data;
    },
    error: (err) => {
      console.error('Failed to load machines:', err);
    }
  });
}

  deleteMachine(id: number): void {
    if (confirm('Are you sure you want to delete this machine?')) {
      this.machineService.delete(id).subscribe(() => {
        this.loadMachines();
      });
    }
  }
}