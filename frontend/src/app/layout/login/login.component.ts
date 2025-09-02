import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Credencial } from '../../models/credencial';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  hide = true;

  constructor(
    private fb: FormBuilder,
    private toast: ToastrService,
    private service: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  logar(): void {
    if (this.loginForm.invalid) {
      this.toast.warning('Preencha os campos corretamente');
      return;
    }

    const creds: Credencial = this.loginForm.value;

    this.service.authenticate(creds).subscribe({
      next: (resposta) => {
        const token = resposta.headers.get('Authorization')?.substring(7) ?? '';
        if (token) {
          this.service.successfulLogin(token);

          Promise.resolve().then(() => this.router.navigate(['']));
        } else {
          this.toast.error('Erro ao obter token de autenticação');
        }
      },
      error: () => {
        this.toast.error('Usuário e/ou senha inválidos');
      }
    });

  }

  get email() {
    return this.loginForm.get('email');
  }

  get senha() {
    return this.loginForm.get('senha');
  }

  validaCampos(): boolean {
    return this.loginForm.valid;
  }

}
