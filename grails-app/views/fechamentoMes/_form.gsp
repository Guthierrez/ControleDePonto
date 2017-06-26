<%@ page import="controledeponto.FechamentoMes" %>



<div class="fieldcontain ${hasErrors(bean: fechamentoMesInstance, field: 'dataFechamento', 'error')} required">
	<label for="dataFechamento">
		<g:message code="fechamentoMes.dataFechamento.label" default="Data Fechamento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="dataFechamento" type="number" value="${fechamentoMesInstance.dataFechamento}" required=""/>

</div>

