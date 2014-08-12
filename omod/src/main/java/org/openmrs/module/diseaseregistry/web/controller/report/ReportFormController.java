package org.openmrs.module.diseaseregistry.web.controller.report;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.AccountingService;
import org.openmrs.module.diseaseregistry.api.model.ExpenseBalance;
import org.openmrs.module.diseaseregistry.api.model.FiscalPeriod;
import org.openmrs.module.diseaseregistry.utils.ExcelReportHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportFormController {
	
	
	@ModelAttribute("periods")
	public List<FiscalPeriod> registerPeriods(){
		return Context.getService(AccountingService.class).getCurrentYearPeriods();
	}
	
	@RequestMapping(value = "module/accounting/report.form", method = RequestMethod.GET)
	public String viewReportForm() {
		
		return "module/accounting/report/reportForm";
	}
	
	@RequestMapping(value = "/module/accounting/downloadReport.form ", method = RequestMethod.GET)
	public void getFile(@RequestParam(value="periodId", required=false) Integer periodId, HttpServletResponse response) throws IOException {
		if (periodId == null) {
			List<ExpenseBalance> balances = Context.getService(AccountingService.class).listActiveExpenseBalance();
			ExcelReportHelper.generateExcel(balances, response.getOutputStream());
		} else {
			FiscalPeriod period = Context.getService(AccountingService.class).getFiscalPeriod(periodId);
			if (period != null) {
				List<ExpenseBalance> balances = Context.getService(AccountingService.class).listActiveExpenseBalance(period);
				ExcelReportHelper.generateExcel(balances, response.getOutputStream());
			}
		}
		
	}
}
