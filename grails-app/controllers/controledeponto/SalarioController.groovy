package controledeponto

import controledeponto.model.RegistroDiario
import controledeponto.model.SaldoTotalHoras

class SalarioController {

    def salarioService

    def index() {
        return [funcionarios : Funcionario.findAll()]
    }

    def show(){
        Funcionario funcionario = Funcionario.get(params.getLong("id"))
        SaldoTotalHoras saldoTotalHorasPeriodoMensal = salarioService.getSaldoTotalHorasPeriodoMensal(funcionario, params.mes as Date)
		BigDecimal salario = salarioService.getSalarioPeriodoMensal(funcionario, saldoTotalHorasPeriodoMensal)

		return [saldoTotalHoras: saldoTotalHorasPeriodoMensal, salario: salario, funcionario: funcionario, mes: params.mes]
    }
}
