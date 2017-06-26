
<%@ page import="controledeponto.StatusRequisicao; controledeponto.RequisicaoPonto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'requisicaoPonto.label', default: 'RequisicaoPonto')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<asset:stylesheet src="bootstrap.min.css"/>
		<asset:stylesheet src="bootstrap-theme.min.css"/>
		<asset:javascript src="bootstrap.min.js"/>
	</head>
	<body>
		<a href="#show-requisicaoPonto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<sec:ifAllGranted roles="ROLE_FUNCIONARIO">
					<li><g:link class="create" action="create">Nova Requisição de Ponto</g:link></li>
				</sec:ifAllGranted>
			</ul>
		</div>
		<div id="show-requisicaoPonto" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list requisicaoPonto">
			
				<g:if test="${requisicaoPontoInstance?.hora}">
				<li class="fieldcontain">
					<span id="hora-label" class="property-label"><g:message code="requisicaoPonto.hora.label" default="Hora" /></span>
					
						<span class="property-value" aria-labelledby="hora-label"><g:formatDate date="${requisicaoPontoInstance?.hora}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${requisicaoPontoInstance?.statusRequisicao}">
				<li class="fieldcontain">
					<span id="statusRequisicao-label" class="property-label"><g:message code="requisicaoPonto.statusRequisicao.label" default="Status Requisicao" /></span>
					
						<span class="property-value" aria-labelledby="statusRequisicao-label"><g:fieldValue bean="${requisicaoPontoInstance}" field="statusRequisicao"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${requisicaoPontoInstance?.justificativa}">
				<li class="fieldcontain">
					<span id="justificativa-label" class="property-label"><g:message code="requisicaoPonto.justificativa.label" default="Justificativa" /></span>
					
						<span class="property-value" aria-labelledby="justificativa-label"><g:fieldValue bean="${requisicaoPontoInstance}" field="justificativa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${requisicaoPontoInstance?.funcionario}">
				<li class="fieldcontain">
					<span id="funcionario-label" class="property-label"><g:message code="requisicaoPonto.funcionario.label" default="Funcionario" /></span>
						<span class="property-value" aria-labelledby="funcionario-label"><g:link controller="funcionario" action="show" id="${requisicaoPontoInstance?.funcionario?.id}">${requisicaoPontoInstance?.funcionario?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>

				<sec:ifAllGranted roles="ROLE_ADMINISTRADOR">
					<g:if test="${requisicaoPontoInstance.statusRequisicao == controledeponto.StatusRequisicao.PENDENTE}">
						<li class="fieldcontain">
							<span class="property-label">
								<a  href="${createLink(action: 'analisarSolicitacao', params: [id : "${requisicaoPontoInstance.id}", aprovar: "true"])}"
									class="btn btn-primary">
									Aprovar
								</a>
							</span>
							<span class="property-value">
								<a  href="${createLink(action: 'analisarSolicitacao', params: [id : "${requisicaoPontoInstance.id}", aprovar: "false"])}"
									class="btn btn-danger">
									Reprovar
								</a>

							</span>
						</li>
					</g:if>
				</sec:ifAllGranted>
			
			</ol>

			<g:form url="[resource:requisicaoPontoInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					%{--<g:link class="edit" action="edit" resource="${requisicaoPontoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
					%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
