import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { DialogService } from '../../layout/dialog/dialog.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Ocorrencia } from '../../models/ocorrencia';
import { OcorrenciaService } from '../../services/ocorrencia.service';
import { DominioService } from '../../services/dominio.service';
import { OcorrenciaFormComponent } from './ocorrencia-form/ocorrencia-form.component';
import { StatusFormComponent } from './status-form/status-form.component';
import { ComentarioFormComponent } from './comentario-form/comentario-form.component';
import { Comentario } from '../../models/comentario';
import { Dominio } from '../../models/dominio';

@Component({
  selector: 'app-ocorrencia',
  standalone: false,
  templateUrl: './ocorrencia.component.html',
  styleUrls: ['./ocorrencia.component.scss']
})
export class OcorrenciaComponent implements OnInit {
  @ViewChild(MatSort) sort!: MatSort;

  ocorrencias: Ocorrencia[] = [];
  totalRegistros = 0;
  pageIndex = 0;
  pageSize = 10;
  filtroTexto = '';
  filtroStatus = '';
  filtroPrioridade = '';

  statusList: Dominio[] = [];
  prioridadesList: Dominio[] = [];

  constructor(
    private ocorrenciaService: OcorrenciaService,
    private dominioService: DominioService,
    private toastrService: ToastrService,
    private dialogService: DialogService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.dominioService.buscarStatus().subscribe(data => this.statusList = data);
    this.dominioService.buscarPrioridades().subscribe(data => this.prioridadesList = data);

    this.activatedRoute.queryParamMap.subscribe(params => {
      this.pageIndex = +params.get('page')! || 0;
      this.pageSize = +params.get('size')! || 10;
      this.filtroTexto = params.get('texto') || '';
      this.filtroStatus = params.get('status') || '';
      this.filtroPrioridade = params.get('prioridade') || '';

      const sortParam = params.get('sort');
      if (sortParam) {
        const [active, direction] = sortParam.split(',');
        if (this.sort) {
          this.sort.active = active;
          this.sort.direction = direction as 'asc' | 'desc';
        }
      }

      this.carregarOcorrencias();
    });
  }

  carregarOcorrencias(): void {
    this.ocorrenciaService.listarOcorrencias({
      page: this.pageIndex,
      size: this.pageSize,
      sort: this.sort ? `${this.sort.active},${this.sort.direction}` : 'dataAbertura,',
      texto: this.filtroTexto,
      status: this.filtroStatus,
      prioridade: this.filtroPrioridade
    }).subscribe(response => {
      this.ocorrencias = response.content;
      this.totalRegistros = response.totalElements;
    });
  }

  atualizarUrl() {
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: {
        page: this.pageIndex,
        size: this.pageSize,
        texto: this.filtroTexto || null,
        status: this.filtroStatus || null,
        prioridade: this.filtroPrioridade || null,
        sort: this.sort ? `${this.sort.active},${this.sort.direction}` : 'dataAbertura,'
      },
      queryParamsHandling: 'merge'
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.atualizarUrl();
    this.carregarOcorrencias();
  }

  onSortChange(sort: Sort): void {
    this.atualizarUrl();
    this.carregarOcorrencias();
  }

  aplicarFiltros(): void {
    this.pageIndex = 0;
    this.atualizarUrl();
    this.carregarOcorrencias();
  }

  cadastrarOcorrencia(): void {
    this.dialogService.openForm({
      formComponent: OcorrenciaFormComponent,
      title: 'CADASTRAR OCORRÊNCIA',
      resources: {
        statusList: this.statusList,
        prioridadesList: this.prioridadesList
      }
    }).subscribe((result: any) => {
      if (result) {
        this.ocorrenciaService.criarOcorrencia(result).subscribe({
          next: () => {
            this.toastrService.success('Ocorrência cadastrada com sucesso!');
            this.carregarOcorrencias();
          },
          error: () => this.toastrService.error('Erro ao cadastrar ocorrência.')
        });
      }
    });
  }

  visualizarOcorrencia(ocorrencia: Ocorrencia): void {
    this.dialogService.openForm({
      formComponent: OcorrenciaFormComponent,
      title: 'VISUALIZAR OCORRÊNCIA',
      value: ocorrencia,
      resources: {
        statusList: this.statusList,
        prioridadesList: this.prioridadesList
      },
      readonly: true
    });
  }

  editarOcorrencia(ocorrencia: Ocorrencia): void {
    this.dialogService.openForm({
      formComponent: OcorrenciaFormComponent,
      title: 'EDITAR OCORRÊNCIA',
      value: ocorrencia,
      resources: {
        statusList: this.statusList,
        prioridadesList: this.prioridadesList
      }
    }).subscribe((result: any) => {
      if (result) {
        this.ocorrenciaService.atualizarOcorrencia({ ...ocorrencia, ...result }).subscribe({
          next: () => {
            this.toastrService.success('Ocorrência atualizada com sucesso!');
            this.carregarOcorrencias();
          },
          error: () => this.toastrService.error('Erro ao atualizar ocorrência.')
        });
      }
    });
  }

  gerenciarComentarios(ocorrencia: Ocorrencia): void {
    this.dialogService.openForm({
      formComponent: ComentarioFormComponent,
      title: 'GERENCIAR COMENTÁRIOS',
      value: { idOcorrencia: ocorrencia.id }
    }).subscribe((comentarios: Comentario[]) => {
      this.ocorrenciaService.criarComentariosOcorrencia(ocorrencia.id!, comentarios).subscribe({
        next: () => {
          this.toastrService.success('Comentários adicionados com sucesso!');
        },
        error: () => this.toastrService.error('Erro ao adicionar comentários.')
      });
    });
  }

  alterarStatus(ocorrencia: Ocorrencia): void {
    this.dialogService.openForm({
      formComponent: StatusFormComponent,
      title: 'ALTERAR STATUS',
      value: ocorrencia,
      resources: {
        statusList: this.statusList
      }
    }).subscribe((result: any) => {
      if (result?.status && result.status !== ocorrencia.status) {
        this.ocorrenciaService.atualizarStatus(ocorrencia.id!, result.status).subscribe({
          next: () => {
            this.toastrService.success('Status atualizado com sucesso!');
            this.carregarOcorrencias();
          },
          error: () => this.toastrService.error('Erro ao atualizar status.')
        });
      }
    });
  }

  deletarOcorrencia(ocorrencia: Ocorrencia): void {
    this.dialogService.openConfirm({
      title: 'Excluir Ocorrência',
      message: `Tem certeza que deseja deletar a ocorrência "${ocorrencia.titulo}"?`
    }).subscribe(confirmado => {
      if (confirmado) {
        this.ocorrenciaService.deletarOcorrencia(ocorrencia.id!).subscribe({
          next: () => {
            this.toastrService.success('Ocorrência excluída com sucesso!');
            this.carregarOcorrencias();
          },
          error: () => this.toastrService.error('Erro ao excluir ocorrência.')
        });
      }
    });
  }
}
