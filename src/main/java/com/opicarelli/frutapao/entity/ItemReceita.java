package com.opicarelli.frutapao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ItemReceita implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double quantidade;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_item_receita_item_estoque"))
	private ItemEstoque itemEstoque;

	@Column(nullable = false)
	private Integer ordem;

	@Column(nullable = false)
	private String descricao;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_item_receita_receita"))
	private Receita receita;

	@Transient
	public BigDecimal getCustoMedio() {
		BigDecimal multiplicand = new BigDecimal(quantidade).setScale(3, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal result = itemEstoque.getPrecoMedio().multiply(multiplicand);
		return result.setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

}
