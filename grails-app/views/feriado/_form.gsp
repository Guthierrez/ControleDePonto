<%@ page import="controledeponto.Feriado" %>



<div class="fieldcontain ${hasErrors(bean: feriadoInstance, field: 'dia', 'error')} required">
	<label for="dia">
		<g:message code="feriado.dia.label" default="Dia" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dia" precision="day"  value="${feriadoInstance?.dia}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: feriadoInstance, field: 'descricao', 'error')} required">
	<label for="descricao">
		<g:message code="feriado.descricao.label" default="Descricao" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descricao" required="" value="${feriadoInstance?.descricao}"/>

</div>

