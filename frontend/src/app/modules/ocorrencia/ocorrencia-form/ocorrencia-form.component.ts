import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';
import { Ocorrencia } from '../../../models/ocorrencia';

@Component({
  selector: 'app-ocorrencia-form',
  standalone: false,
  templateUrl: './ocorrencia-form.component.html',
  styleUrls: ['./ocorrencia-form.component.scss']
})
export class OcorrenciaFormComponent {
  form: FormGroup;
  isEdit!: boolean;

  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: { value: Ocorrencia}
  ) {
    this.isEdit = !!data;

    this.form = this.fb.group({
      titulo: [data.value?.titulo || '', [Validators.required, Validators.maxLength(120)]],
      descricao: [data.value?.descricao || '', [Validators.required, Validators.maxLength(500)]],
      status: [data.value?.status.code || 'ABERTA', Validators.required],
      prioridade: [data.value?.prioridade.code || 'MEDIA', Validators.required],
      dataCriacao: [data.value?.dataAbertura || new Date(), Validators.required],
      emailResponsavel: [data.value?.emailResponsavel || '', [Validators.required, Validators.email]],
      tags: this.fb.array(data.value?.tags?.map((t: string) => this.fb.control(t)) || [])
    });
  }

  get tags(): FormArray {
    return this.form.get('tags') as FormArray;
  }

  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.tags.push(this.fb.control(value));
    }
    event.chipInput!.clear();
  }

  removeTag(index: number): void {
    this.tags.removeAt(index);
  }
}
