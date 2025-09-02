import { Dominio } from "./dominio";

export interface Usuario {
  id?: string;
  nome: string;
  email: string;
  senha: string;
  perfil: Dominio;
  dataCriacao: string;
}

export interface UsuarioParametro {
  page: number,
  size: number,
  sort?: string,
  texto?: string,
  perfil?: string
}
