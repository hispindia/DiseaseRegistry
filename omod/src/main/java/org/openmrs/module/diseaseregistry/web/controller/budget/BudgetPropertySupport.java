package org.openmrs.module.diseaseregistry.web.controller.budget;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.math.NumberUtils;
import org.openmrs.module.diseaseregistry.api.model.Budget;


public class BudgetPropertySupport extends PropertyEditorSupport {

	@Override
    public String getAsText() {
		Budget budget = (Budget) this.getValue();
	    return budget != null ? budget.getName() : "";
    }

	@Override
    public void setAsText(String text) throws IllegalArgumentException {
		Budget budget = new Budget();
		budget.setId(NumberUtils.createInteger(text));
	    this.setValue(budget);
    }
	
	
}
