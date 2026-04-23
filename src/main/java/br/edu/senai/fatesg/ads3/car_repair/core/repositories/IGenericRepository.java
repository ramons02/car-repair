/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.core.repositories;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author Clayton
 * @param <E>
 */
@NoRepositoryBean
public interface IGenericRepository <E extends BaseModel>  extends JpaRepository<E, UUID> {
    Optional<E> findByIdAndAtivoTrue(UUID id);
    Page<E> findAllByAtivoTrue(Pageable pageable);
}
