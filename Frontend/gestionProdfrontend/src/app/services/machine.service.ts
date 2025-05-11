import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Machine } from '../models/machine';
@Injectable({
  providedIn: 'root'
})
export class MachineService {
private apiUrl = 'http://localhost:9090/api/machines';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Machine[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(machines => {
        console.log('Raw API response:', machines); // Debug log
        return machines.map(machine => ({
          id: machine.id,
          nom: machine.nom,
          etat: machine.etat || '', // Use 'etat' from API response
          maintenanceProchaine: machine.maintenanceProchaine || ''
        }));
      })
    );
  }

  getById(id: number): Observable<Machine> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      map(machine => {
        console.log('Raw API response for ID:', machine); // Debug log
        return {
          id: machine.id,
          nom: machine.nom,
          etat: machine.etat || '', // Use 'etat' from API response
          maintenanceProchaine: machine.maintenanceProchaine || ''
        };
      })
    );
  }

  create(machine: Machine): Observable<Machine> {
    const payload = {
      ...machine,
      etat: machine.etat // Use 'etat' to match backend expectation
    };
    return this.http.post<any>(this.apiUrl, payload).pipe(
      map(response => {
        console.log('Create response:', response); // Debug log
        return {
          id: response.id,
          nom: response.nom,
          etat: response.etat || '', // Use 'etat' from API response
          maintenanceProchaine: response.maintenanceProchaine || ''
        };
      })
    );
  }

  update(id: number, machine: Machine): Observable<Machine> {
    const payload = {
      ...machine,
      etat: machine.etat // Use 'etat' to match backend expectation
    };
    return this.http.put<any>(`${this.apiUrl}/${id}`, payload).pipe(
      map(response => {
        console.log('Update response:', response); // Debug log
        return {
          id: response.id,
          nom: response.nom,
          etat: response.etat || '', // Use 'etat' from API response
          maintenanceProchaine: response.maintenanceProchaine || ''
        };
      })
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
