<%@ page import="controledeponto.Funcionario" %>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="funcionario.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="required" value="${funcionarioInstance?.nome}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'cpf', 'error')} required">
	<label for="cpf">
		<g:message code="funcionario.cpf.label" default="CPF (Somente Números)" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cpf" required="required" value="${funcionarioInstance?.cpf}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'cargaHorariaMensal', 'error')} required">
	<label for="cargaHorariaMensal">
		<g:message code="funcionario.cargaHorariaMensal.label" default="Carga Horária Mensal" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cargaHorariaMensal" type="number" value="${funcionarioInstance.cargaHorariaMensal}" required="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'salario', 'error')} required">
	<label for="salario">
		<g:message code="funcionario.salario.label" default="Salario" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="salario" type="text" value="${fieldValue(bean: funcionarioInstance, field: 'salario')}" required="true"/>

</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="funcionario.username.label" default="Login" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="required" value="${funcionarioInstance?.username}" autocomplete="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="funcionario.password.label" default="Senha" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" required="required" value="${funcionarioInstance?.password}" autocomplete="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="funcionario.enabled.label" default="Ativo"/>
	</label>
	<g:checkBox name="enabled" value="${funcionarioInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: funcionarioInstance, field: 'papeis', 'error')} ">
	<label for="id">
		<g:message code="saldoHoras.papeis.label" default="Papeis" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="id" name="id" from="${controledeponto.Papel.findAll()}" optionKey="id" required="" value="${id}" multiple="multiple"/>
</div>

