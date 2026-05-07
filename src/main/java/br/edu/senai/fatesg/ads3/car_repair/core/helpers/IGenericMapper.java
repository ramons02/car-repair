package br.edu.senai.fatesg.ads3.car_repair.core.helpers;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;


public interface IGenericMapper<E extends BaseModel, D extends BaseDTO> {
	// Converte de Entidade para DTO (Saída para o Front)
	D toDto(E entity);

	// Converte de DTO para Entidade (Entrada do Front)
	E toEntity(D dto);

	// Converte listas
	default List<D> toDtoList(List<E> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}

	// Converte páginas (Essencial para o PWA)
	default Page<D> toDtoPage(Page<E> page) {
		if (page == null)
			return null;
		return page.map(this::toDto);
	}
}
