package org.openmrs.module.diseaseregistry.web.controller.common;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;

public class ConceptWordEditor extends PropertyEditorSupport {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public void setAsText(String text) throws IllegalArgumentException {
		
		if(StringUtils.isNotBlank(text)) {
			Concept concept = Context.getConceptService().getConcept(text);
			setValue(concept);	
		} else {
			log.error("Unable to find concept");
			setValue(null);			
		}
		
	}
	
	/**
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	public String getAsText() {
		Concept concept = (Concept) getValue();
		if (concept == null) {
			return "";
		} else {
			return concept.getName().toString();
		}
	}

}
