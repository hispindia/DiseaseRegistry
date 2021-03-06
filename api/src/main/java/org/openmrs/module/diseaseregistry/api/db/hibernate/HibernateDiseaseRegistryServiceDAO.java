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
package org.openmrs.module.diseaseregistry.api.db.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.diseaseregistry.api.db.DiseaseRegistryServiceDAO;
import org.openmrs.module.diseaseregistry.api.model.DRConcept;
import org.openmrs.module.diseaseregistry.api.model.DRProgram;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflow;
import org.openmrs.module.diseaseregistry.api.model.DRWorkflowPatient;

/**
 * It is a default implementation of  {@link DiseaseRegistryServiceDAO}.
 */
public class HibernateDiseaseRegistryServiceDAO implements DiseaseRegistryServiceDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }    
	
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }
        
    @Override
	public DRProgram saveProgram(DRProgram program) throws DAOException {
		return (DRProgram) sessionFactory.getCurrentSession().merge(program);
	}
    
    @Override
	@SuppressWarnings("unchecked")
	public Collection<DRProgram> getPrograms(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DRProgram.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("voided", false));
		return criteria.list();		
	}
    
    
    @Override
	public DRProgram getProgram(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				DRProgram.class);
		criteria.add(Restrictions.eq("id", id));
		return (DRProgram) criteria.uniqueResult();
	}
    
	@Override
	public DRWorkflow saveWorkflow(DRWorkflow workflow) throws DAOException {
		return (DRWorkflow) sessionFactory.getCurrentSession().merge(workflow);
	}
	
	@Override
	public Collection<DRWorkflow> getWorkflows(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DRWorkflow.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("voided", false));
		return criteria.list();		
	}
	
	
	@Override
	public Collection<DRWorkflow> getWorkflowsByProgram(DRProgram program) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DRWorkflow.class);
		criteria.add(Restrictions.eq("voided", false));
		criteria.add(Restrictions.eq("program", program));
		return criteria.list();		
	}
	
	@Override
	public DRWorkflow getWorkflow(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				DRWorkflow.class);
		criteria.add(Restrictions.eq("id", id));
		return (DRWorkflow) criteria.uniqueResult();
	}
	
	@Override
	public DRConcept saveConcept(DRConcept concept) throws DAOException {		
		return (DRConcept) sessionFactory.getCurrentSession().merge(concept);
	}	
	
	@Override
	public Collection<DRConcept> getConceptByWorkflow(DRWorkflow workflow, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				DRConcept.class);
		criteria.add(Restrictions.eq("workflow", workflow));
		if (!includeRetired)
			criteria.add(Restrictions.eq("voided", false));
		return criteria.list();
	}	
	
	@Override
	public DRConcept getConcept(String id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				DRConcept.class);
		criteria.add(Restrictions.eq("id", id));
		return (DRConcept) criteria.uniqueResult();
	}	
	
	@Override
	public DRWorkflowPatient saveWorkflowPatient(DRWorkflowPatient workflowPatient) throws DAOException {		
		return (DRWorkflowPatient) sessionFactory.getCurrentSession().merge(workflowPatient);
	}	
	
	@Override
	public Collection<DRWorkflowPatient> getWorkflowPatients(Patient patient, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				DRWorkflowPatient.class);
		criteria.add(Restrictions.eq("patient", patient));
		if (!includeRetired)
			criteria.add(Restrictions.eq("voided", false));
		return criteria.list();
	}	
	
	@Override
	public DRWorkflowPatient getWorkflowPatient(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				DRWorkflowPatient.class);
		criteria.add(Restrictions.eq("id", id));
		return (DRWorkflowPatient) criteria.uniqueResult();
	}
}