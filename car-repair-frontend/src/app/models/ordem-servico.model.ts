export interface ordemServico{
   cliente_id?: number;
   veiculo_id?: number;
   data_entrada: Date;
   data_saida: Date;
   problema :string;
   observacoes:string;
}