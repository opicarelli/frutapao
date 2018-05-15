package com.opicarelli.frutapao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.opicarelli.frutapao.entity.ItemEstoque;
import com.opicarelli.frutapao.entity.ItemReceita;
import com.opicarelli.frutapao.entity.Produto;
import com.opicarelli.frutapao.entity.Receita;
import com.opicarelli.frutapao.entity.UnidadeMedida;
import com.opicarelli.frutapao.service.EstoqueService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { FrutapaoConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstoqueServiceTest {

	@Autowired
	private EstoqueService service;

	@Test
	public void test1_criaItemEstoque() {
		String nome = "Farinha integral";
		ItemEstoque itemEstoque = service.criaItemEstoque(nome, UnidadeMedida.KG);
		ItemEstoque findItemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertEquals(itemEstoque.getNome(), findItemEstoque.getNome());

		nome = "Linhaca marrom";
		itemEstoque = service.criaItemEstoque(nome, UnidadeMedida.KG);
		findItemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertEquals(itemEstoque.getNome(), findItemEstoque.getNome());

		nome = "Semente girassol";
		itemEstoque = service.criaItemEstoque(nome, UnidadeMedida.KG);
		findItemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertEquals(itemEstoque.getNome(), findItemEstoque.getNome());

		nome = "Aveia em flocos";
		itemEstoque = service.criaItemEstoque(nome, UnidadeMedida.KG);
		findItemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertEquals(itemEstoque.getNome(), findItemEstoque.getNome());
	}

	/**
	 * Farinha integral: +100kg; R$ 2,45 unidade; Total R$ 245,00 <br />
	 * Linhaca marrom: +100kg; R$ 7,00 unidade; Total R$ 700,00 <br />
	 * Semente girassol: +100kg; R$ 16,50 unidade; Total R$ 1650,00 <br />
	 * Aveia em flocos: +100kg; R$ 8,50 unidade; Total R$ 850,00 <br />
	 */
	@Test
	public void test2_realizaEntradaInicial() {
		// Realiza entrada
		String nome = "Farinha integral";
		ItemEstoque itemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertTrue(BigDecimal.ZERO.compareTo(itemEstoque.getPrecoMedio()) == 0);
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));
		Calendar dataValidacao = Calendar.getInstance();
		dataValidacao.add(Calendar.MONTH, 3);
		BigDecimal valorUnitario = new BigDecimal(2.45).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());
		// Valida
		itemEstoque = service.findItemEstoque(itemEstoque.getId());
		Assert.assertThat(valorUnitario, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));

		// Realiza entrada
		nome = "Linhaca marrom";
		itemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertTrue(BigDecimal.ZERO.compareTo(itemEstoque.getPrecoMedio()) == 0);
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));
		dataValidacao = Calendar.getInstance();
		dataValidacao.add(Calendar.MONTH, 3);
		valorUnitario = new BigDecimal(7.00).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());
		// Valida
		itemEstoque = service.findItemEstoque(itemEstoque.getId());
		Assert.assertThat(valorUnitario, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));

		// Realiza entrada
		nome = "Semente girassol";
		itemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertTrue(BigDecimal.ZERO.compareTo(itemEstoque.getPrecoMedio()) == 0);
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));
		dataValidacao = Calendar.getInstance();
		dataValidacao.add(Calendar.MONTH, 3);
		valorUnitario = new BigDecimal(16.50).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());
		// Valida
		itemEstoque = service.findItemEstoque(itemEstoque.getId());
		Assert.assertThat(valorUnitario, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));

		// Realiza entrada
		nome = "Aveia em flocos";
		itemEstoque = service.findItemEstoqueByNome(nome);
		Assert.assertTrue(BigDecimal.ZERO.compareTo(itemEstoque.getPrecoMedio()) == 0);
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));
		dataValidacao = Calendar.getInstance();
		dataValidacao.add(Calendar.MONTH, 3);
		valorUnitario = new BigDecimal(8.50).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());
		// Valida
		itemEstoque = service.findItemEstoque(itemEstoque.getId());
		Assert.assertThat(valorUnitario, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));
	}

	/**
	 * Farinha integral: +100kg; R$ 2,55 unidade; Total R$ 255,00 <br />
	 * Novo preco medio: R$ 2,50
	 */
	@Test
	public void test3_realizaEntrada_novoPreco() {
		String nome = "Farinha integral";
		ItemEstoque itemEstoque = service.findItemEstoqueByNome(nome);

		// Realiza entrada
		Calendar dataValidacao = Calendar.getInstance();
		dataValidacao.add(Calendar.MONTH, 3);
		BigDecimal valorUnitario = new BigDecimal(2.55).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());
		// Valida
		itemEstoque = service.findItemEstoque(itemEstoque.getId());
		BigDecimal novoPrecoMedio = new BigDecimal(2.50).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		Assert.assertThat(novoPrecoMedio, Matchers.comparesEqualTo(itemEstoque.getPrecoMedio()));
	}

	@Test
	public void test4_criaReceita() {
		// TODO Levar implementacao para servico criaReceita
		Receita receitaPaoIntegralMultiGrao = new Receita();
		Produto produtoGerado = new Produto();
		produtoGerado.setNome("Pao integral multigrao");
		receitaPaoIntegralMultiGrao.setProdutoGerado(produtoGerado);

		// 550g Farinha integral
		ItemEstoque itemEstoqueFarinhaIntegral = service.findItemEstoqueByNome("Farinha integral");
		ItemReceita itemReceitaFarinhaIntegral = new ItemReceita();
		itemReceitaFarinhaIntegral.setOrdem(1);
		itemReceitaFarinhaIntegral.setItemEstoque(itemEstoqueFarinhaIntegral);
		itemReceitaFarinhaIntegral.setQuantidade(0.550);
		receitaPaoIntegralMultiGrao.addItem(itemReceitaFarinhaIntegral);
		Assert.assertThat(new BigDecimal(1.37).setScale(2, BigDecimal.ROUND_HALF_DOWN),
				Matchers.comparesEqualTo(itemReceitaFarinhaIntegral.getCustoMedio()));

		// 5g Linhaca marrom
		ItemEstoque itemEstoqueLinhacaMarrom = service.findItemEstoqueByNome("Linhaca marrom");
		ItemReceita itemReceitaLinhacaMarrom = new ItemReceita();
		itemReceitaLinhacaMarrom.setOrdem(2);
		itemReceitaLinhacaMarrom.setItemEstoque(itemEstoqueLinhacaMarrom);
		itemReceitaLinhacaMarrom.setQuantidade(0.005);
		receitaPaoIntegralMultiGrao.addItem(itemReceitaLinhacaMarrom);
		Assert.assertThat(new BigDecimal(0.03).setScale(2, BigDecimal.ROUND_HALF_DOWN),
				Matchers.comparesEqualTo(itemReceitaLinhacaMarrom.getCustoMedio()));

		// 30g Semente girassol
		ItemEstoque itemEstoqueSementeGirassol = service.findItemEstoqueByNome("Semente girassol");
		ItemReceita itemReceitaSementeGirassol = new ItemReceita();
		itemReceitaSementeGirassol.setOrdem(3);
		itemReceitaSementeGirassol.setItemEstoque(itemEstoqueSementeGirassol);
		itemReceitaSementeGirassol.setQuantidade(0.030);
		receitaPaoIntegralMultiGrao.addItem(itemReceitaSementeGirassol);
		Assert.assertThat(new BigDecimal(0.49).setScale(2, BigDecimal.ROUND_HALF_DOWN),
				Matchers.comparesEqualTo(itemReceitaSementeGirassol.getCustoMedio()));

		// 120g Aveia em flocos
		ItemEstoque itemEstoqueAveiaEmFlocos = service.findItemEstoqueByNome("Aveia em flocos");
		ItemReceita itemReceitaAveiaEmFlocos = new ItemReceita();
		itemReceitaAveiaEmFlocos.setOrdem(4);
		itemReceitaAveiaEmFlocos.setItemEstoque(itemEstoqueAveiaEmFlocos);
		itemReceitaAveiaEmFlocos.setQuantidade(0.120);
		receitaPaoIntegralMultiGrao.addItem(itemReceitaAveiaEmFlocos);
		Assert.assertThat(new BigDecimal(1.02).setScale(2, BigDecimal.ROUND_HALF_DOWN),
				Matchers.comparesEqualTo(itemReceitaAveiaEmFlocos.getCustoMedio()));
	}
}
