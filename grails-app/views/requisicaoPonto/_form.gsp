<%@ page import="controledeponto.RequisicaoPonto" %>



<div class="fieldcontain ${hasErrors(bean: requisicaoPontoInstance, field: 'hora', 'error')} required">
	<label for="hora">
		<g:message code="requisicaoPonto.hora.label" default="Hora" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="hora" precision="minute"  value="${requisicaoPontoInstance?.hora}"  />

</div>

%{--<div class="fieldcontain ${hasErrors(bean: requisicaoPontoInstance, field: 'statusRequisicao', 'error')} required">--}%
	%{--<label for="statusRequisicao">--}%
		%{--<g:message code="requisicaoPonto.statusRequisicao.label" default="Status Requisicao" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:select name="statusRequisicao" from="${controledeponto.StatusRequisicao?.values()}" keys="${controledeponto.StatusRequisicao.values()*.name()}" required="" value="${requisicaoPontoInstance?.statusRequisicao?.name()}" />--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: requisicaoPontoInstance, field: 'justificativa', 'error')} required">
	<label for="justificativa">
		<g:message code="requisicaoPonto.justificativa.label" default="Justificativa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="justificativa" required="" value="${requisicaoPontoInstance?.justificativa}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: requisicaoPontoInstance, field: 'funcionario', 'error')} required">
	<label for="funcionario">
		<g:message code="requisicaoPonto.funcionario.label" default="Funcionario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="funcionario"
			  name="funcionario.id"
			  from="${controledeponto.Funcionario.list()}"
			  optionKey="id"
			  required=""
			  value="${requisicaoPontoInstance?.funcionario?.id}" class="many-to-one"
		      disabled="true"
	/>
	<g:select id="funcionario" name="funcionario.id" from="${controledeponto.Funcionario.list()}" optionKey="id" value="${requisicaoPontoInstance?.funcionario?.id}" class="many-to-one" readonly="true" hidden="true"/>
</div>

