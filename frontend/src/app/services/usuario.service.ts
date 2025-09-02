import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';
import { environment } from '../../environments/environment';
import { RespostaPaginada } from '../models/resposta-paginada';
import { UsuarioParametro } from '../models/usuario';

const httpOptions = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('token')}`
  }
};

const API = `${environment.baseUrl}/users`;

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  listar(filtro: UsuarioParametro): Observable<RespostaPaginada<Usuario>> {
    let params = new HttpParams()
      .set('page', filtro.page.toString())
      .set('size', filtro.size.toString());

    if (filtro.texto) {
      params = params.set('texto', filtro.texto);
    }
    if (filtro.perfil) {
      params = params.set('perfil', filtro.perfil);
    }
    if (filtro.sort) {
      params = params.set('sort', filtro.sort);
    }

    return this.http.get<RespostaPaginada<Usuario>>(API, { ...httpOptions, params });
  }

  buscarPorId(id: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${API}/${id}`, httpOptions);
  }

  criar(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(API, usuario, httpOptions);
  }

  atualizar(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${API}/${usuario.id}`, usuario, httpOptions);
  }

  atualizarPerfil(id: string, perfil: string): Observable<Usuario> {
    return this.http.patch<Usuario>(`${API}/${id}/perfil`, perfil, httpOptions);
  }

  atualizarSenha(id: string, senha: string): Observable<Usuario> {
    return this.http.patch<Usuario>(`${API}/${id}/senha`, senha, httpOptions);
  }

  deletar(id: string): Observable<Usuario> {
    return this.http.delete<Usuario>(`${API}/${id}`, httpOptions);
  }
}
