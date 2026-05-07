import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  // URL do seu backend Spring Boot
  private readonly API_URL = 'http://localhost:8080/api'; 

  constructor(private http: HttpClient) { }

  // Exemplo de método genérico para buscar dados
  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.API_URL}/${endpoint}`);
  }
}