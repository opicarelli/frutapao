package com.opicarelli.frutapao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opicarelli.frutapao.entity.ItemEstoque;
import com.opicarelli.frutapao.service.EstoqueService;

@Controller
@RequestMapping(value = "/estoque")
public class EstoqueController {

	@Autowired
	private EstoqueService estoqueService;

	@GetMapping("")
	public String estoque() {
		return "estoque";
	}

	@GetMapping(value = "/search")
	@ResponseBody
	public List<ItemEstoque> search(Model model) {
		return estoqueService.findAllItemEstoque();
	}
}