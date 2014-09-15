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
package org.openmrs.module.diseaseregistry.api;

import java.util.Collection;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.diseaseregistry.api.model.DRConcept;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflowPatient;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(DiseaseRegistryServiceService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface DiseaseRegistryService extends OpenmrsService {
	
	public static final boolean NOT_INCLUDE_RETIRED = false;
	public static final boolean INCLUDE_RETIRED = true;

	public abstract Collection<DRProgram> getPrograms(boolean includeRetired);

	public abstract DRProgram saveProgram(DRProgram program);

	public abstract DRProgram getProgram(Integer id);

	public abstract DRWorkflow saveWorkflow(DRWorkflow workflow);

	public abstract DRConcept saveConcept(DRConcept concept);

	public abstract Collection<DRWorkflow> getWorkflows(boolean includeRetired);

	public abstract DRWorkflow getWorkflow(Integer id);

	public abstract Collection<DRConcept> getConceptByWorkflow(DRWorkflow workflow, boolean includeRetired);

	public abstract DRConcept getConcept(String id);

	public abstract Collection<DRWorkflow> getWorkflowsByProgram(DRProgram program);

	public abstract DRWorkflowPatient saveWorkflowPatient(DRWorkflowPatient workflowPatient)
			throws DAOException;

	public abstract Collection<DRWorkflowPatient> getWorkflowPatients(Patient patient, boolean includeRetired);

	public abstract DRWorkflowPatient getWorkflowPatient(Integer id);
     
	/*
	 * Add service methods here
	 * 
	 */
}