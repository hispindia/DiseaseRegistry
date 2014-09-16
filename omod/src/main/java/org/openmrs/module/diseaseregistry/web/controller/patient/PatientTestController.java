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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptSet;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRConcept;
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
public class PatientTestController {

	protected final Log log = LogFactory.getLog(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	@RequestMapping(value = "/module/diseaseregistry/patientTest.form", method = RequestMethod.GET)
	public String show(ModelMap model,
			@RequestParam(value = "workflowPatientId") Integer workflowPatientId) {

		DiseaseRegistryService drs = Context
				.getService(DiseaseRegistryService.class);		
		DRWorkflowPatient workflowPatient = drs.getWorkflowPatient(workflowPatientId);
		List<DRConcept> tests = new ArrayList<DRConcept>(drs.getConceptByWorkflow(workflowPatient.getWorkflow(), DiseaseRegistryService.NOT_INCLUDE_RETIRED));
		List<Map<String, Object>> testDetails = new ArrayList<Map<String, Object>>();
		for(DRConcept test:tests) {
			Concept concept = test.getConcept();
			Map<String, Object> testDetail = new HashMap<String, Object>();
			testDetail.put("id", test.getDrConceptId());
			testDetail.put("name", test.getConcept().getName().getName());
			if ((concept.getDatatype().getName().equalsIgnoreCase("text"))
					|| (concept.getDatatype().getName()
							.equalsIgnoreCase("numeric"))) {
				testDetail.put("type", "textbox");
			} else if (concept.getDatatype().getName()
					.equalsIgnoreCase("coded")) {
				Set<String> options = new HashSet<String>();
				for (ConceptAnswer ca : concept.getAnswers()) {
					Concept c = ca.getAnswerConcept();
					options.add(c.getName().getName());
				}

				for (ConceptSet cs : concept.getConceptSets()) {
					Concept c = cs.getConcept();
					options.add(c.getName().getName());
				}
				testDetail.put("options", options);
				testDetail.put("type", "selection");
			}			
			testDetails.add(testDetail);
		}
		
		model.addAttribute("workflowPatient", workflowPatient);
		model.addAttribute("testDetails", testDetails);
		model.addAttribute("tests", tests);
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/diseaseregistry/patient/test";
	}
	
	private List<String> getSortedOptionNames(Set<String> options){
		List<String> names = new ArrayList<String>();
		names.addAll(options);
		Collections.sort(names);		
		return names;
	}
	
	@RequestMapping(value = "/module/diseaseregistry/patientTest.form", method = RequestMethod.POST)
	public String enterTest(ModelMap model,
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
