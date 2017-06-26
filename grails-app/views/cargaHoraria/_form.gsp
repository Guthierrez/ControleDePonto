<%@ page import="controledeponto.CargaHoraria" %>



<div class="fieldcontain ${hasErrors(bean: cargaHorariaInstance, field: 'diaSemana', 'error')} required">
	<label for="diaSemana">
		<g:message code="cargaHoraria.diaSemana.label" default="Dia da Semana" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="diaSemana" from="${controledeponto.DiaSemana?.values()}" keys="${controledeponto.DiaSemana.values()*.name()}" required="" value="${cargaHorariaInstance?.diaSemana?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: cargaHorariaInstance, field: 'diaUtil', 'error')} ">
	<label for="diaUtil">
		<g:message code="cargaHoraria.diaUtil.label" default="Dia Útil" />
		
	</label>
	<g:checkBox name="diaUtil" value="${cargaHorariaInstance?.diaUtil}" />

</div>

<div class="fieldcontain ${hasErrors(bean: cargaHorariaInstance, field: 'quantidadeHoras', 'error')} required">
	<label for="quantidadeHoras">
		<g:message code="cargaHoraria.quantidadeHoras.label" default="Quantidade de Horas" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="quantidadeHoras" type="number" value="${cargaHorariaInstance.quantidadeHoras}" required=""/>

</div>

