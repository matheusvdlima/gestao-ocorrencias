import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OcorrenciaService } from '../../services/ocorrencia.service';
import { EstatisticaService } from '../../services/estatistica.service';
import { Ocorrencia } from '../../models/ocorrencia';
import { Estatistica } from '../../models/estatistica';
import { AuthService } from '../../security/auth.service';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  usuario!: string;
  ocorrencias: Ocorrencia[] = [];
  estatistica?: Estatistica;

  totalOcorrencias = 0;
  resolvidas = 0;
  criticas = 0;
  abertas = 0;
  naoResolvidas = 0;
  baixaPrioridade = 0;

  constructor(
    private ocorrenciaService: OcorrenciaService,
    private estatisticaService: EstatisticaService,
    private usuarioService: UsuarioService,
    private toastrService: ToastrService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.loggedIn$.subscribe(loggedIn => {
      if (loggedIn) {
        this.carregarOcorrencias();
        this.carregarEstatisticas();
      }
    });

    const email = this.authService.getUser()?.email || this.authService.getUserName();

    this.usuarioService.buscarPorEmail(email).subscribe({
      next: (res: Usuario) => {
        this.usuario = res.nome;
      },
      error: (err) => {
        this.toastrService.error('Erro ao buscar usuário:', err);
      }
    });
  }

  private carregarOcorrencias(): void {
    this.ocorrenciaService.listarOcorrencias({ page: 0, size: 5 }).subscribe({
      next: (dados) => this.ocorrencias = dados.content,
      error: () => this.toastrService.error("Erro ao buscar ocorrências")
    });
  }

  private carregarEstatisticas(): void {
    this.estatisticaService.buscarEstatisticas().subscribe({
      next: (e: Estatistica) => {
        this.estatistica = e;

        this.totalOcorrencias = Number(e.total) || 0;
        this.resolvidas = this.buscarValor(e.status, 'RESOLVIDA');
        this.criticas = this.buscarValor(e.prioridade, 'ALTA');
        this.abertas = this.buscarValor(e.status, 'ABERTA');
        this.naoResolvidas = this.buscarValor(e.status, 'ABERTA') +
          this.buscarValor(e.status, 'EM_ANDAMENTO');
        this.baixaPrioridade = this.buscarValor(e.prioridade, 'BAIXA');
      },
      error: () => this.toastrService.error("Erro ao buscar estatísticas")
    });
  }

  private buscarValor(lista: any[], chave: string): number {
    const item = lista.find(r => r.chave === chave);
    return item ? item.valor : 0;
  }
}
