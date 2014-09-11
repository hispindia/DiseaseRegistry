<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	function enroll() {

		var d = new Date();
		id = d.getTime();
		
		jQuery("#enroll").attr("disabled","disabled");
		clone = jQuery("#template_program").clone();
		clone.attr("id", "program_" + id);
		clone.insertAfter(jQuery("#programs"));
		programDropList = jQuery("#template_programDroplist", clone);		
		programDropList.attr("id", "programDropList_" + id);
		workflowDropList = jQuery("#template_workflowDroplist", clone);		
		workflowDropList.attr("id", "workflowDroplist_" + id);

		jQuery.ajax({
			type : "GET",
			url : "<%= request.getContextPath() %>/module/diseaseregistry/ajax/programDroplist.htm",
			data : ({
				
			}),
			success : function(data) {
				programDropList.append(data);
				jQuery('select', programDropList).bind('change', function() {
					updateWorkflow('programDropList_' + id, 'workflowDroplist_' + id)
				});
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
	}

	function updateWorkflow(programId, workflowId) {
		programId = jQuery('#'+programId).find(":selected").val();
		jQuery.ajax({
			type : "GET",
			url : "<%= request.getContextPath() %>/module/diseaseregistry/ajax/workflowDroplist.htm",
			data : ({
				programId: programId
			}),
			success : function(data) {
				jQuery('#'+workflowId).empty();
				jQuery('#'+workflowId).append(data);
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
	<table >
	<tr id='template_program'>		
		<td></td>
		<td >
			<table>
				<tr>
					<td>Program</td>
					<td id='template_programDroplist'></td>
				</tr>
				<tr>
					<td>Workflow</td>
					<td id='template_workflowDroplist'></td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='button' value='Delete'/>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>