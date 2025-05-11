import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { OrdreFabrication } from '../models/ordre-fabrication';
@Injectable({
  providedIn: 'root'
})
export class OrdreFabricationService {
private apiUrl = 'http://localhost:9090/api/ordres';

  constructor(private http: HttpClient) {}

 getAll(): Observable<OrdreFabrication[]> {
    return this.http.get<OrdreFabrication[]>(this.apiUrl);
  }

  getById(id: number): Observable<OrdreFabrication> {
    return this.http.get<OrdreFabrication>(`${this.apiUrl}/${id}`);
  }

  create(ordre: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.post<OrdreFabrication>(this.apiUrl, ordre);
  }

  update(id: number, ordre: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.put<OrdreFabrication>(`${this.apiUrl}/${id}`, ordre);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  trackOrdreStatus(id: number): Observable<string> {
    return this.http.get(`${this.apiUrl}/${id}/status`, { responseType: 'text' });
  }
}
