<%--
  Created by IntelliJ IDEA.
  User: Guthierrez
  Date: 15/06/2017
  Time: 10:41
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="bootbox.min.js"/>
    <title>Saldo de Horas</title>
</head>

<body>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
<g:form url="[action:'show']" >
    <fieldset class="form">
        <div class="fieldcontain required">
            <label for="inicio">
                <g:message code="saldoHoras.inicio.label" default="Início" />
                <span class="required-indicator">*</span>
            </label>
            <g:datePicker name="inicio" precision="day"  value="${inicio}"  />
        </div>
        <div class="fieldcontain required">
            <label for="fim">
                <g:message code="saldoHoras.fim.label" default="Fim" />
                <span class="required-indicator">*</span>
            </label>
            <g:datePicker name="fim" precision="day"  value="${fim}"  />
        </div>
		<sec:ifAllGranted roles="ROLE_ADMINISTRADOR">
			<div class="fieldcontain required">
				<label for="id">
					<g:message code="saldoHoras.funcionario.label" default="Funcionário" />
					<span class="required-indicator">*</span>
				</label>
				<g:select id="id" name="id" from="${funcionarios}" optionKey="id" required="" value="${id}"/>
			</div>
		</sec:ifAllGranted>
    </fieldset>
    <br><br>
    <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="Buscar" />
    </fieldset>
</g:form>
</body>
</html>