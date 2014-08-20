package org.openmrs.module.diseaseregistry.api.model;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

public class DRProgram extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String description;
	private Concept concept;
	
	public DRProgram() {
		super.setVoided(false);
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DRProgram other = (DRProgram) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DRProgram [id=" + id + ", name=" + name + "]";
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
