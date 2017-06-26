
<%@ page import="controledeponto.FechamentoMes" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fechamentoMes.label', default: 'FechamentoMes')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-fechamentoMes" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index">Data de Fechamento</g:link></li>
			</ul>
		</div>
		<div id="show-fechamentoMes" class="content scaffold-show" role="main">
			<h1>Fechamento da Folha Salarial</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list fechamentoMes">
			
				<g:if test="${fechamentoMesInstance?.dataFechamento}">
				<li class="fieldcontain">
					<span id="dataFechamento-label" class="property-label"><g:message code="fechamentoMes.dataFechamento.label" default="Data Fechamento" /></span>
					
						<span class="property-value" aria-labelledby="dataFechamento-label"><g:fieldValue bean="${fechamentoMesInstance}" field="dataFechamento"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:fechamentoMesInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${fechamentoMesInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
