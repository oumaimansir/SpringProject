import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Technicien } from '../models/technicien';
@Injectable({
  providedIn: 'root'
})
export class TechnicienService {
private apiUrl = 'http://localhost:9090/api/techniciens';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Technicien[]> {
    return this.http.get<Technicien[]>(this.apiUrl);
  }

  getById(id: number): Observable<Technicien> {
    return this.http.get<Technicien>(`${this.apiUrl}/${id}`);
  }

  create(technicien: Technicien): Observable<Technicien> {
    return this.http.post<Technicien>(this.apiUrl, technicien);
  }

  update(id: number, technicien: Technicien): Observable<Technicien> {
    return this.http.put<Technicien>(`${this.apiUrl}/${id}`, technicien);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  assignMachine(id: number, machineNom: string): Observable<Technicien> {
    return this.http.put<Technicien>(`${this.apiUrl}/${id}/assign-machine?machineNom=${machineNom}`, null);
  }
}
