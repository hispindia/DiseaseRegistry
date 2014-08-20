package org.openmrs.module.diseaseregistry.web.controller.program;

import org.openmrs.module.diseaseregistry.api.model.DRProgram;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProgramValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return DRProgram.class.equals(klass);
	}

	@Override
	public void validate(Object cmd, Errors error) {
		// TODO Auto-generated method stub
		DRProgram program = (DRProgram) cmd;
		
	}

}
