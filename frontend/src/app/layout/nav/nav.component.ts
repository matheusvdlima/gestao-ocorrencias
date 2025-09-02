import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../security/auth.service';
import { DialogService } from '../dialog/dialog.service';
import { SenhaFormComponent } from './senha-form/senha-form.component';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-nav',
  standalone: false,
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  usuario = "John Doe";

  constructor(
    private router: Router,
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private dialogService: DialogService,
    private toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.router.navigate(['home']);
  }

  alterarSenha(): void {
    this.dialogService.openForm({
      formComponent: SenhaFormComponent,
      title: 'ALTERAR SENHA',
    }).subscribe((result: any) => {
      if (result) {
        this.usuarioService.atualizarSenha('', result.perfis).subscribe({
          next: () => {
            this.toastrService.success('Senha alterada com sucesso!');
          },
          error: () => this.toastrService.error('Erro ao alterar senha.')
        });
      }
    });
  }

  logout() {
    this.router.navigate(['login']);
    this.authService.logout();
    this.toastrService.info('Logout realizado com sucesso', 'Logout');
  }
}
