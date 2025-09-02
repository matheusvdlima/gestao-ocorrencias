export interface Usuario {
  id?: string;
  nome: string;
  email: string;
  senha: string;
  perfis: string;
  dataCriacao: string;
}

export interface UsuarioParametro {
  page: number,
  size: number,
  sort?: string,
  texto?: string,
  perfil?: string
}
