package com.ehuman.oidc.controllers;

public class ExceptionGeneratorController {
	public String generator() throws Exception{
		throw new Exception("excepcion");
	}

}
