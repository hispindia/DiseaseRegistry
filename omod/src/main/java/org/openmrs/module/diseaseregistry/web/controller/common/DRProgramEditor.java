package org.openmrs.module.diseaseregistry.web.controller.common;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;

public class DRProgramEditor extends PropertyEditorSupport {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public void setAsText(String text) throws IllegalArgumentException {
		
		if(StringUtils.isNotBlank(text)) {
			DRProgram program = Context.getService(DiseaseRegistryService.class).getProgram(Integer.parseInt(text));
			setValue(program);	
		} else {
			log.error("Unable to find program");
			setValue(null);			
		}
		
	}
	
	/**
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	public String getAsText() {
		DRProgram program = (DRProgram) getValue();
		if (program == null) {
			return "";
		} else {
			return program.toString();
		}
	}

}
