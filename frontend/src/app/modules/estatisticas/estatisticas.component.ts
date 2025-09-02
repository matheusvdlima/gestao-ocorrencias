import { Component, OnInit } from '@angular/core';
import { Estatistica } from '../../models/estatistica';
import { EstatisticaService } from '../../services/estatistica.service';
import { ToastrService } from 'ngx-toastr';
import { Color, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-estatisticas',
  standalone: false,
  templateUrl: './estatisticas.component.html',
  styleUrls: ['./estatisticas.component.scss']
})
export class EstatisticasComponent implements OnInit {

  colorScheme: Color = {
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#013179', '#d88a0e', '#019147', '#c43c2d']
  };

  estatistica?: Estatistica;

  statusValores: { chave: string, valor: number }[] = [];
  prioridadeValores: { chave: string, valor: number }[] = [];

  chartStatus: any[] = [];
  chartPrioridade: any[] = [];

  view: [number, number] = [500, 300];

  constructor(
    private estatisticaService: EstatisticaService,
    private toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.carregarEstatisticas();
  }

  private carregarEstatisticas(): void {
    this.estatisticaService.buscarEstatisticas().subscribe({
      next: (e) => {
        this.estatistica = e;

        this.statusValores = [
          { chave: 'ABERTA', valor: this.buscarValor(e.status, 'ABERTA') },
          { chave: 'EM_ANDAMENTO', valor: this.buscarValor(e.status, 'EM_ANDAMENTO') },
          { chave: 'RESOLVIDA', valor: this.buscarValor(e.status, 'RESOLVIDA') },
          { chave: 'CANCELADA', valor: this.buscarValor(e.status, 'CANCELADA') },
        ];

        this.prioridadeValores = [
          { chave: 'ALTA', valor: this.buscarValor(e.prioridade, 'ALTA') },
          { chave: 'MEDIA', valor: this.buscarValor(e.prioridade, 'MEDIA') },
          { chave: 'BAIXA', valor: this.buscarValor(e.prioridade, 'BAIXA') },
        ];

        this.chartStatus = this.statusValores.map(s => ({ name: s.chave, value: s.valor }));
        this.chartPrioridade = this.prioridadeValores.map(p => ({ name: p.chave, value: p.valor }));
      },
      error: () => this.toastrService.error("Erro ao buscar estatísticas")
    });
  }

  private buscarValor(lista: any[], chave: string): number {
    const item = lista.find(r => r.chave === chave);
    return item ? item.valor : 0;
  }
}
