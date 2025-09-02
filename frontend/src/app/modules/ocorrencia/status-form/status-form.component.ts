import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ocorrencia } from '../../../models/ocorrencia';

@Component({
  selector: 'app-status-form',
  standalone: false,
  templateUrl: './status-form.component.html',
  styleUrls: ['./status-form.component.scss']
})
export class StatusFormComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: Ocorrencia
  ) {
    this.form = this.fb.group({
      status: [data?.status || '', Validators.required]
    });
  }
}
