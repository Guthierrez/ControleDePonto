
<%@ page import="controledeponto.RequisicaoPonto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'requisicaoPonto.label', default: 'RequisicaoPonto')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-requisicaoPonto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<sec:ifAllGranted roles="ROLE_FUNCIONARIO">
					<li><g:link class="create" action="create">Nova Requisição de Ponto</g:link></li>
				</sec:ifAllGranted>
			</ul>
		</div>
		<div id="list-requisicaoPonto" class="content scaffold-list" role="main">
			<h1><g:message code="requisicaoPonto.list.label" default="Requisições de Ponto Passado"/></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
						<g:sortableColumn property="id" title="${message(code: 'requisicaoPonto.id.label', default: 'Nº')}" />
					
						<g:sortableColumn property="hora" title="${message(code: 'requisicaoPonto.hora.label', default: 'Hora')}" />
					
						<g:sortableColumn property="statusRequisicao" title="${message(code: 'requisicaoPonto.statusRequisicao.label', default: 'Status Requisicao')}" />
					
						<g:sortableColumn property="justificativa" title="${message(code: 'requisicaoPonto.justificativa.label', default: 'Justificativa')}" />
					
						<th><g:message code="requisicaoPonto.funcionario.label" default="Funcionario" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${requisicaoPontoInstanceList}" status="i" var="requisicaoPontoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>${fieldValue(bean: requisicaoPontoInstance, field: "id")}</td>

						<td><g:link action="show" id="${requisicaoPontoInstance.id}">${requisicaoPontoInstance.hora.format("dd/MM/yyyy HH:mm")}</g:link></td>
					
						<td>${fieldValue(bean: requisicaoPontoInstance, field: "statusRequisicao")}</td>
					
						<td>${fieldValue(bean: requisicaoPontoInstance, field: "justificativa")}</td>
					
						<td>${fieldValue(bean: requisicaoPontoInstance, field: "funcionario")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${requisicaoPontoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
