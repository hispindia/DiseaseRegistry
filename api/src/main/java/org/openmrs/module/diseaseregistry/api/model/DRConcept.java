package org.openmrs.module.diseaseregistry.api.model;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

public class DRConcept extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;	
	
    private String drConceptId;
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
		result = prime * result
				+ ((drConceptId == null) ? 0 : drConceptId.hashCode());
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
		if (drConceptId == null) {
			if (other.drConceptId != null)
				return false;
		} else if (!drConceptId.equals(other.drConceptId))
			return false;
		return true;
	}	

	@Override
	public String toString() {
		return "DRConcept [drConceptId=" + drConceptId + ", concept=" + concept
				+ ", workflow=" + workflow + ", weight=" + weight + "]";
	}

	public Integer getId() {
		return null;
	}

	public void setId(Integer id) {
		
	}

	public String getDrConceptId() {
		return drConceptId;
	}

	public void setDrConceptId(String drConceptId) {
		this.drConceptId = drConceptId;
	}
	
	
	
}
