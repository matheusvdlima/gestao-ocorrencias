import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuario } from '../../../models/usuario';

@Component({
  selector: 'app-usuario-form',
  standalone: false,
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.scss']
})
export class UsuarioFormComponent {
  form: FormGroup;
  hidePassword = true;
  isEdit!: boolean;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: { value: Usuario }
  ) {
    this.isEdit = !!data.value;

    console.log(data.value);

    this.form = this.fb.group(
      {
        nome: [data.value?.nome || '', [Validators.required, Validators.maxLength(150)]],
        email: [data.value?.email || '', [Validators.required, Validators.email, Validators.maxLength(120)]],
        perfil: [data.value?.perfil.code || '', !this.isEdit ? Validators.required : []]
      }
    );
  }
}
