<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<openmrs:hasPrivilege privilege="Manage Disease Registry Dashboard">
		<li class="first <c:if test='<%= request.getRequestURI().contains("/dashboard") %>'>active</c:if>">
			<a
			href="${pageContext.request.contextPath}/module/diseaseregistry/dashboard.list"><spring:message
					code="diseaseregistry.dashboard" /></a>
		</li>	
	</openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="Manage Disease Registry Program">
		<li
			<c:if test='<%= request.getRequestURI().contains("/program") %>'>class="active"</c:if>>
			<a
			href="${pageContext.request.contextPath}/module/diseaseregistry/program.list"><spring:message
					code="diseaseregistry.program" /></a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Manage Disease Registry Workflow">
		<li
			<c:if test='<%= request.getRequestURI().contains("/workflow") %>'>class="active"</c:if>>
			<a
			href="${pageContext.request.contextPath}/module/diseaseregistry/workflow.list"><spring:message
					code="diseaseregistry.workflow" /></a>
		</li>	
	</openmrs:hasPrivilege>
	<!-- Add further links here -->
</ul>
