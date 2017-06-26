<%@ page import="controledeponto.Ponto" %>



<div class="fieldcontain ${hasErrors(bean: pontoInstance, field: 'funcionario', 'error')} required">
	<label for="funcionario">
		<g:message code="ponto.funcionario.label" default="Funcionario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="funcionario" name="funcionario.id" from="${controledeponto.Funcionario.list()}" optionKey="id" required="" value="${pontoInstance?.funcionario?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: pontoInstance, field: 'horario', 'error')} required">
	<label for="horario">
		<g:message code="ponto.horario.label" default="Horario" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="horario" precision="day"  value="${pontoInstance?.horario}"  />

</div>

