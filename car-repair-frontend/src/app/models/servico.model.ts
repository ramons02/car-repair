export interface Servico{
    ordem_servico_id?: number;
    descricao: string;
    data_hora_servico: string | Date;
    data_hora_termino: string | Date;
    mecanico: string;
    valor: number;
}