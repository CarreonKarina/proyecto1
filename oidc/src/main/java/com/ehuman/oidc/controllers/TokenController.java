package com.ehuman.oidc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehuman.oidc.dto.EmpleadoTokenDto;

import com.ehuman.oidc.services.EmpleadoTokenService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/registra")
public class TokenController {
	
	@Autowired
	private EmpleadoTokenService empleadoService;
	
@PostMapping("/empleado")	
public EmpleadoTokenDto addEmpleadoToken(@RequestBody EmpleadoTokenDto empleadoDto) {
	if(empleadoDto != null) {
	
	//verificar que exista en empleado en tabla con token
    EmpleadoTokenDto empleadoTDto = empleadoService.getEmpleadoToken(empleadoDto.getNumCia(), empleadoDto.getNumEmp());
    
    //borramos datos si ya estan en la tabla
    if(empleadoTDto != null) {
    	
    	empleadoService.updateEmpleadoToken(empleadoDto);
    	
    }else {
    	empleadoService.addRegistroEmpleado(empleadoTDto);
    
    }
	}
    return empleadoDto;
    }



	
	

}
