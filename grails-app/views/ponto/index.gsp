<%--
  Created by IntelliJ IDEA.
  User: Guthierrez
  Date: 15/06/2017
  Time: 19:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ponto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.5">
    <asset:javascript src="application.js"/>
    <asset:stylesheet src="flipclock.css"/>
    <asset:javascript src="flipclock.min.js"/>
    <asset:stylesheet src="bootstrap.min.css"/>
    <asset:stylesheet src="bootstrap-theme.min.css"/>
    <asset:javascript src="bootstrap.min.js"/>
</head>

<body style="margin: 0 auto; max-width: 1100px;">
    <sec:ifAnyGranted roles="ROLE_FUNCIONARIO, ROLE_ADMINISTRADOR">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${createLink(controller: 'ponto', action: 'index')}">Home</a></li>
					<sec:ifAllGranted roles="ROLE_FUNCIONARIO">
                    	<li><a href="${createLink(controller: 'requisicaoPonto', action: 'create')}">Ponto Passado</a></li>
						<li><a href="${createLink(controller: 'saldoHoras', action: 'index')}">Meus Pontos</a></li>
					</sec:ifAllGranted>
					<sec:ifAllGranted roles="ROLE_ADMINISTRADOR">
						<li><a href="${createLink(controller: 'requisicaoPonto', action: 'index')}">Pontos Passados</a></li>
						<li><a href="${createLink(controller: 'cargaHoraria', action: 'index')}">Cargas Horárias</a></li>
						<li><a href="${createLink(controller: 'fechamentoMes', action: 'index')}">Fechamento Mês</a></li>
						<li><a href="${createLink(controller: 'feriado', action: 'index')}">Feriados</a></li>
						<li><a href="${createLink(controller: 'saldoHoras', action: 'index')}">Saldo de Horas</a></li>
						<li><a href="${createLink(controller: 'salario', action: 'index')}">Relatório Mensal</a></li>
						<li><a href="${createLink(controller: 'funcionario', action: 'index')}">Funcionários</a></li>
					</sec:ifAllGranted>
					<li><a href="${createLink(controller: 'logout')}">Logout</a></li>
                </ul>
            </div>
        </nav>

    </sec:ifAnyGranted>
    <div class="jumbotron" style="text-align: center; height: 200px">
        <h1>Controle de Ponto</h1>
    </div>
    <div style="text-align: center; width: 60%; margin: 0 auto" class="container">
        <div class="clock"></div>
        <div class="alert alert-success hidden" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <span id="success-message">${flash.message}</span>
        </div>
        <div class="alert alert-danger hidden" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <span id="error-message">${flash.error}</span>
        </div>
        <sec:ifAllGranted roles="ROLE_MAQUINA">
            <form class="form-inline">
                <div class="form-group">
                    <label class="control-label">CPF: *</label>
                    <input id="cpf" pattern= "[0-9]" name="cpf" type="number" maxlength="11" class="form-control">
                </div>
                <div class="form-group">
                    <button id="btn-registrar-ponto" class="btn btn-primary">Registrar Ponto</button>
                </div>
            </form>
        </sec:ifAllGranted>
        <sec:ifAllGranted roles="ROLE_FUNCIONARIO">
            <g:link controller="ponto" action="registrarPontoFuncionarioLogado"
                    class="btn btn-primary registrarPontoLogadoAction">
                Registrar Ponto</g:link>
        </sec:ifAllGranted>
    </div>
	<br><br><br>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${createLink(controller: 'ponto', action: 'index')}">Controle de Ponto</a>
			</div>
			<p style="margin-right: 5px;" class="navbar-text navbar-right">ZG Solucões</p>
		</div>
	</nav>
    <script type="text/javascript">
        var clock;
        $(document).ready(function() {
            clock = $('.clock').FlipClock({
                clockFace: 'TwentyFourHourClock'
            });

            $( "#btn-registrar-ponto" ).click(function(event) {
                event.preventDefault();
                $('.alert-danger').addClass('hidden');
                $('.alert-success').addClass('hidden');
                var cpf = $('#cpf').val()
                var url = "/ControleDePonto/funcionarios/" + cpf + "/pontos"
                $.post(url, function (data) {
                    var horario = new Date(data.horario);
                    $("#success-message").text(data);
                    $('.alert-success').removeClass('hidden');
                    $('#cpf').val("");
                }).fail(function(error) {
                    $('.alert-danger').removeClass('hidden');
                    $("#error-message").text(error.responseText);
                    $('#cpf').val("");
                });
            });

            if("${flash.message}"){
                $('.alert-success').removeClass('hidden');
            }

            if("${flash.error}"){
                $('.alert-danger').removeClass('hidden');
            }
        });

    </script>
</body>
</html>