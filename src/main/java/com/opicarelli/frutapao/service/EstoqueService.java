package com.opicarelli.frutapao.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opicarelli.frutapao.entity.ItemEntrada;
import com.opicarelli.frutapao.entity.ItemEstoque;
import com.opicarelli.frutapao.entity.UnidadeMedida;
import com.opicarelli.frutapao.repository.ItemEntradaRepository;
import com.opicarelli.frutapao.repository.ItemEstoqueRepository;

@Service
public class EstoqueService {

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;

	@Autowired
	private ItemEntradaRepository itemEntradaRepository;

	public ItemEstoque criaItemEstoque(String nome, UnidadeMedida unidadeMedida) {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setNome(nome);
		itemEstoque.setUnidadeMedida(unidadeMedida);
		itemEstoque.setPrecoMedio(BigDecimal.ZERO);
		itemEstoqueRepository.save(itemEstoque);
		return itemEstoque;
	}

	public ItemEntrada realizaEntrada(ItemEstoque itemEstoque, Double quantidade, BigDecimal valorUnitario,
			Date dataValidade) {
		ItemEntrada entrada = new ItemEntrada();
		entrada.setItemEstoque(itemEstoque);
		entrada.setQuantidade(quantidade);
		entrada.setValorUnitario(valorUnitario);
		BigDecimal valorTotal = valorUnitario.multiply(new BigDecimal(quantidade));
		entrada.setValorTotal(valorTotal);
		entrada.setDataValidade(dataValidade);
		entrada.setData(new Date());
		itemEntradaRepository.save(entrada);

		calcularPrecoMedio(itemEstoque.getId());

		return entrada;
	}

	public void calcularPrecoMedio(Long idItemEstoque) {
		List<ItemEntrada> entradas = findAllItemEntradaValida(idItemEstoque);
		int qtdeEntradas = entradas.size();
		BigDecimal precoMedio = BigDecimal.ZERO;
		if (qtdeEntradas > 0) {
			BigDecimal valorTotal = BigDecimal.ZERO;
			Double quantidadeTotal = Double.valueOf(0);
			for (ItemEntrada itemEntrada : entradas) {
				Double quantidadeRestante = itemEntrada.getQuantidade();
				BigDecimal quantidadeRestanteBigDecimal = new BigDecimal(quantidadeRestante);
				BigDecimal valorTotalRestante = itemEntrada.getValorUnitario().multiply(quantidadeRestanteBigDecimal);
				valorTotal = valorTotal.add(valorTotalRestante);
				quantidadeTotal += quantidadeRestante;
			}
			BigDecimal quantidadeTotalBigDecimal = new BigDecimal(quantidadeTotal);
			precoMedio = valorTotal.divide(quantidadeTotalBigDecimal, 2, BigDecimal.ROUND_HALF_DOWN);
		}
		ItemEstoque itemEstoque = itemEstoqueRepository.findOne(idItemEstoque);
		itemEstoque.setPrecoMedio(precoMedio);
		itemEstoqueRepository.save(itemEstoque);
	}

	public List<ItemEntrada> findAllItemEntradaValida(Long idItemEstoque) {
		List<ItemEntrada> entradas = itemEntradaRepository.findAllItemEntradaValida(idItemEstoque);
		return entradas;
	}

	public List<ItemEstoque> findAllItemEstoque() {
		return itemEstoqueRepository.findAll();
	}

	public ItemEstoque findItemEstoque(Long id) {
		return itemEstoqueRepository.findOne(id);
	}
}
