package org.openmrs.module.diseaseregistry.api.model;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

public class DRConcept extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;	
	
    private Integer id;
	private Concept concept;
	private DRWorkflow workflow;
	private Integer weight;
	
	public DRConcept() {
		super.setVoided(false);
		this.weight = 0;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public DRWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(DRWorkflow workflow) {
		this.workflow = workflow;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result
				+ ((workflow == null) ? 0 : workflow.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DRConcept other = (DRConcept) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		if (workflow == null) {
			if (other.workflow != null)
				return false;
		} else if (!workflow.equals(other.workflow))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DRConcept [concept=" + concept + ", workflow=" + workflow + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
