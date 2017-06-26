package controledeponto

import controledeponto.model.SaldoTotalHoras

class SaldoHorasController {

    def saldoHorasService
	def springSecurityService

    def index() {
        return [funcionarios : Funcionario.findAll()]
    }

    def show(){
        Funcionario funcionario = Funcionario.get(params.getLong("id"))
		if(!funcionario){
			funcionario = Funcionario.findByCpf(springSecurityService.getCurrentUser().cpf)
		}
		SaldoTotalHoras saldosTotalHoras = saldoHorasService.getSaldoTotalHorasByFuncionarioAndPeriodo(funcionario, params.inicio, params.fim)
        return [saldoTotalHoras: saldosTotalHoras, funcionario: funcionario, inicio: params.inicio, fim: params.fim]
    }
}
