package org.openmrs.module.diseaseregistry.extension.html;

import org.openmrs.module.web.extension.LinkExt;

public class DiseaseRegistryHeader extends LinkExt {

	@Override
	public String getLabel() {
		return "diseaseregistry.title";
	}

	@Override
	public String getRequiredPrivilege() {
		return "Manage Disease Registry";
	}

	@Override
	public String getUrl() {
		return "module/diseaseregistry/program.list";
	}
	

}
