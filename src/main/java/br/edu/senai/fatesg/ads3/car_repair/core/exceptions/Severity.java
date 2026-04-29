package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

public enum Severity {
	INFO, // Informativo
	WARNING, // Alerta (regra de negócio contornável)
	ERROR, // Erro impeditivo
	FATAL // Erro crítico de sistema
}
