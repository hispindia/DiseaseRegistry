<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	function enroll() {

		jQuery("#enroll").attr("disabled","disabled");

		var d = new Date();
		enrollId = d.getTime();				
		initializeClone(enrollId);

		jQuery.ajax({
			type : "GET",
			url : "<%= request.getContextPath() %>/module/diseaseregistry/ajax/programDroplist.htm",
			data : ({
				
			}),
			success : function(data) {
				programDropList.append(data);
				jQuery('select', programDropList).bind('change', function() {
					programChanged(enrollId);
				});
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
	}

	function programChanged(enrollId) {

		idMap = createIdMap(enrollId);		

		programId = jQuery('#' + idMap['programDropListId']).find(":selected").val();
		jQuery.ajax({
			type : "GET",
			url : "<%= request.getContextPath() %>/module/diseaseregistry/ajax/workflowDroplist.htm",
			data : ({
				programId: programId
			}),
			success : function(data) {
				workflowDroplist = jQuery('#' + idMap['workflowDropListId']);
				workflowDroplist.empty();
				workflowDroplist.append(data);
				
				jQuery('select', workflowDroplist).bind('change', function() {
					workflowChanged(enrollId);
				});
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
	}

	function workflowChanged(enrollId) {

		idMap = createIdMap(enrollId);				
		selectedProgramId = jQuery('#'+idMap['programDropListId']).find(":selected").val();
		selectedWorkflowId = jQuery('#'+idMap['workflowDropListId']).find(":selected").val();

		if(selectedProgramId>0 && selectedWorkflowId>0) {
			jQuery('#'+idMap['buttons']).append('<input id="' + idMap['enrollButton'] + '" type="submit" value="Enroll"/>');
		}
	}

	function createIdMap(enrollId) {
		var map = {}
		map['programDropListId'] = 'programDropList_' + enrollId;
		map['workflowDropListId'] = 'workflowDroplist_' + enrollId;
		map['enrollForm'] = 'enrollForm_' + enrollId;
		map['buttons'] = 'buttons_' + enrollId;
		map['deleteButton'] = 'deleteButton_' + enrollId;
		map['enrollButton'] = 'enrollButton_' + enrollId;
		return map;
	}

	function initializeClone(enrollId) {

		// update ids
		idMap = createIdMap(enrollId);
		clone = jQuery("#template_enrollForm").clone();
		clone.attr("id", "enrollForm_" + enrollId);
		clone.insertAfter(jQuery("#programs"));
		programDropList = jQuery("#template_programDroplist", clone);		
		programDropList.attr("id", idMap['programDropListId']);
		workflowDropList = jQuery("#template_workflowDroplist", clone);		
		workflowDropList.attr("id", idMap['workflowDropListId']);
		deleteButton = jQuery("#template_deleteButton", clone);		
		deleteButton.attr("id", idMap['deleteButton']);
		buttons = jQuery("#template_buttons", clone);		
		buttons.attr("id", idMap['buttons']);

		// update videos		
		jQuery(deleteButton).bind('click', function() {
			clone.remove();
			jQuery("#enroll").attr("disabled","");
		});
	}

	ACTIONS = {
		enrollDelete: function() {
			
		}
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
	<c:if test="${fn:length(workflowPatients) != 0}">
		<c:forEach var="workflowPatient" items="${workflowPatients}">
			<tr>
				<td></td>
				<td >
					<form method='GET' action='patientTest.form'>
						<input type='hidden' name='workflowPatientId' value='${workflowPatient.id}'/>
						<table style='border: 1px solid green; margin: 2px; padding: 2px;'>
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
							<tr>
								<td colspan='2'>
									<input type='submit' value='Enter test'/>
									<input type='submit' value='Cancel'/>
								</td>					
							</tr>
						</table>
					</form>
				</td>	
			</tr>
		</c:forEach>
	</c:if>
</table>

<div style='display:none'>
	<table >
	<tr id='template_enrollForm'>			
		<td></td>
		<td >
			<form method='POST'>				
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
						<td colspan='2' id='template_buttons'>
							<input id='template_deleteButton' type='button' value='Delete'/>
						</td>					
					</tr>
				</table>
			</form>
		</td>		
	</tr>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>