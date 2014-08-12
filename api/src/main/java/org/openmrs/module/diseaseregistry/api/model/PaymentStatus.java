package org.openmrs.module.diseaseregistry.api.model;


public enum PaymentStatus {
	NEW("NEW"),
	COMMITTED("COMMITTED"),
	PAID("PAID"),
	DELETED("DELETED");
	
	private String name;
	
	PaymentStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
