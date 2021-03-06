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
package org.openmrs.module.diseaseregistry.web.controller.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflowPatient;
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
public class PatientProfileController {

	protected final Log log = LogFactory.getLog(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	@RequestMapping(value = "/module/diseaseregistry/patientProfile.form", method = RequestMethod.GET)
	public String show(ModelMap model,
			@RequestParam(value = "patientId") Integer patientId) {

		Patient patient = Context.getPatientService().getPatient(patientId);
		List<DRWorkflowPatient> workflowPatients = new ArrayList<DRWorkflowPatient>(
				Context.getService(DiseaseRegistryService.class)
						.getWorkflowPatients(patient,
								DiseaseRegistryService.NOT_INCLUDE_RETIRED));

		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("patient", patient);
		model.addAttribute("workflowPatients", workflowPatients);
		return "/module/diseaseregistry/patient/profile";
	}

	@RequestMapping(value = "/module/diseaseregistry/patientProfile.form", method = RequestMethod.POST)
	public String enroll(ModelMap model,
			@RequestParam(value = "patientId") Integer patientId,
			@RequestParam(value = "workflowId") Integer workflowId) {

		Patient patient = Context.getPatientService().getPatient(patientId);
		DiseaseRegistryService drs = Context
				.getService(DiseaseRegistryService.class);
		DRWorkflow workflow = drs.getWorkflow(workflowId);
		DRWorkflowPatient workflowPatient = new DRWorkflowPatient();
		workflowPatient.setPatient(patient);
		workflowPatient.setWorkflow(workflow);
		workflowPatient.setStatus(DRWorkflowPatient.ENROLLED);
		workflowPatient.setDateEnrolled(new Date());
		workflowPatient.setDateCreated(new Date());
		workflowPatient.setCreator(Context.getAuthenticatedUser());
		drs.saveWorkflowPatient(workflowPatient);
		return "redirect:/module/diseaseregistry/patientProfile.form?patientId="
				+ patientId;
	}
}
