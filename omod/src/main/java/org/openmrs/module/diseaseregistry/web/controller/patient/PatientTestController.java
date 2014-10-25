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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptSet;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.DiseaseRegistryConstants;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRConcept;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflowPatient;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.RadiologyUtil;
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
			@RequestParam(value = "workflowPatientId") Integer workflowPatientId, @RequestParam(value = "action", required=false) String action) {
		
		DiseaseRegistryService drs = Context
				.getService(DiseaseRegistryService.class);		
		DRWorkflowPatient workflowPatient = drs.getWorkflowPatient(workflowPatientId);
		
		// if user clicks Cancel button
		if(StringUtils.isNotBlank(action)) {
			if(action.equalsIgnoreCase("cancel")) {
				workflowPatient.setVoided(true);
				workflowPatient.setVoidedBy(Context.getAuthenticatedUser());
				workflowPatient.setDateVoided(new Date());
				drs.saveWorkflowPatient(workflowPatient);
				return "redirect:/module/diseaseregistry/patientProfile.form?patientId="+ workflowPatient.getPatient().getPatientId();
			} 
		}
		
		// user clicks on Enter result button
		List<DRConcept> tests = new ArrayList<DRConcept>(drs.getConceptByWorkflow(workflowPatient.getWorkflow(), DiseaseRegistryService.NOT_INCLUDE_RETIRED));
		List<Map<String, Object>> testDetails = new ArrayList<Map<String, Object>>();
		Encounter encounter = workflowPatient.getEncounter();		
		for(DRConcept test:tests) {
			Concept concept = test.getConcept();
			Map<String, Object> testDetail = new HashMap<String, Object>();
			testDetail.put("id", test.getDrConceptId());
			testDetail.put("name", test.getConcept().getName().getName());
			if ((concept.getDatatype().getName().equalsIgnoreCase("text"))
					|| (concept.getDatatype().getName()
							.equalsIgnoreCase("numeric"))) {
				testDetail.put("type", "textbox");
				if(encounter!=null) {
					testDetail.put("value", getValue(encounter.getAllObs(), concept));
				}
			} else if (concept.getDatatype().getName()
					.equalsIgnoreCase("coded")) {
				Set<Map<String, String>> options = new HashSet<Map<String, String>>();
				for (ConceptAnswer ca : concept.getAnswers()) {
					Concept c = ca.getAnswerConcept();
					Map<String, String> option = new HashMap<String, String>();
					option.put("conceptId", c.getConceptId().toString());
					option.put("conceptName", c.getName().getName());					
					options.add(option);
				}

				for (ConceptSet cs : concept.getConceptSets()) {
					Concept c = cs.getConcept();
					Map<String, String> option = new HashMap<String, String>();
					option.put("conceptId", c.getConceptId().toString());
					option.put("conceptName", c.getName().getName());
					options.add(option);
				}
				
				if(encounter!=null) {
					testDetail.put("value", getValue(encounter.getAllObs(), concept));
				}
				testDetail.put("options", options);
				testDetail.put("type", "selection");
			}			
			testDetails.add(testDetail);
		}
		
		model.addAttribute("workflowPatient", workflowPatient);
		model.addAttribute("testDetails", testDetails);		
		model.addAttribute("user", Context.getAuthenticatedUser());
		if(StringUtils.isBlank(action)) {
			action = "Enter test";
		}
		model.addAttribute("action", action);
		return "/module/diseaseregistry/patient/test";
	}
	
	private String getValue(Set<Obs> allObs, Concept concept) {
		
		for(Obs obs:allObs) {
			if(obs.getConcept().equals(concept)) {
				if(concept.getDatatype().getName().equalsIgnoreCase("text")) {
					return obs.getValueText();
				} else if(concept.getDatatype().getName().equalsIgnoreCase("numeric")) {
					return obs.getValueNumeric().toString();
				} else if(concept.getDatatype().getName().equalsIgnoreCase("coded")) {
					return obs.getValueCoded().getConceptId().toString();
				}
			}
		}
		return null;
	}
	
	private List<String> getSortedOptionNames(Set<String> options){
		List<String> names = new ArrayList<String>();
		names.addAll(options);
		Collections.sort(names);		
		return names;
	}
	
	@RequestMapping(value = "/module/diseaseregistry/patientTest.form", method = RequestMethod.POST)
	public String enterTest(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "workflowPatientId") Integer workflowPatientId) {
		
		DiseaseRegistryService drs = Context
				.getService(DiseaseRegistryService.class);		
		DRWorkflowPatient workflowPatient = drs.getWorkflowPatient(workflowPatientId);
		
		Encounter encounter = workflowPatient.getEncounter();
		if(encounter==null) {
			encounter = new Encounter();
			String encounterTypeStr = GlobalPropertyUtil.getString(
					DiseaseRegistryConstants.PROPERTY_DISEASE_REGISTRY_ENCOUNTER_TYPE,
					"DISEASEREGISTRYENCOUNTER");
			EncounterType encounterType = Context.getEncounterService()
					.getEncounterType(encounterTypeStr);		
			encounter.setCreator(Context.getAuthenticatedUser());
			encounter.setDateCreated(new Date());
			Location loc = Context.getLocationService().getLocation(1);
			encounter.setLocation(loc);
			encounter.setPatient(workflowPatient.getPatient());
			encounter.setPatientId(workflowPatient.getPatient().getId());
			encounter.setEncounterType(encounterType);
			encounter.setVoided(false);
			encounter.setProvider(Context.getAuthenticatedUser().getPerson());
			encounter.setUuid(UUID.randomUUID().toString());
			encounter.setEncounterDatetime(new Date());
			encounter = Context.getEncounterService().saveEncounter(encounter);
			workflowPatient.setEncounter(encounter);
		}
		
		Map<String, String> parameters = buildParameterList(request);
		Order order = createOrder(encounter, workflowPatient.getWorkflow().getConcept(), workflowPatient.getPatient());
		for (String key : buildParameterList(request).keySet()) {
			
			DRConcept test = drs.getConcept(key);						
			Obs obs = insertValue(encounter, test.getConcept(), parameters.get(key), order);
			if (obs.getId() == null) {
				encounter.addObs(obs);
			}
		}	
		Context.getEncounterService().saveEncounter(encounter);
		workflowPatient.setStatus(DRWorkflowPatient.TESTED);
		workflowPatient.setDateTested(new Date());
		workflowPatient.setDateChanged(new Date());
		workflowPatient.setChangedBy(Context.getAuthenticatedUser());		
		
		return "redirect:/module/diseaseregistry/patientProfile.form?patientId="
				+ workflowPatient.getPatient().getPatientId();
	}
	
	private Map<String, String> buildParameterList(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			String parameterName = (String) e.nextElement();
			if(StringUtils.isNumeric(parameterName)) {
				parameters.put(parameterName, request.getParameter(parameterName));
			}
		}
		return parameters;
	}
	
	private Obs insertValue(Encounter encounter, Concept concept, String value, Order order) {

		Obs obs = getObs(encounter, concept);
		obs.setEncounter(encounter);
		obs.setConcept(concept);
		obs.setOrder(order);
		if (concept.getDatatype().getName().equalsIgnoreCase("Text")) {
			value = value.replace("\n", "\\n");
			obs.setValueText(value);
		} else if (concept.getDatatype().getName().equalsIgnoreCase("Numeric")) {
			obs.setValueNumeric(new Double(value));
		} else if (concept.getDatatype().getName().equalsIgnoreCase("Coded")) {
			Concept answerConcept = RadiologyUtil.searchConcept(value);
			obs.setValueCoded(answerConcept);
		}		
		return obs;
	}

	private Obs getObs(Encounter encounter, Concept concept) {
		for (Obs obs : encounter.getAllObs()) {
			if (obs.getConcept().equals(concept))
				return obs;
		}
		Obs obs = new Obs();
		return obs;
	}
	
	private Order createOrder(Encounter encounter, Concept concept, Patient patient) {
		Order order = new Order();
		order.setConcept(concept);
		order.setCreator(Context.getAuthenticatedUser());
		order.setDateCreated(new Date());
		order.setOrderer(Context.getAuthenticatedUser());
		order.setPatient(patient);
		order.setStartDate(new Date());
		order.setAccessionNumber("0");
		
		Integer orderTypeId = GlobalPropertyUtil.getInteger(DiseaseRegistryConstants.GLOBAL_PROPRETY_DISEASE_REGISTRY_ORDER_TYPE, 5);
		OrderType orderType = Context.getOrderService().getOrderType(orderTypeId);
		order.setOrderType(orderType);
		order.setEncounter(encounter);
		encounter.addOrder(order);
		return order;
	}
}
