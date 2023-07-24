package com.ehuman.oidc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ehuman.oidc.services.ConsultaDB;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/humanSSO")
public class EmpleadoController {

	@Autowired
	private ConsultaDB consulta;



	private static final Logger LOG = LoggerFactory.getLogger(EmpleadoController.class);

	@GetMapping("/valida")
	//public String empleadoRegistrado(@RequestParam Long numEmp, @RequestParam Long numComp) {
	public String empleadoRegistrado(@RequestParam String email) {
		LOG.info("En EmpleadoController : Ingresa a empleadoRegistrado");
	
		//return consulta.getUrlToken(numEmp, numComp);
		return consulta.getUrlToken(email);
	}







		




	}
