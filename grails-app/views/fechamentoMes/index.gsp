
<%@ page import="controledeponto.FechamentoMes" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fechamentoMes.label', default: 'FechamentoMes')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-fechamentoMes" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="list-fechamentoMes" class="content scaffold-list" role="main">
			<h1>Fechamento da Folha Salarial</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="dataFechamento" title="${message(code: 'fechamentoMes.dataFechamento.label', default: 'Data Fechamento')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fechamentoMesInstanceList}" status="i" var="fechamentoMesInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>Dia de Fechamento da Folha Salarial no Mês: <g:link action="show" id="${fechamentoMesInstance.id}">${fieldValue(bean: fechamentoMesInstance, field: "dataFechamento")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fechamentoMesInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
