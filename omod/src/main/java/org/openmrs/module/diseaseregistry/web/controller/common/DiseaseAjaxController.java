package org.openmrs.module.diseaseregistry.web.controller.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.openmrs.ConceptWord;
import org.openmrs.api.context.Context;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiseaseAjaxController {

	/**
	 * Concept search autocomplete for form
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/module/diseaseregistry/ajax/autocompleteConceptSearch.htm", method = RequestMethod.GET)
	public String autocompleteConceptSearch(
			@RequestParam(value = "q", required = false) String name,
			Model model) {
		List<ConceptWord> cws = Context.getConceptService().findConcepts(name,
				new Locale("en"), false);
		Set<String> conceptNames = new HashSet<String>();
		for (ConceptWord word : cws) {
			String conceptName = word.getConcept().getName().getName();
			conceptNames.add(conceptName);
		}
		List<String> concepts = new ArrayList<String>();
		concepts.addAll(conceptNames);
		Collections.sort(concepts, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});
		model.addAttribute("conceptNames", concepts);
		return "/module/diseaseregistry/ajax/autocompleteConceptSearch";
	}

	@RequestMapping(value = "/module/diseaseregistry/ajax/programDroplist.htm", method = RequestMethod.GET)
	public String getProgramDroplist(Model model) {

		List<DRProgram> programs = new ArrayList<DRProgram>(Context.getService(
				DiseaseRegistryService.class).getPrograms(
				DiseaseRegistryService.NOT_INCLUDE_RETIRED));
		model.addAttribute("programs", programs);
		return "/module/diseaseregistry/ajax/programDroplist";
	}
	
	@RequestMapping(value = "/module/diseaseregistry/ajax/workflowDroplist.htm", method = RequestMethod.GET)
	public String getWorkflowDroplist(Model model, @RequestParam(value = "programId") Integer programId) {

		DiseaseRegistryService drs = Context.getService(DiseaseRegistryService.class);
		DRProgram program = drs.getProgram(programId);
		List<DRWorkflow> workflows = new ArrayList<DRWorkflow>(drs.getWorkflowsByProgram(program));
		model.addAttribute("workflows", workflows);
		return "/module/diseaseregistry/ajax/workflowDroplist";
	}
}
