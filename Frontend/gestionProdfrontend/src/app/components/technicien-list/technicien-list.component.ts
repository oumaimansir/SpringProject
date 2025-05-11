import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TechnicienService } from '../../services/technicien.service';
import { Technicien } from '../../models/technicien';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-technicien-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './technicien-list.component.html',
  styleUrls: ['./technicien-list.component.scss']
})
export class TechnicienListComponent implements OnInit {
  techniciens: Technicien[] = [];

  constructor(private technicienService: TechnicienService) {}

  ngOnInit(): void {
    this.loadTechniciens();
  }

  loadTechniciens(): void {
    this.technicienService.getAll().subscribe(data => {
      this.techniciens = data;
    });
  }

  deleteTechnicien(id: number): void {
    if (confirm('Are you sure you want to delete this technicien?')) {
      this.technicienService.delete(id).subscribe(() => {
        this.loadTechniciens();
      });
    }
  }
}