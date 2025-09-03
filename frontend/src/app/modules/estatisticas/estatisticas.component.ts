import { Component, OnInit } from '@angular/core';
import { Estatistica } from '../../models/estatistica';
import { EstatisticaService } from '../../services/estatistica.service';
import { ToastrService } from 'ngx-toastr';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { DominioService } from '../../services/dominio.service';
import { Dominio } from '../../models/dominio';

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

  statusList: Dominio[] = [];
  prioridadesList: Dominio[] = [];

  constructor(
    private estatisticaService: EstatisticaService,
    private dominioService: DominioService,
    private toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.carregarEstatisticas();
  }

  private carregarEstatisticas(): void {
    this.dominioService.buscarStatus().subscribe(data => this.statusList = data);
    this.dominioService.buscarPrioridades().subscribe(data => this.prioridadesList = data);

    this.estatisticaService.buscarEstatisticas().subscribe({
      next: (e) => {
        this.estatistica = e;

        this.statusValores = this.statusList.map(d => ({
          chave: d.label,
          valor: this.buscarValor(e.status, d.code)
        }));

        this.prioridadeValores = this.prioridadesList.map(d => ({
          chave: d.label,
          valor: this.buscarValor(e.prioridade, d.code)
        }));

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
