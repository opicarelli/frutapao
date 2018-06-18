package com.opicarelli.frutapao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemComboCardapioSemanal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_item_combo_combo"))
	private ComboCardapioSemanal combo;

	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_item_combo_item_cardapio"))
	private ItemCardapioSemanal itemCardapio;

	@Column(nullable = false)
	private Long quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ComboCardapioSemanal getCombo() {
		return combo;
	}

	public void setCombo(ComboCardapioSemanal combo) {
		this.combo = combo;
	}

	public ItemCardapioSemanal getItemCardapio() {
		return itemCardapio;
	}

	public void setItemCardapio(ItemCardapioSemanal itemCardapio) {
		this.itemCardapio = itemCardapio;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

}
