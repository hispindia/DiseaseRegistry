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
package org.openmrs.module.diseaseregistry.web.controller.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRConcept;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.openmrs.module.diseaseregistry.web.controller.common.ConceptWordEditor;
import org.openmrs.module.diseaseregistry.web.controller.common.DRProgramEditor;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */
@Controller
public class WorkflowController {

	protected final Log log = LogFactory.getLog(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class,
				new CustomBooleanEditor("true", "false", true));
		binder.registerCustomEditor(org.openmrs.Concept.class,
				new ConceptWordEditor());
		binder.registerCustomEditor(
				org.openmrs.module.diseaseregistry.api.model.DRProgram.class,
				new DRProgramEditor());
	}

	@ModelAttribute("programs")
	public List<DRProgram> getPrograms() {

		return new ArrayList<DRProgram>(Context.getService(
				DiseaseRegistryService.class).getPrograms(
				DiseaseRegistryService.NOT_INCLUDE_RETIRED));
	}

	@RequestMapping(value = "/module/diseaseregistry/workflow.list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		List<DRWorkflow> workflows = new ArrayList<DRWorkflow>(Context
				.getService(DiseaseRegistryService.class).getWorkflows(
						DiseaseRegistryService.NOT_INCLUDE_RETIRED));

		model.addAttribute("workflows", workflows);
		model.addAttribute("user", Context.getAuthenticatedUser());

		return "/module/diseaseregistry/workflow/workflowList";
	}

	@RequestMapping(value = "/module/diseaseregistry/workflow.list", method = RequestMethod.POST)
	public String delete(ModelMap model,
			@RequestParam(value = "id", required = false) String ids) {

		String[] idList = ids.split(",");
		for (String id : idList) {

			Integer workflowId = Integer.parseInt(id);
			DRWorkflow workflow = Context.getService(
					DiseaseRegistryService.class).getWorkflow(workflowId);
			workflow.setVoided(true);
			workflow.setVoidedBy(Context.getAuthenticatedUser());
			workflow.setDateVoided(new Date());
			workflow.setVoidReason("Retired on Manage Program page");
			Context.getService(DiseaseRegistryService.class).saveWorkflow(
					workflow);
		}

		model.addAttribute("user", Context.getAuthenticatedUser());

		return "/module/diseaseregistry/workflow/workflowList";
	}

	@RequestMapping(value = "/module/diseaseregistry/workflow.form", method = RequestMethod.GET)
	public String form(@ModelAttribute("workflow") DRWorkflow workflow,
			BindingResult bindingResult, ModelMap model,
			@RequestParam(value = "id", required = false) String id) {

		if (StringUtils.isNotBlank(id)) {
			workflow = Context.getService(DiseaseRegistryService.class)
					.getWorkflow(Integer.parseInt(id));
			model.addAttribute("workflow", workflow);
			List<DRConcept> tests = new ArrayList<DRConcept>(Context
					.getService(DiseaseRegistryService.class)
					.getConceptByWorkflow(workflow,
							DiseaseRegistryService.NOT_INCLUDE_RETIRED));
			Collections.sort(tests, new Comparator<DRConcept>() {
				public int compare(DRConcept t1, DRConcept t2) {
					return t1.getWeight() - t2.getWeight();
				}
			});
			model.addAttribute("tests", tests);
		}
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/diseaseregistry/workflow/workflowForm";
	}

	@RequestMapping(value = "/module/diseaseregistry/workflow.form", method = RequestMethod.POST)
	public String form(HttpServletRequest request,
			@ModelAttribute("workflow") DRWorkflow submittedWorkflow,
			BindingResult bindingResult, ModelMap model) {

		new WorkflowValidator().validate(submittedWorkflow, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/diseaseregistry/workflow.form";
		}

		if (submittedWorkflow.getId() != null) {

			DRWorkflow workflow = Context.getService(
					DiseaseRegistryService.class).getWorkflow(
					submittedWorkflow.getId());
			workflow.setProgram(submittedWorkflow.getProgram());
			workflow.setName(submittedWorkflow.getName());
			workflow.setDescription(submittedWorkflow.getDescription());
			workflow.setConcept(submittedWorkflow.getConcept());
			workflow.setDateChanged(new Date());
			workflow.setChangedBy(Context.getAuthenticatedUser());
			workflow = Context.getService(DiseaseRegistryService.class)
					.saveWorkflow(workflow);
			updateTests(request, workflow);

		} else {

			submittedWorkflow.setCreator(Context.getAuthenticatedUser());
			submittedWorkflow.setDateCreated(new Date());
			submittedWorkflow = Context
					.getService(DiseaseRegistryService.class).saveWorkflow(
							submittedWorkflow);
			updateTests(request, submittedWorkflow);
		}

		model.addAttribute("user", Context.getAuthenticatedUser());
		return "redirect:/module/diseaseregistry/workflow.list";
	}

	private void updateTests(HttpServletRequest request, DRWorkflow workflow) {

		DiseaseRegistryService drs = Context
				.getService(DiseaseRegistryService.class);

		List<DRConcept> tests = new ArrayList<DRConcept>(
				drs.getConceptByWorkflow(workflow,
						DiseaseRegistryService.NOT_INCLUDE_RETIRED));
		String[] ids = request.getParameterValues("tests");

		// add new and update existing tests
		for (int i = 0; i < ids.length; i++) {
			String key = ids[i];
			if (StringUtils.isNumeric(key)) {

				String value = request.getParameter(key);
				DRConcept test = drs.getConcept(key);
				if (test == null) {

					// add new test
					test = new DRConcept();
					test.setConcept(Context.getConceptService().getConcept(
							value));
					test.setDrConceptId(key);
					test.setWeight(i);
					test.setWorkflow(workflow);
					test.setCreator(Context.getAuthenticatedUser());
					test.setDateCreated(new Date());
					drs.saveConcept(test);
				} else {

					// update concept
					test.setConcept(Context.getConceptService().getConcept(
							value));
					test.setDrConceptId(key);
					test.setWeight(i);
					test.setWorkflow(workflow);
					test.setDateChanged(new Date());
					drs.saveConcept(test);
					tests.remove(test);
				}
			}
		}

		// retired tests
		for (DRConcept test : tests) {

			test.setVoided(true);
			test.setVoidedBy(Context.getAuthenticatedUser());
			test.setDateVoided(new Date());
			drs.saveConcept(test);
		}

	}
}
