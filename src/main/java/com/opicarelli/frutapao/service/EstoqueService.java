package com.opicarelli.frutapao.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opicarelli.frutapao.dto.ItemReceitaDto;
import com.opicarelli.frutapao.entity.ItemEntrada;
import com.opicarelli.frutapao.entity.ItemEstoque;
import com.opicarelli.frutapao.entity.ItemReceita;
import com.opicarelli.frutapao.entity.Produto;
import com.opicarelli.frutapao.entity.Receita;
import com.opicarelli.frutapao.entity.UnidadeMedida;
import com.opicarelli.frutapao.repository.ItemEntradaRepository;
import com.opicarelli.frutapao.repository.ItemEstoqueRepository;
import com.opicarelli.frutapao.repository.ReceitaRepository;

@Service
public class EstoqueService {

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;

	@Autowired
	private ItemEntradaRepository itemEntradaRepository;

	@Autowired
	private ReceitaRepository receitaRepository;

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
		BigDecimal multiplicand = new BigDecimal(quantidade).setScale(3, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal valorTotal = valorUnitario.multiply(multiplicand);
		entrada.setValorTotal(valorTotal);
		entrada.setDataValidade(dataValidade);
		entrada.setData(new Date());
		entrada = itemEntradaRepository.save(entrada);

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
				BigDecimal quantidadeRestanteBigDecimal = new BigDecimal(quantidadeRestante).setScale(3,
						BigDecimal.ROUND_HALF_DOWN);
				BigDecimal valorTotalRestante = itemEntrada.getValorUnitario().multiply(quantidadeRestanteBigDecimal);
				valorTotal = valorTotal.add(valorTotalRestante);
				quantidadeTotal += quantidadeRestante;
			}
			BigDecimal quantidadeTotalBigDecimal = new BigDecimal(quantidadeTotal).setScale(3,
					BigDecimal.ROUND_HALF_DOWN);
			precoMedio = valorTotal.divide(quantidadeTotalBigDecimal, 2, BigDecimal.ROUND_HALF_DOWN);
		}
		ItemEstoque itemEstoque = itemEstoqueRepository.findOne(idItemEstoque);
		itemEstoque.setPrecoMedio(precoMedio);
		itemEstoqueRepository.save(itemEstoque);
	}

	public ItemReceitaDto criaItemReceitaDto(Long idItemEstoque, Double quantidade, Integer ordem) {
		ItemReceitaDto dto = new ItemReceitaDto();
		ItemEstoque itemEstoque = itemEstoqueRepository.findOne(idItemEstoque);
		dto.setOrdem(ordem);
		dto.setItemEstoque(itemEstoque);
		dto.setQuantidade(quantidade);
		return dto;
	}

	public Receita criaReceita(String nome, UnidadeMedida unidadeMedida, Double peso, List<ItemReceitaDto> items) {
		Receita receita = new Receita();
		Produto produtoGerado = new Produto();
		produtoGerado.setNome(nome);
		produtoGerado.setUnidadeMedida(unidadeMedida);
		produtoGerado.setPeso(peso);
		receita.setProdutoGerado(produtoGerado);

		for (ItemReceitaDto dto : items) {
			ItemReceita itemReceita = new ItemReceita();
			itemReceita.setOrdem(dto.getOrdem());
			itemReceita.setItemEstoque(dto.getItemEstoque());
			itemReceita.setQuantidade(dto.getQuantidade());
			receita.addItem(itemReceita);
		}
		return receitaRepository.save(receita);
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

	public ItemEstoque findItemEstoqueByNome(String nome) {
		return itemEstoqueRepository.findItemEstoqueByNome(nome);
	}
}
