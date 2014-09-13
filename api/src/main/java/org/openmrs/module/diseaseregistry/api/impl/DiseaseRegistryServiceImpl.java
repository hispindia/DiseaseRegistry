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
package org.openmrs.module.diseaseregistry.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.diseaseregistry.api.DiseaseRegistryService;
import org.openmrs.module.diseaseregistry.api.db.DiseaseRegistryServiceDAO;
import org.openmrs.module.diseaseregistry.api.model.DRConcept;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflowPatient;

/**
 * It is a default implementation of {@link DiseaseRegistryService}.
 */
public class DiseaseRegistryServiceImpl extends BaseOpenmrsService implements DiseaseRegistryService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private DiseaseRegistryServiceDAO dao;	
	
    public void setDao(DiseaseRegistryServiceDAO dao) {
	    this.dao = dao;
    }    
    
    public DiseaseRegistryServiceDAO getDao() {
	    return dao;
    }
    
    @Override
	public DRProgram saveProgram(DRProgram program) {
    	return dao.saveProgram(program);
    }
    
    @Override
	public Collection<DRProgram> getPrograms(boolean includeRetired) {
    	return dao.getPrograms(includeRetired);
    }
    
    @Override
	public DRProgram getProgram(Integer id) {
    	return dao.getProgram(id);
    }    
    
	@Override
	public DRWorkflow saveWorkflow(DRWorkflow workflow) {
    	return dao.saveWorkflow(workflow);
    }
	
	@Override
	public Collection<DRWorkflow> getWorkflows(boolean includeRetired) {
    	return dao.getWorkflows(includeRetired);
    }
	
	@Override
	public Collection<DRWorkflow> getWorkflowsByProgram(DRProgram program) {
		return dao.getWorkflowsByProgram(program);
	}
	
	@Override
	public DRWorkflow getWorkflow(Integer id) {
    	return dao.getWorkflow(id);
    }
	
	@Override
	public DRConcept saveConcept(DRConcept concept) {
    	return dao.saveConcept(concept);
    }	
	
	@Override
	public Collection<DRConcept> getConceptByWorkflow(DRWorkflow workflow, boolean includeRetired) {
    	return dao.getConceptByWorkflow(workflow, includeRetired);
    }
	
	@Override
	public DRConcept getConcept(String id) {
		return dao.getConcept(id);
	}	
	
	@Override
	public DRWorkflowPatient saveWorkflowPatient(DRWorkflowPatient workflowPatient) throws DAOException {		
		return dao.saveWorkflowPatient(workflowPatient);
	}
}