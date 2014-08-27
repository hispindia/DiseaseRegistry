<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#concept").autocomplete('${pageContext.request.contextPath}/module/diseaseregistry/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			insertConcept(item);
		});
    });

    function insertConcept(item){		
		jQuery("#concept").val(item);
	}
</script>

<h2><spring:message code="diseaseregistry.addEdit.workflow" /></h2>

<form method="post" id="theForm">
	<table>
	<tr>		
		<th><openmrs:message code="diseaseregistry.program"/><span class="required">*</span></th>
		<td>
			<spring:bind path="workflow.program">
				<select id="program" name="${status.expression}" value="${status.value}">
					<c:forEach var="program" items="${programs}">
    					<option value="${program.id}">${program.name}</option>
					</c:forEach>
				</select>				
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>		
		<th><openmrs:message code="general.name"/><span class="required">*</span></th>
		<td>
			<spring:bind path="workflow.name">
				<input id="name" type="text" name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>		
		<th><openmrs:message code="general.description"/><span class="required"></span></th>
		<td>
			<spring:bind path="workflow.description">
				<input id="description" type="text" name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>		
		<th><openmrs:message code="Program.concept"/><span class="required">*</span></th>
		<td>
			<spring:bind path="workflow.concept">
				<input id="concept" type="text" name="${status.expression}" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>	
	</table>
	<br/>
	<input type="submit" value='<openmrs:message code="Program.save"/>' onClick="jQuery('#theForm').submit()" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>