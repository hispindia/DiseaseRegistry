package org.openmrs.module.diseaseregistry.web.controller;

import org.openmrs.Program;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProgramValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return Program.class.equals(klass);
	}

	@Override
	public void validate(Object cmd, Errors error) {
		// TODO Auto-generated method stub
		Program program = (Program) cmd;
		
	}

}
