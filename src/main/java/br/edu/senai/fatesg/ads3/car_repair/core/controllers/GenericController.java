package br.edu.senai.fatesg.ads3.car_repair.core.controllers;


import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import br.edu.senai.fatesg.ads3.car_repair.core.services.IGenericService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * E: Entity (Entidade de Banco)
 * D: DTO (Data Transfer Object)
 * S: Service especializado
 * M: Mapper especializado
 * @param <E>
 * @param <D>
 * @param <S>
 * @param <M>
 */
public abstract class GenericController<
    E extends BaseModel, 
    D extends BaseDTO, 
    S extends IGenericService<E, ?, ?>, 
    M extends IGenericMapper<E, D>> {

    @Autowired
    protected S service;

    @Autowired
    protected M mapper;

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        E entity = service.findByIdActive(id);
        D dto = mapper.toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        Page<E> entities = service.findAllActive(pageable);
        Page<D> dtos = mapper.toDtoPage(entities);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        E saved = service.insert(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        entity.setId(id); // Garante que o ID da URL seja o ID processado
        E updated = service.update(entity);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok("Registro removido com sucesso.");
    }
}
