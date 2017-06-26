<%--
  Created by IntelliJ IDEA.
  User: guthierrezsouza
  Date: 19/06/17
  Time: 10:43
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
				<g:message code="salario.mes" default="Mês e Ano" />
				<span class="required-indicator">*</span>
			</label>
			<g:datePicker name="mes" precision="month"  value="${mes}"  />
		</div>
		<div class="fieldcontain required">
			<label for="id">
				<g:message code="saldoHoras.funcionario.label" default="Funcionário" />
				<span class="required-indicator">*</span>
			</label>
			<g:select id="id" name="id" from="${funcionarios}" optionKey="id" required="" value="${id}"/>
		</div>
	</fieldset>
	<fieldset class="buttons">
		<g:submitButton name="create" class="save" value="Buscar" />
	</fieldset>
</g:form>
</body>
</html>