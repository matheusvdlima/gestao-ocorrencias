import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ocorrencia } from '../models/ocorrencia';
import { environment } from '../../environments/environment';
import { Comentario } from '../models/comentario';
import { RespostaPaginada } from '../models/resposta-paginada';
import { OcorrenciaParametro } from '../models/ocorrencia';

const API = `${environment.baseUrl}/incidents`;

@Injectable({
  providedIn: 'root'
})
export class OcorrenciaService {

  constructor(private http: HttpClient) { }

  listarOcorrencias(filtro: OcorrenciaParametro): Observable<RespostaPaginada<Ocorrencia>> {
    let params = new HttpParams()
      .set('page', filtro.page.toString())
      .set('size', filtro.size.toString());

    if (filtro.texto) {
      params = params.set('texto', filtro.texto);
    }
    if (filtro.status) {
      params = params.set('status', filtro.status);
    }
    if (filtro.prioridade) {
      params = params.set('prioridade', filtro.prioridade);
    }
    if (filtro.sort && !filtro.sort.startsWith('undefined')) {
      params = params.set('sort', filtro.sort);
    }

    return this.http.get<RespostaPaginada<Ocorrencia>>(API, { params });
  }

  buscarOcorrenciaId(id: string): Observable<Ocorrencia> {
    return this.http.get<Ocorrencia>(`${API}/${id}`);
  }

  criarOcorrencia(ocorrencia: Ocorrencia): Observable<Ocorrencia> {
    return this.http.post<Ocorrencia>(API, ocorrencia);
  }

  atualizarOcorrencia(ocorrencia: Ocorrencia): Observable<Ocorrencia> {
    return this.http.put<Ocorrencia>(`${API}/${ocorrencia.id}`, ocorrencia);
  }

  atualizarStatus(id: string, status: string): Observable<Ocorrencia> {
    return this.http.patch<Ocorrencia>(`${API}/${id}/status`, {status});
  }

  deletarOcorrencia(id: string): Observable<Ocorrencia> {
    return this.http.delete<Ocorrencia>(`${API}/${id}`);
  }

  listarComentariosOcorrencia(id: string): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${API}/${id}/comments`);
  }

  criarComentariosOcorrencia(id: string, comentario: Comentario[]): Observable<Comentario[]> {
    return this.http.post<Comentario[]>(`${API}/${id}/comments`, [comentario]);
  }
}
