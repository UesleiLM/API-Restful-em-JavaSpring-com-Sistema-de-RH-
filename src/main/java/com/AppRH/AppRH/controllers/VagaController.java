package com.AppRH.AppRH.controllers;

import jakarta.persistence.criteria.Path;
import jakarta.validation.Valid;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.Repository.CandidatoRepository;
import com.AppRH.AppRH.Repository.VagaRepository;
import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;

@Controller
public class VagaController {

	@Autowired
	private VagaRepository vr;
	
	@Autowired
	private CandidatoRepository cr;

	// Cadastrar vaga
	@RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
	public String form() {
		return "vaga/form-vaga";
	}

	@RequestMapping(value = "cadastrarVaga", method = RequestMethod.POST)
	public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarVaga";
		}
		vr.save(vaga);
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
		return "redirect:/cadastrarVaga";
	}

	// Listar vaga
	@RequestMapping("/vagas")
	public ModelAndView listaVagas() {
		ModelAndView mv = new ModelAndView("vaga/lista-vaga");
		Iterable<Vaga> vagas = vr.findAll();
		mv.addObject("vagas", vagas);
		return mv;
	}

	//
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("vaga/detalhes-vaga");
		mv.addObject("vaga", vaga);
		Iterable<Candidato> candidatos = cr.findByVaga(vaga);
		mv.addObject("candidatos", candidatos);

		return mv;
	}

	// Deleta vaga
	@RequestMapping("/deletarVaga")
	public String deletarVaga(long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		vr.delete(vaga);
		return "redirect:/vagas";
	}

	public String detalhesVagaPost(@PathVariable("codigo") long codigo, @Valid Candidato candidato,
			BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "verifique os campos!");
			return "redirect:/{codigo}";
		}
		
		//RG duplicado
		
		if(cr.findByRg(candidato.getRg()) != null) {
			attributes.addFlashAttribute("mensagem erro", "RG duplicado");
			return ":/{codigo}";
		}
		Vaga vaga = vr.findByCodigo(codigo);
		candidato.setVaga(vaga);
		cr.save(candidato);
		attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	//Deleta candidato pelo RG
	
	@RequestMapping("/deletarCandidato")
	public String deletarCandidato(String rg) {
		Candidato candidato = cr.findByRg(rg);
		Vaga vaga = candidato.getVaga();
		String codigo = "" + vaga.getCodigo();
		cr.delete(candidato);
		return "redirect:/" +codigo;
		
	}
	
	//Atualização de dados
	
	//formulario de edição de vaga
	@RequestMapping(value="/editar-vaga", method = RequestMethod.GET)
	public ModelAndView editarVaga(long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("vaga", vaga);
		return mv;
	}
	
	//Update da vaga
	@RequestMapping(value="/editar-vaga", method = RequestMethod.POST)
	public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
		vr.save(vaga);
		attributes.addFlashAttribute("sucess", "Vaga alterada com sucesso!");
		long codigoLong = vaga.getCodigo();
		String codigo = "" +codigoLong;
		return "redirect:/" +codigo;
	}
	
	
}
