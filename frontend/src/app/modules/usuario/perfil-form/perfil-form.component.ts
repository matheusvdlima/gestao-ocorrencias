import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuario } from '../../../models/usuario';

@Component({
  selector: 'app-perfil-form',
  standalone: false,
  templateUrl: './perfil-form.component.html',
  styleUrls: ['./perfil-form.component.scss']
})
export class PerfilFormComponent {
  form: FormGroup;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: { valeu: Usuario }
  ) {

    this.form = this.fb.group(
      { perfil: [data.valeu?.perfil.code || '', Validators.required] }
    );
  }
}
