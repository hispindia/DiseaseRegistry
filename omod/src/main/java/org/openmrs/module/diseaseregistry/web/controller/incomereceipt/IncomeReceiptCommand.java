package org.openmrs.module.diseaseregistry.web.controller.incomereceipt;

import org.openmrs.module.diseaseregistry.api.model.FiscalPeriod;
import org.openmrs.module.diseaseregistry.api.model.IncomeReceipt;


public class IncomeReceiptCommand {
	private IncomeReceipt incomeReceipt;
	private FiscalPeriod period;
	
    public IncomeReceipt getIncomeReceipt() {
    	return incomeReceipt;
    }
	
    public void setIncomeReceipt(IncomeReceipt incomeReceipt) {
    	this.incomeReceipt = incomeReceipt;
    }
	
    public FiscalPeriod getPeriod() {
    	return period;
    }
	
    public void setPeriod(FiscalPeriod period) {
    	this.period = period;
    }
}
