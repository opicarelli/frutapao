package com.opicarelli.frutapao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ComboCardapioSemanal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_combo_cardapio_semanal_cardapio"))
	private CardapioSemanal cardapio;

	@OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ItemComboCardapioSemanal> items = new ArrayList<>();

	@Column(nullable = false, scale = 2, precision = 11)
	private BigDecimal valor;

	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CardapioSemanal getCardapio() {
		return cardapio;
	}

	public void setCardapio(CardapioSemanal cardapio) {
		this.cardapio = cardapio;
	}

	public List<ItemComboCardapioSemanal> getItems() {
		return items;
	}

	public void setItems(List<ItemComboCardapioSemanal> items) {
		this.items = items;
	}

	public void addItem(ItemComboCardapioSemanal item) {
		item.setCombo(this);
		items.add(item);
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
