package com.ehuman.oidc.services;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.ehuman.oidc.dao.EmpleadoTokenDao;
import com.ehuman.oidc.dto.EmpleadoDto;
import com.ehuman.oidc.dto.EmpleadoTokenDto;
import com.ehuman.oidc.models.EmpleadosTokenWs;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class EmpleadoTokenService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmpleadoTokenDao empeadoDao;
	@Autowired
	private ConsultaDB consulta;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaDB.class);
	
	// vefificar datos exitentes en HU_EMPLS_TOKEN_WS
		public EmpleadoTokenDto getEmpleadoToken(Long numCia, Long numEmp) {
			LOG.info("En getEmpleadoToken obtiene datos de empleado por numcia :"+numCia+ " y numEmp: " +numEmp);
			EmpleadosTokenWs empleadoT= empeadoDao.findByNumCiaAndNumEmp(numCia, numEmp);
			EmpleadoTokenDto empDto = this.modelToDto(empleadoT);
			LOG.info(empDto.toString());
			return empDto;
		}
			
			
				
		public void updateEmpleadoToken(EmpleadoTokenDto empleadoDto)	{
			LOG.info("En deleteEmpleadoToken se borro empleado de HU_EMPLS_TOKEN_WS con datos" +empleadoDto.toString() );
			List<EmpleadoDto>empDto= consulta.getEmpleado(empleadoDto.getNumEmp(), empleadoDto.getNumCia());
			List<EmpleadoDto>empExtDto = new ArrayList<>();
			List<String> username=  new ArrayList<>();
		    if(!empDto.isEmpty()) {
		    	for(EmpleadoDto e : empDto) {
		    		username.add(e.getNombre()+e.getApell_pat()+e.getApell_mat());
		    	}	
		    }
		    else {
		    	empExtDto= consulta.getEmpletadoExterno(empleadoDto.getNumEmp(), empleadoDto.getNumCia());
		    	if(!empExtDto.isEmpty()) {
		    		for(EmpleadoDto e : empExtDto) {
			    		username.add(e.getNombre()+e.getApell_pat()+e.getApell_mat());
			    	}
		    	}
		    }
		    
		    
			empleadoDto.setFechaMov(LocalDateTime.now());
			//falta generar token automatico
			if(username.size()==1) {
			for(String s : username) {
				empleadoDto.setToken(this.getJWTToken(s, 5000));
			}
			}
			
			EmpleadosTokenWs empleado= this.dtoToModel(empleadoDto);
			EmpleadosTokenWs emplToken = empeadoDao.save(this.dtoToModel(empleadoDto));
			LOG.info("Datos guardaos"+ emplToken);
			 				
			}
		
		
		public void addRegistroEmpleado(EmpleadoTokenDto empTokenDto) {
			LOG.info("En addRegistroEmpleado se tienen datos para registro de empleado en HU_EMPLS_TOKEN_WS con datos:" + empTokenDto.toString());
			
			empeadoDao.save(this.dtoToModel(empTokenDto));
			
		}
		
		
			
			
		
		// convertir model a dto
		public EmpleadoTokenDto modelToDto(EmpleadosTokenWs empToken) {
			EmpleadoTokenDto empTDto = new EmpleadoTokenDto();
			empTDto.setNumCia(empToken.getNumCia());
			empTDto.setNumEmp(empToken.getNumEmp());
			empTDto.setToken(empToken.getToken());
			empTDto.setFechaMov(empToken.getFechaMov());
			return empTDto;
		}
		
		//convertir dto a model
		public EmpleadosTokenWs dtoToModel(EmpleadoTokenDto empTokDto) {
			EmpleadosTokenWs emp = new EmpleadosTokenWs();
			emp.setNumCia(empTokDto.getNumCia());
			emp.setNumEmp(empTokDto.getNumEmp());
			emp.setToken(empTokDto.getToken());
			emp.setFechaMov(empTokDto.getFechaMov());
			return emp;
		}
		
		

		
		
		public String getJWTToken(String username, long tokenExp) {		
			String secretKey = "humanRHD";
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");		
			String token = Jwts.builder().setId("venturssotfJWT")
										 .setSubject(username)
										 .claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
										 .setIssuedAt(new Date(System.currentTimeMillis()))
										 .setExpiration(new Date(System.currentTimeMillis() + tokenExp))
										 .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
										 .compact();
			return "Bearer " + token;
		}
		
		

}
