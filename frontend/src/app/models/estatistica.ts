export interface Estatistica {
    total: string,
    status: Resultado[],
    prioridade: Resultado[]
}

interface Resultado {
    chave: string,
    valor: number
}