package com.opicarelli.frutapao;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.opicarelli.frutapao.entity.ItemEstoque;
import com.opicarelli.frutapao.entity.UnidadeMedida;
import com.opicarelli.frutapao.service.EstoqueService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { FrutapaoConfiguration.class })
public class EstoqueServiceTest {

	@Autowired
	private EstoqueService service;

	@Test
	public void testCriaItemEstoque() {
		String nome = "Farinha Integral";
		service.criaItemEstoque(nome, UnidadeMedida.KG);
		List<ItemEstoque> allItemEstoque = service.findAllItemEstoque();
		Assert.assertTrue(allItemEstoque.size() == 1);
		Assert.assertEquals(nome, allItemEstoque.get(0).getNome());
	}

	@Test
	public void testRealizaEntrada() {
		String nome = "Farinha Integral";
		ItemEstoque itemEstoque = service.criaItemEstoque(nome, UnidadeMedida.KG);
		List<ItemEstoque> allItemEstoque = service.findAllItemEstoque();
		Assert.assertTrue(allItemEstoque.size() == 1);
		ItemEstoque findItemEstoque = allItemEstoque.get(0);
		Assert.assertEquals(itemEstoque.getNome(), findItemEstoque.getNome());

		Assert.assertTrue(BigDecimal.ZERO.compareTo(findItemEstoque.getPrecoMedio()) == 0);
		Assert.assertThat(BigDecimal.ZERO,  Matchers.comparesEqualTo(findItemEstoque.getPrecoMedio()));
		Calendar dataValidacao = Calendar.getInstance();
		dataValidacao.add(Calendar.MONTH, 3);
		BigDecimal valorUnitario = new BigDecimal(2.45).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());

		findItemEstoque = service.findItemEstoque(itemEstoque.getId());
		Assert.assertThat(valorUnitario,  Matchers.comparesEqualTo(findItemEstoque.getPrecoMedio()));

		valorUnitario = new BigDecimal(2.55).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		service.realizaEntrada(itemEstoque, 100.0, valorUnitario, dataValidacao.getTime());

		findItemEstoque = service.findItemEstoque(itemEstoque.getId());
		BigDecimal novoPrecoMedio = new BigDecimal(2.50).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		Assert.assertThat(novoPrecoMedio,  Matchers.comparesEqualTo(findItemEstoque.getPrecoMedio()));
	}
}
