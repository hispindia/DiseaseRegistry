<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="../template/localHeader.jsp"%>

<h2><spring:message code="diseaseregistry.manage.program" /></h2>

<a href="program.form"><openmrs:message code="Program.add"/></a>

<br /><br />
<b class="boxHeader"><openmrs:message code="Program.list.title"/></b>
<div class="box">
	<c:if test="${fn:length(programList) == 0}">
		<tr>
			<td colspan="6"><openmrs:message code="general.none"/></td>
		</tr>
	</c:if>
	<c:if test="${fn:length(programList) != 0}">
		<form method="post" id="theForm">
		<table cellspacing="0" cellpadding="2">
			<tr>
				<th> </th>
				<th> <openmrs:message code="general.id"/> </th>
				<th> <openmrs:message code="general.name"/> </th>
				<th> <openmrs:message code="general.description"/> </th>
				<th> <openmrs:message code="Concept.name"/> </th>
				<th> <openmrs:message code="Program.workflows"/> </th>				
			</tr>
			<c:forEach var="program" items="${programList}">
				<tr>
					<c:if test="${program.voided}">
						<td colspan="6">
							<i><openmrs:message code="general.retired"/><strike>
								<a href="program.form?programId=${program.id}">${program.name}</a>
							</strike></i>
						</td>
					</c:if>
					<c:if test="${!program.voided}">
						<td valign="top"> <input type="checkbox" name="programId" value="${program.id}">	</td>
						<td valign="top">
							${program.id}
						</td>
						<td valign="top">
							<a href="program.form?programId=${program.id}">${program.name}</a>
						</td>
						<td valign="top">
							${program.description}
						</td>
						<openmrs:concept conceptId="${program.concept.conceptId}" var="v" nameVar="n" numericVar="num">
							<td valign="top">
								${n.name}
							</td>
						</openmrs:concept>						
					</c:if>
				</tr>
			</c:forEach>
		</table>		
		<input type="submit" value='<openmrs:message code="diseaseregistry.manage.program.delete"/>' />
		</form>
	</c:if>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>