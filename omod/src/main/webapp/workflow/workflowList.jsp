<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<h2><spring:message code="diseaseregistry.manage.workflow" /></h2>

<a href="workflow.form"><openmrs:message code="diseaseregistry.add.workflow"/></a>

<br /><br />

${programs}

<%@ include file="/WEB-INF/template/footer.jsp"%>