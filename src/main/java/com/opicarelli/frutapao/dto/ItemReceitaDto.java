package com.opicarelli.frutapao.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.opicarelli.frutapao.entity.ItemEstoque;

@Entity
public class ItemReceitaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Double quantidade;

	private ItemEstoque itemEstoque;

	@Column(nullable = false)
	private Integer ordem;

	@Column(nullable = false)
	private String descricao;

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public ItemEstoque getItemEstoque() {
		return itemEstoque;
	}

	public void setItemEstoque(ItemEstoque itemEstoque) {
		this.itemEstoque = itemEstoque;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
