package com.opicarelli.frutapao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.opicarelli.frutapao.entity.ItemEntrada;

@Repository
public interface ItemEntradaRepository extends JpaRepository<ItemEntrada, Long> {

	@Query("FROM ItemEntrada WHERE itemEstoque.id = :idItemEstoque AND quantidade > 0")
	public List<ItemEntrada> findAllItemEntradaValida(@Param("idItemEstoque") Long idItemEstoque);
}