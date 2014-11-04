/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.diseaseregistry.web.controller.dashboard;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.DiseaseRegistryConstants;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */
@Controller
public class DashboardController {

	protected final Log log = LogFactory.getLog(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	@RequestMapping(value = "/module/diseaseregistry/dashboard.list", method = RequestMethod.GET)
	public String list(@RequestParam(value = "q", required = false) String query, ModelMap model) {
		
		Integer opdWard = Integer.parseInt(Context.getAdministrationService().getGlobalProperty(DiseaseRegistryConstants.PROPERTY_OPD_WARD));
		if(opdWard==0) {
			
			model.addAttribute("conceptNotFound", true);
			return "/module/diseaseregistry/dashboard/dashboardList";	
		}
		
		PatientQueueService patientQueueService = Context.getService(PatientQueueService.class);
		List<OpdPatientQueue> patientQueues = patientQueueService.listOpdPatientQueue(query, opdWard, "",0, 0);		
		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("patientQueues", patientQueues);

		return "/module/diseaseregistry/dashboard/dashboardList";
	}
}
