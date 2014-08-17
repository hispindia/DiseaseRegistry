<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first"
		<c:if test='<%= request.getRequestURI().contains("/dashboard") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/diseaseregistry/dashboard"><spring:message
				code="diseaseregistry.dashboard" /></a>
	</li>
	<li
		<c:if test='<%= request.getRequestURI().contains("/program") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/diseaseregistry/program.list"><spring:message
				code="diseaseregistry.program" /></a>
	</li>	
	<!-- Add further links here -->
</ul>
