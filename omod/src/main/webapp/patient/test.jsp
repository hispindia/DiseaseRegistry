<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
</script>

<h2><spring:message code="diseaseregistry.patient.profile" /></h2>

<c:set var="patient" value="${workflowPatient.patient}"/>
<table>
	<tr>
		<td><b><openmrs:message code="diseaseregistry.patient.identifier"/></b></td>
		<td>${patient.patientIdentifier.identifier}</td>
	</tr>
	<tr>
		<td><b><openmrs:message code="diseaseregistry.patient.name"/></b></td>
		<td>${patient.personName.fullName}</td>
	</tr>
	<tr>
		<td><b><openmrs:message code="Patient.gender"/></b></td>
		<td>
			<c:choose>
				<c:when test="${patient.gender eq 'M'}">
					<openmrs:message code="Patient.gender.male"/>
				</c:when>
				<c:otherwise>
					<openmrs:message code="Patient.gender.female"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td><b><openmrs:message code="Person.birthdate"/></b></td>		
		<td><openmrs:formatDate date="${patient.birthdate}" type="textbox"/></td>
	</tr>
	<tr>
		<td><b><openmrs:message code="diseaseregistry.program"/></b></td>
		<td>${workflowPatient.workflow.program.name}</td>
	</tr>
	<tr>
		<td><b><openmrs:message code="diseaseregistry.workflow"/></b></td>
		<td>${workflowPatient.workflow.name}</td>
	</tr>
	<tr>
		<td><b><openmrs:message code="diseaseregistry.enrolled.date"/></b></td>
		<td><openmrs:formatDate date="${workflowPatient.dateEnrolled}" type="textbox"/></td>
	</tr>
</table>
<c:if test="${fn:length(testDetails) == 0}">		
	No test found for this workflow
</c:if>
<c:if test="${fn:length(testDetails) != 0}">
	<form id='theForm' method='POST'>
		<table>
			<c:forEach var='testDetail' items='${testDetails}'>
				<tr>
					<td>${testDetail['name']}</td>
					<td>
						<c:choose>
							<c:when test="${testDetail['type'] eq 'textbox'}">
								<input type="text" name="${testDetail['id']}" value="${testDetail['value']}"/>
							</c:when>

							<c:when test="${testDetail['type'] eq 'selection'}">							    
								<select name="${testDetail['id']}">
									<option value=''>Please select</option>
									<c:forEach var="option" items="${testDetail['options'] }">
										<option value="${option['conceptId']}" <c:if test="${option['conceptId'] eq testDetail['value']}">selected</c:if>>
											${option['conceptName']}
										</option>
									</c:forEach>
								</select>
							</c:when>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td><input type="submit" value='<openmrs:message code="Program.save"/>' onClick="jQuery('#theForm').submit()" /> <br/><br/></td>
			</tr>
		</table>
	</form>
</c:if>
<%@ include file="/WEB-INF/template/footer.jsp"%>