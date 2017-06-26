
<%@ page import="controledeponto.Ponto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ponto.label', default: 'Ponto')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ponto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ponto" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="ponto.funcionario.label" default="Funcionario" /></th>
					
						<g:sortableColumn property="horario" title="${message(code: 'ponto.horario.label', default: 'Horario')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pontoInstanceList}" status="i" var="pontoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${pontoInstance.id}">${fieldValue(bean: pontoInstance, field: "funcionario")}</g:link></td>
					
						<td><g:formatDate date="${pontoInstance.horario}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pontoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
