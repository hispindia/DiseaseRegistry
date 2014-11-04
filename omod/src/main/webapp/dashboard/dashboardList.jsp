<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	function clearSearch() {				
		jQuery("#query").val("");
		jQuery("#searchForm").submit();
	}
</script>

<c:if test="${not empty conceptNotFound}">
	Please set property <b>diseaseregistry.opdWard</b> to concept <b>Disease Registry Ward</b>
</c:if>
<c:if test="${empty conceptNotFound}">
	<form id="searchForm">
		<openmrs:message code="diseaseregistry.dashboard.search"/>
		<input id="query" name="q"/>
		<input type="submit" value='<openmrs:message code="general.search"/>'/>
		<input type="button" value='<openmrs:message code="general.clear"/>' onclick="clearSearch();"/>		
	</form><br/>
	<b class="boxHeader"><openmrs:message code="Patient.header"/></b>
	<div class="box">
		<c:if test="${fn:length(patientQueues) == 0}">
			<tr>
				<td colspan="6"><openmrs:message code="general.none"/></td>
			</tr>
		</c:if>
		<c:if test="${fn:length(patientQueues) != 0}">
			<table cellspacing="0" cellpadding="2">
				<tr>					
					<th><openmrs:message code="diseaseregistry.patient.identifier"/></th>
					<th><openmrs:message code="diseaseregistry.patient.name"/></th>
					<th><openmrs:message code="diseaseregistry.program"/></th>
				</tr>
				<c:forEach var="patient" items="${patientQueues}">
					<tr>
						<td>
							<a href="patientProfile.form?patientId=${patient.patient.patientId}">${patient.patientIdentifier}</a>
						</td>
						<td>${patient.patientName}</td>
						<td></td>
					</tr>
				</c:forEach>
			</table>			
		</c:if>
	</div>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp"%>