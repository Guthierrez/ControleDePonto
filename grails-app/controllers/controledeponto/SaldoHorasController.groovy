package controledeponto

import controledeponto.model.SaldoTotalHoras

class SaldoHorasController {

    def saldoHorasService
	def springSecurityService

    def index() {
		Funcionario funcionario = null
		if(springSecurityService.getCurrentUser().getAuthorities().any {it.authority == "ROLE_FUNCIONARIO"}){
			String cpf = springSecurityService.getCurrentUser()?.cpf
			funcionario = Funcionario.findByCpf(cpf)
		}
        return [funcionarios : Funcionario.findAll(), funcionario: funcionario]
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
