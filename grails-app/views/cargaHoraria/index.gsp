
<%@ page import="controledeponto.CargaHoraria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cargaHoraria.label', default: 'CargaHoraria')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-cargaHoraria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create">Nova Carga Horária</g:link></li>
			</ul>
		</div>
		<div id="list-cargaHoraria" class="content scaffold-list" role="main">
			<h1>Cargas Horárias Cadastradas</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="diaSemana" title="${message(code: 'cargaHoraria.diaSemana.label', default: 'Dia Semana')}" />
					
						<g:sortableColumn property="diaUtil" title="${message(code: 'cargaHoraria.diaUtil.label', default: 'Dia Util')}" />
					
						<g:sortableColumn property="quantidadeHoras" title="${message(code: 'cargaHoraria.quantidadeHoras.label', default: 'Quantidade Horas')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${cargaHorariaInstanceList}" status="i" var="cargaHorariaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${cargaHorariaInstance.id}">${fieldValue(bean: cargaHorariaInstance, field: "diaSemana")}</g:link></td>
					
						<td><g:formatBoolean boolean="${cargaHorariaInstance.diaUtil}" /></td>
					
						<td>${fieldValue(bean: cargaHorariaInstance, field: "quantidadeHoras")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${cargaHorariaInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
