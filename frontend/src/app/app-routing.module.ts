import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './layout/login/login.component';
import { NavComponent } from './layout/nav/nav.component';
import { HomeComponent } from './modules/home/home.component';
import { OcorrenciaComponent } from './modules/ocorrencia/ocorrencia.component';
import { UsuarioComponent } from './modules/usuario/usuario.component';
import { EstatisticasComponent } from './modules/estatisticas/estatisticas.component';
import { AuthGuard } from './security/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '', component: NavComponent, canActivate: [AuthGuard], children: [
      { path: 'home', component: HomeComponent },
      { path: 'ocorrencias', component: OcorrenciaComponent },
      { path: 'estatisticas', component: EstatisticasComponent },
      { path: 'usuarios', component: UsuarioComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
