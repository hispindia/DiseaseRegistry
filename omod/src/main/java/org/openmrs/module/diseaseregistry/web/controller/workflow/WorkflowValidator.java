package org.openmrs.module.diseaseregistry.web.controller.workflow;

import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class WorkflowValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return DRWorkflow.class.equals(klass);
	}

	@Override
	public void validate(Object cmd, Errors error) {
		// TODO Auto-generated method stub
		DRWorkflow workflow = (DRWorkflow) cmd;
		
	}

}
