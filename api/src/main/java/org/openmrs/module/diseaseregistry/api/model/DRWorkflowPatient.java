package org.openmrs.module.diseaseregistry.api.model;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Encounter;
import org.openmrs.Patient;

public class DRWorkflowPatient extends BaseOpenmrsData implements Serializable {
	
	public static final String ENROLLED = "enrolled";
	public static final String TESTED = "tested";
	
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private DRWorkflow workflow;
	private Patient patient;
	private Date dateEnrolled;
	private Date dateTested;
	private String status;
	private Encounter encounter;
	
	public DRWorkflowPatient() {
		super.setVoided(false);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DRWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(DRWorkflow workflow) {
		this.workflow = workflow;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

	public Date getDateEnrolled() {
		return dateEnrolled;
	}

	public void setDateEnrolled(Date dateEnrolled) {
		this.dateEnrolled = dateEnrolled;
	}

	public Date getDateTested() {
		return dateTested;
	}

	public void setDateTested(Date dateTested) {
		this.dateTested = dateTested;
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DRWorkflowPatient other = (DRWorkflowPatient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DRWorkflowPatient [id=" + id + ", workflow=" + workflow
				+ ", patient=" + patient + ", status=" + status + "]";
	}
}
