package org.openmrs.module.diseaseregistry.web.controller.budget;

import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.AccountingService;
import org.openmrs.module.diseaseregistry.api.model.BudgetItem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class BudgetItemValidator implements Validator{

	@Override
    public boolean supports(Class<?> arg0) {
	    return arg0.isInstance(BudgetItem.class);
    }

	@Override
    public void validate(Object arg0, Errors error) {
	    BudgetItem item = (BudgetItem) arg0;
	    AccountingService service = Context.getService(AccountingService.class);
	    
	    if (item.getAccount() == null) {
	    	error.reject("accounting.account.required");
	    } else 	if (service.isBudgetItemOverlap(item.getAccount().getId(), item.getStartDate(), item.getEndDate())){
		    	error.reject("overlap"," Budget period is overlap");
		    }
	    
	    
	    
    }
	
}
