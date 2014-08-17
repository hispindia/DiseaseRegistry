<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/js_css.jsp" %>
<%@ include file="../template/localHeader.jsp"%>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#conceptAutocomplete").autocomplete('${pageContext.request.contextPath}/module/diseaseregistry/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			insertConcept(item);
		});
    });

    function insertConcept(item){
		alert(item);
		jQuery("#conceptAutocomplete").val('');
	}
</script>

<input id="conceptAutocomplete" />

<%@ include file="/WEB-INF/template/footer.jsp"%>