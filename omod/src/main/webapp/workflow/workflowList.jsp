<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<h2><spring:message code="diseaseregistry.manage.workflow" /></h2>

<a href="workflow.form"><openmrs:message code="diseaseregistry.add.workflow"/></a>

<br /><br />

<b class="boxHeader"><openmrs:message code="diseaseregistry.workflow.list.title"/></b>
<div class="box">
	<c:if test="${fn:length(workflows) == 0}">
		<tr>
			<td colspan="6"><openmrs:message code="general.none"/></td>
		</tr>
	</c:if>
	<c:if test="${fn:length(workflows) != 0}">
		<form method="post" id="theForm">
		<table cellspacing="0" cellpadding="2">
			<tr>
				<th> </th>
				<th> <openmrs:message code="general.id"/> </th>
				<th> <openmrs:message code="general.name"/> </th>
				<th> <openmrs:message code="general.description"/> </th>
				<th> <openmrs:message code="Concept.name"/> </th>	
				<th> <openmrs:message code="diseaseregistry.program"/> </th>
			</tr>
			<c:forEach var="workflow" items="${workflows}">
				<tr>
					<c:if test="${workflow.voided}">
						<td colspan="6">
							<i><openmrs:message code="general.retired"/><strike>
								<a href="workflow.form?id=${workflow.id}">${workflow.name}</a>
							</strike></i>
						</td>
					</c:if>
					<c:if test="${!workflow.voided}">
						<td valign="top"> <input type="checkbox" name="id" value="${workflow.id}">	</td>
						<td valign="top">
							${workflow.id}
						</td>
						<td valign="top">
							<a href="workflow.form?id=${workflow.id}">${workflow.name}</a>
						</td>
						<td valign="top">
							${workflow.description}
						</td>
						<openmrs:concept conceptId="${workflow.concept.conceptId}" var="v" nameVar="n" numericVar="num">
							<td valign="top">
								${n.name}
							</td>
						</openmrs:concept>						
						<td valign="top">
							<a href="program.form?programId=${workflow.program.id}">
								${workflow.program.name}
							</a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>		
		<input type="submit" value='<openmrs:message code="diseaseregistry.manage.program.delete"/>' />
		</form>
	</c:if>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>