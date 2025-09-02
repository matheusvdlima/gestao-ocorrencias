import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Credencial } from '../models/credencial';
import { environment } from '../../environments/environment';
import { BehaviorSubject } from 'rxjs';

const API = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService();

  loggedIn$ = new BehaviorSubject<boolean>(this.isAuthenticated());

  constructor(private http: HttpClient) { }

  authenticate(creds: Credencial) {
    return this.http.post(`${API}/login`, creds, {
      observe: 'response'
    })
  }

  successfulLogin(authToken: string) {
    localStorage.setItem('token', authToken);
    this.loggedIn$.next(true);
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return token ? !this.jwtService.isTokenExpired(token) : false;
  }

  logout() {
    localStorage.clear();
    this.loggedIn$.next(false);
  }
}
