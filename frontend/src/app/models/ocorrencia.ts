import { Dominio } from "./dominio";

export interface Ocorrencia {
  id?: string;
  titulo: string;
  descricao: string;
  prioridade: Dominio;
  status: Dominio;
  emailResponsavel: string;
  tags: string[];
  dataAbertura?: string;
  dataAtualizacao?: string;
}

export interface OcorrenciaParametro {
  page: number,
  size: number,
  sort?: string,
  texto?: string,
  status?: string,
  prioridade?: string
}
