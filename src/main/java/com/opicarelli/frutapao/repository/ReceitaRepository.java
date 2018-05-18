package com.opicarelli.frutapao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opicarelli.frutapao.entity.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

}