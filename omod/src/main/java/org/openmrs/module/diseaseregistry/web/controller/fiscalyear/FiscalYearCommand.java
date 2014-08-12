package org.openmrs.module.diseaseregistry.web.controller.fiscalyear;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.openmrs.module.diseaseregistry.api.model.FiscalPeriod;
import org.openmrs.module.diseaseregistry.api.model.FiscalYear;

public class FiscalYearCommand {
	
	private FiscalYear fiscalYear;
	
	@SuppressWarnings("unchecked")
    private List<FiscalPeriod> periods = LazyList.decorate(new ArrayList<FiscalPeriod>(),
	    FactoryUtils.instantiateFactory(FiscalPeriod.class));
	
	public FiscalYear getFiscalYear() {
		return fiscalYear;
	}
	
	public void setFiscalYear(FiscalYear fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	public List<FiscalPeriod> getPeriods() {
		return periods;
	}
	
	public void setPeriods(List<FiscalPeriod> periods) {
		this.periods = periods;
	}
}
