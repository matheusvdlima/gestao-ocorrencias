import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ocorrencia } from '../../../models/ocorrencia';
import { Dominio } from '../../../models/dominio';

type DialogData = {
  value: Ocorrencia;
  resources?: any;
  readonly?: boolean;
};
@Component({
  selector: 'app-status-form',
  standalone: false,
  templateUrl: './status-form.component.html',
  styleUrls: ['./status-form.component.scss']
})
export class StatusFormComponent {
  form: FormGroup;

  statusList: Dominio[];

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {
    this.statusList = data.resources.statusList ?? [];

    this.form = this.fb.group({
      status: [data.value?.status.code || '', Validators.required]
    });
  }
}
