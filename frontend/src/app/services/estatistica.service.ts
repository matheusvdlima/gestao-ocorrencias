import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estatistica } from '../models/estatistica';
import { environment } from '../../environments/environment';

const httpOptions = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('token')}`
  }
};

const API = `${environment.baseUrl}/stats/incidents`;

@Injectable({
  providedIn: 'root'
})
export class EstatisticaService {

  constructor(private http: HttpClient) { }

  buscarEstatisticas(): Observable<Estatistica> {
    return this.http.get<Estatistica>(API, httpOptions);
  }
}
