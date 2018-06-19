package com.opicarelli.frutapao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CardapioSemanal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataDe;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAte;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date data;

	@OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ItemCardapioSemanal> items = new ArrayList<>();

	@OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ComboCardapioSemanal> combos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<ItemCardapioSemanal> getItems() {
		return items;
	}

	public void setItems(List<ItemCardapioSemanal> items) {
		this.items = items;
	}

	public void addItem(ItemCardapioSemanal item) {
		item.setCardapio(this);
		items.add(item);
	}

	public List<ComboCardapioSemanal> getCombos() {
		return combos;
	}

	public void setCombos(List<ComboCardapioSemanal> combos) {
		this.combos = combos;
	}

	public void addItem(ComboCardapioSemanal item) {
		item.setCardapio(this);
		combos.add(item);
	}

}
