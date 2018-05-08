package com.opicarelli.frutapao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Receita implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_receita_produto_gerado"))
	private Produto produtoGerado;

	@OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ItemReceita> items = new ArrayList<>();

	@Transient
	public BigDecimal getCustoMedioMateriaPrima() {
		BigDecimal custo = BigDecimal.ZERO;
		for (ItemReceita itemReceita : items) {
			custo = custo.add(itemReceita.getCustoMedio());
		}
		return custo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProdutoGerado() {
		return produtoGerado;
	}

	public void setProdutoGerado(Produto produtoGerado) {
		this.produtoGerado = produtoGerado;
	}

	public List<ItemReceita> getItems() {
		return items;
	}

	public void setItems(List<ItemReceita> items) {
		this.items = items;
	}

}
