<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#concept").autocomplete('${pageContext.request.contextPath}/module/diseaseregistry/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			insertConcept("#concept", item);
		});		

		jQuery("#test").autocomplete('${pageContext.request.contextPath}/module/diseaseregistry/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			appendTest(item);
		});		
    });

    function insertConcept(id, item){		
		jQuery(id).val(item);
	}

	function appendTest(item) {

		var d = new Date();
		id = d.getTime();
		template = "<div id='_id_' class='test'><input name='_id_' value='_item_' readonly/><a href='javascript:moveUp(_id_)'>Up</a>&nbsp;<a href='javascript:moveDown(_id_)'>Down</a>&nbsp;<a href='javascript:remove(_id_)'>Delete</a><input name='tests' type='hidden' value='_id_'/></div>";
		template = template.replace(/_id_/g, id);
		template = template.replace(/_item_/g, item);
		jQuery("#tests").append(template);
		jQuery("#test").val("");
	}

	function moveUp(row) {

		me = jQuery("#" + row);
		prev = jQuery("#" + row).prev();
		if(prev.attr("class")=="test") {
			me.after(prev);	
		}
	}

	function moveDown(row) {
		me = jQuery("#" + row);
		next = jQuery("#" + row).next();
		if(next.attr("class")=="test") {
			me.before(next);
		}		
	}

	function remove(row) {		
		jQuery("#" + row).remove();
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
	<tr>		
		<th><openmrs:message code="diseaseregistry.tests"/></th>
		<td id="tests">			
			<input id="test" type="text" size="35" /><br/>
			<c:if test="${fn:length(tests) != 0}">
				<c:forEach var="test" items="${tests}">
					<div id='${test.drConceptId}' class='test'>
						<openmrs:concept conceptId="${test.concept.conceptId}" var="v" nameVar="n" numericVar="num">
							<input name='${test.drConceptId}' value='${n.name}' readonly/>
						</openmrs:concept>						
						<a href='javascript:moveUp(${test.drConceptId})'>Up</a>&nbsp;
						<a href='javascript:moveDown(${test.drConceptId})'>Down</a>&nbsp;
						<a href='javascript:remove(${test.drConceptId})'>Delete</a>
						<input name='tests' type='hidden' value='${test.drConceptId}'/>
					</div>
				</c:forEach>				
			</c:if>
		</td>
	</tr>	 
	</table>	
	<input type="submit" value='<openmrs:message code="Program.save"/>' onClick="jQuery('#theForm').submit()" /> <br/><br/>

</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>