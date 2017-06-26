
<%@ page import="controledeponto.CargaHoraria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cargaHoraria.label', default: 'CargaHoraria')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cargaHoraria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index">Cargas Horárias</g:link></li>
				<li><g:link class="create" action="create">Nova Carga Horária</g:link></li>
			</ul>
		</div>
		<div id="show-cargaHoraria" class="content scaffold-show" role="main">
			<h1>Carga Horária</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cargaHoraria">
			
				<g:if test="${cargaHorariaInstance?.diaSemana}">
				<li class="fieldcontain">
					<span id="diaSemana-label" class="property-label"><g:message code="cargaHoraria.diaSemana.label" default="Dia Semana" /></span>
					
						<span class="property-value" aria-labelledby="diaSemana-label"><g:fieldValue bean="${cargaHorariaInstance}" field="diaSemana"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cargaHorariaInstance?.diaUtil}">
				<li class="fieldcontain">
					<span id="diaUtil-label" class="property-label"><g:message code="cargaHoraria.diaUtil.label" default="Dia Util" /></span>
					
						<span class="property-value" aria-labelledby="diaUtil-label"><g:formatBoolean boolean="${cargaHorariaInstance?.diaUtil}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${cargaHorariaInstance?.quantidadeHoras}">
				<li class="fieldcontain">
					<span id="quantidadeHoras-label" class="property-label"><g:message code="cargaHoraria.quantidadeHoras.label" default="Quantidade Horas" /></span>
					
						<span class="property-value" aria-labelledby="quantidadeHoras-label"><g:fieldValue bean="${cargaHorariaInstance}" field="quantidadeHoras"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:cargaHorariaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${cargaHorariaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
