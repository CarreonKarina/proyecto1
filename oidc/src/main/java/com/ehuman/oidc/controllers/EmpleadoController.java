package com.ehuman.oidc.controllers;

/* Consulta a bd para confirmar empleados registrados*/
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ehuman.oidc.dto.EmpleadoDto;
import com.ehuman.oidc.services.ConsultaDB;



@CrossOrigin(origins = "*")
@RestController
public class EmpleadoController {
	
	@Autowired
	private ConsultaDB consutla;
	
	private static final Logger LOG = LoggerFactory.getLogger(EmpleadoController.class);
	
	@GetMapping("/valida")
	public String empleadoRegistrado(@RequestParam Long numEmp, @RequestParam Long numComp) {
		LOG.info("En EmpleadoController : Ingresa a empleadoRegistrado");
		return recuperarRegistro(numEmp,numComp);
		
	}
	
	
	public String recuperarRegistro(Long numEmpleado, Long numeroCompania ) {
		LOG.info("Ingresa a recuperarRegistro");
	String responseUrlRedirectWorkSocial = "";
		
	List<EmpleadoDto>empleadoDto= consutla.getEmpleado(numEmpleado, numeroCompania);
	
	if(!empleadoDto.isEmpty()) {
		
	responseUrlRedirectWorkSocial = "Empleado  interno encontrado " + empleadoDto.get(0).getNum_cia()+" " + empleadoDto.get(0).getNum_emp()+" "+ empleadoDto.get(0).getApell_pat();
	
	}else {
	
			//if(empleadoDto.isEmpty()) {
		
				List<EmpleadoDto> empleadoExterno =consutla.getEmpletadoExterno(numEmpleado, numeroCompania);
				if(empleadoExterno.isEmpty()) {
					responseUrlRedirectWorkSocial = "Sin correo electronico";
				}
				else {
					responseUrlRedirectWorkSocial = "Empleado externo encontrado " + empleadoExterno.get(0).getNum_cia()+" " + empleadoExterno.get(0).getNum_emp()+""+ empleadoExterno.get(0).getApell_mat();
				LOG.info(responseUrlRedirectWorkSocial);
				}
			//}
	
	}
	
	return responseUrlRedirectWorkSocial;
	
	
	}
	
	
	

}
