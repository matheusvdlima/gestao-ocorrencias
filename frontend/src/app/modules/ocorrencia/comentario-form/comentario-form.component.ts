import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Comentario } from '../../../models/comentario';
import { OcorrenciaService } from '../../../services/ocorrencia.service';

@Component({
  selector: 'app-comentario-form',
  standalone: false,
  templateUrl: './comentario-form.component.html',
  styleUrls: ['./comentario-form.component.scss']
})
export class ComentarioFormComponent implements OnInit {
  @Input() data!: { idOcorrencia: string };

  form!: FormGroup;
  comentarios = new MatTableDataSource<Comentario>([]);
  displayedColumns: string[] = ['dataCriacao', 'autor', 'mensagem', 'acoes'];

  constructor(
    private fb: FormBuilder,
    private ocorrenciaService: OcorrenciaService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      autor: ['', Validators.required],
      mensagem: ['', Validators.required]
    });

    if (this.data?.idOcorrencia) {
      this.ocorrenciaService
        .listarComentariosOcorrencia(this.data.idOcorrencia)
        .subscribe(res => {
          this.comentarios.data = res;
        });
    }
  }

  adicionarComentario(): void {
    if (this.form.valid) {
      const novo: Comentario = {
        idOcorrencia: this.data.idOcorrencia,
        autor: this.form.value.autor,
        mensagem: this.form.value.mensagem
      };

      this.comentarios.data = [...this.comentarios.data, novo];
      this.form.reset();
    }
  }

  removerComentario(index: number): void {
    const data = [...this.comentarios.data];
    data.splice(index, 1);
    this.comentarios.data = data;
  }

  get formValue(): Comentario[] {
    return this.comentarios.data;
  }

  get formValid(): boolean {
    return this.comentarios.data.length > 0;
  }
}
