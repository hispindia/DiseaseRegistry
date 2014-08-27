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
package org.openmrs.module.diseaseregistry.web.controller.program;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;
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
public class ProgramController {

	protected final Log log = LogFactory.getLog(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class,
				new CustomBooleanEditor("true", "false", true));
		binder.registerCustomEditor(org.openmrs.Concept.class,
				new ConceptWordEditor());		
	}

	@RequestMapping(value = "/module/diseaseregistry/program.list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		DiseaseRegistryService drs = Context
				.getService(DiseaseRegistryService.class);

		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("programList", drs.getPrograms(false));
		return "/module/diseaseregistry/program/programList";
	}

	@RequestMapping(value = "/module/diseaseregistry/program.list", method = RequestMethod.POST)
	public String delete(ModelMap model,
			@RequestParam(value = "programId", required = false) String ids) {

		String[] idList = ids.split(",");
		for (String id : idList) {

			Integer programId = Integer.parseInt(id);
			DRProgram program = Context
					.getService(DiseaseRegistryService.class).getProgram(
							programId);
			program.setVoided(true);
			program.setVoidedBy(Context.getAuthenticatedUser());
			program.setDateVoided(new Date());
			program.setVoidReason("Retired on Manage Program page");
			Context.getService(DiseaseRegistryService.class).saveProgram(
					program);
		}

		return "redirect:/module/diseaseregistry/program.list";
	}

	@RequestMapping(value = "/module/diseaseregistry/program.form", method = RequestMethod.GET)
	public String form(@ModelAttribute("program") DRProgram program,
			BindingResult bindingResult, ModelMap model,
			@RequestParam(value = "programId", required = false) Integer id) {

		if (id != null) {

			program = Context.getService(DiseaseRegistryService.class)
					.getProgram(id);
		} else {
			program = new DRProgram();
		}

		model.addAttribute("program", program);
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/diseaseregistry/program/programForm";
	}

	@RequestMapping(value = "/module/diseaseregistry/program.form", method = RequestMethod.POST)
	public String form(@ModelAttribute("program") DRProgram submitProgram,
			BindingResult bindingResult, ModelMap model) {

		new ProgramValidator().validate(submitProgram, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/diseaseregistry/program.form";
		}

		if (submitProgram.getId() != null) {

			DRProgram program = Context
					.getService(DiseaseRegistryService.class).getProgram(
							submitProgram.getId());
			program.setName(submitProgram.getName());
			program.setDescription(submitProgram.getDescription());
			program.setConcept(submitProgram.getConcept());
			program.setDateChanged(new Date());
			program.setChangedBy(Context.getAuthenticatedUser());
			Context.getService(DiseaseRegistryService.class).saveProgram(
					program);

		} else {
			submitProgram.setCreator(Context.getAuthenticatedUser());
			submitProgram.setDateCreated(new Date());
			Context.getService(DiseaseRegistryService.class).saveProgram(
					submitProgram);
		}

		model.addAttribute("user", Context.getAuthenticatedUser());
		return "redirect:/module/diseaseregistry/program.list";
	}
}
