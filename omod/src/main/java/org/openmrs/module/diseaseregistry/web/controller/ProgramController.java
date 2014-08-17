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
package org.openmrs.module.diseaseregistry.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Program;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
	
	@RequestMapping(value = "/module/diseaseregistry/program.list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		
		ProgramWorkflowService ps = Context.getProgramWorkflowService();
		List<Program> programList = ps.getAllPrograms();
		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("programList", programList);		
		return "/module/diseaseregistry/program/programList";
	}
	
	@RequestMapping(value = "/module/diseaseregistry/program.form", method = RequestMethod.GET)	
	public String form(@ModelAttribute("program") Program program, BindingResult bindingResult, ModelMap model, @RequestParam(value="id", required=false) Integer id) {		
			
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/diseaseregistry/program/programForm";
	}
	
	@RequestMapping(value = "/module/diseaseregistry/program.form", method = RequestMethod.POST)	
	public String form(@ModelAttribute("program") Program program, BindingResult bindingResult, ModelMap model) {	
		
		new ProgramValidator().validate(program, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/diseaseregistry/program.form";
		}
			
		ProgramWorkflowService ps = Context.getProgramWorkflowService();
		ps.saveProgram(program);
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/diseaseregistry/program/programForm";
	}
}
