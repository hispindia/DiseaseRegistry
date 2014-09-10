<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	function enroll() {
		
		jQuery("#enroll").attr("disabled","disabled");
		clone = jQuery("#template_program").clone();
		clone.attr("id", "program");
		programDropList = jQuery("#template_programDroplist", clone);		
		programDropList.attr("id", "programDropList");
		clone.insertAfter(jQuery("#programs"));

		jQuery.ajax({
			type : "GET",
			url : "<%= request.getContextPath() %>/module/diseaseregistry/ajax/programDroplist.htm",
			data : ({
				
			}),
			success : function(data) {
				programDropList.append(data);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
		
		

	}
</script>

<h2><spring:message code="diseaseregistry.patient.profile" /></h2>
<div id='test'>
</div>

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
		<td><b><openmrs:message code="Person.address"/></b></td>		
		<td>${patient.personAddress.address1}</td>
	</tr>	
	<tr id='programs'>
		<td></td>
		<td><input id='enroll' type='button' value='Enroll' onclick='enroll()'/></td>
	</tr>
</table>

<div style='display:none'>
	<table>
	<tr id='template_program'>
		<td></td>
		<td id='template_programDroplist'>
			Program
		</td>
	</tr>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>