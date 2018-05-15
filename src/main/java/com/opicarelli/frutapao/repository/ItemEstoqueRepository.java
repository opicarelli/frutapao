package com.opicarelli.frutapao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.opicarelli.frutapao.entity.ItemEstoque;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {

	@Query("FROM ItemEstoque WHERE nome = :nome")
	public ItemEstoque findItemEstoqueByNome(@Param("nome") String nome);
}