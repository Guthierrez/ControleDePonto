<%--
  Created by IntelliJ IDEA.
  User: guthierrezsouza
  Date: 16/06/17
  Time: 10:35
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Saldo de Horas</title>
</head>

<body>
	<br>
	<span style="margin-left: 5px;"><b>Relatório Mensal para <g:formatDate date="${mes}" format="MMMM 'de' yyyy"/></b></span>
	<br><br>
	<div style="margin-left: 20px">
		<b>Funcionário:</b> ${funcionario.nome}
		<br>
		<b>CPF:</b> ${funcionario.cpf}
		<br>
		<b>Horas Esperadas no período:</b> <saldoHoras:bigDecimalToHoras quantidadeHoras="${saldoTotalHoras.totalEsperadoPeriodo}"/>
		<br>
		<b>Horas Trabalhadas no período:</b> <saldoHoras:bigDecimalToHoras quantidadeHoras="${saldoTotalHoras.totalTrabalhadoPeriodo}"/>
		<br>
		<b>Saldo de Horas final no Período:</b> <saldoHoras:bigDecimalIntervalToHoras
			horasEsperadas="${saldoTotalHoras.totalEsperadoPeriodo}"
			horasTrabalhadas="${saldoTotalHoras.totalTrabalhadoPeriodo}"/>
		<br>
		<b>Remuneração no período:</b> <g:formatNumber number="${salario}" type="currency" currencyCode="BRL" />
	</div>
	<br>
	<span style="margin-left: 5px;"><b>Pontos Registrados no Mês</b></span>
	<br><br>
	<table>
		<thead>
		<tr style="font-weight: bold">
			<td>Dia</td>
			<td>Pontos do Dia</td>
			<td>Horas Esperadas</td>
			<td>Horas Trabalhadas (Noturnas)</td>
			<td>Saldo do Dia</td>
		</tr>
		</thead>
		<tbody>
		<g:each in="${saldoTotalHoras.saldosDiarios}" var="saldo" status="i">
			<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
				<td>
					<g:formatDate format="dd/MM EEEE" date="${saldo.dia.toDate()}"/>
				</td>
				<td>
					<g:if test="${saldo.pontoRegistrados.isEmpty()}">
						<span>-------</span>
					</g:if>
					<g:each in="${saldo.pontoRegistrados}" var="horario">
						<g:formatDate format="HH:mm" date="${horario.toDate()}"/>
					</g:each>
				</td>
				<td>
					<g:if test="${saldo.diaUtil || saldo.dia.dayOfWeek == 7}">
						<saldoHoras:bigDecimalToHoras quantidadeHoras="${saldo.horasEsperadas}"/>
					</g:if>
					<g:if test="${!saldo.diaUtil && saldo.dia.dayOfWeek != 7}">
						Feriado
					</g:if>
				</td>
				<td><saldoHoras:bigDecimalToHoras quantidadeHoras="${saldo.horasTrabalhadas}"/> (<saldoHoras:bigDecimalToHoras quantidadeHoras="${saldo.horasNoturnas}"/>)</td>
				<td><saldoHoras:bigDecimalIntervalToHoras horasEsperadas="${saldo.horasEsperadas}" horasTrabalhadas="${saldo.horasTrabalhadas}"/> </td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
	</div>
</body>
</html>
