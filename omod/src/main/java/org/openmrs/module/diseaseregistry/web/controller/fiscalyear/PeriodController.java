package org.openmrs.module.diseaseregistry.web.controller.fiscalyear;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.AccountingService;
import org.openmrs.module.diseaseregistry.api.model.ExpenseBalance;
import org.openmrs.module.diseaseregistry.api.model.FiscalPeriod;
import org.openmrs.module.diseaseregistry.api.model.FiscalYear;
import org.openmrs.module.diseaseregistry.api.model.GeneralStatus;
import org.openmrs.module.diseaseregistry.api.model.IncomeBalance;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PeriodController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@ModelAttribute("listYears")
	public Collection<FiscalYear> registerListFiscalYears(){
		return Context.getService(AccountingService.class).getListFiscalYear(null);
	}
	
	@RequestMapping(value="/module/accounting/closePeriod.htm",method=RequestMethod.POST)
	public String closePeriodSave(@RequestParam("periodId") Integer periodId, 
	                              @RequestParam("nextPeriodId") Integer nextPeriodId, 
	                              @RequestParam("resetBalance") Boolean resetBalance) {
		
		AccountingService  service = Context.getService(AccountingService.class);
		service.closePeriod(periodId, nextPeriodId, resetBalance);
		
		
		return "redirect:/module/accounting/period.list";
	}
	
	@RequestMapping(value="/module/accounting/closePeriod.htm",method=RequestMethod.GET)
	public String closePeriodView(@RequestParam("periodId") Integer periodId ,Model model) {
		if (periodId == null) {
			return "redirect:/module/accounting/period.list";
		} else {
			AccountingService  service = Context.getService(AccountingService.class);
			FiscalPeriod period = service.getFiscalPeriod(periodId);
			List<IncomeBalance> accBalances = service.listActiveAccountBalance(period);
			List<ExpenseBalance> expBalances = service.listActiveExpenseBalance(period);
			
			List<FiscalPeriod> periods = period.getFiscalYear().getPeriods();
			Iterator<FiscalPeriod> itr = periods.iterator();
			while (itr.hasNext()) {
				FiscalPeriod p = itr.next();
				if (p.getStatus().equals(GeneralStatus.CLOSED)) {
					itr.remove();
				}
			}
			periods.remove(period);
			
			model.addAttribute("period",period);
			model.addAttribute("listPeriods",periods);
			model.addAttribute("accBalances",accBalances);
			model.addAttribute("expBalances",expBalances);
			return "/module/accounting/period/closeForm";
		}
	}
	
	@RequestMapping(value="/module/accounting/period.list",method=RequestMethod.GET)
	public String listPeriods(@RequestParam(value="yearId",required=false) Integer yearId, Model model){
		if (yearId != null ) {
			FiscalYear year = Context.getService(AccountingService.class).getFiscalYear(yearId);
			if (year != null) {
				model.addAttribute("periods",year.getPeriods());
			}
		}
		return "/module/accounting/period/listPeriods";
	}
}
