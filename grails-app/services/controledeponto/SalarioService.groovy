package controledeponto

import controledeponto.model.RegistroDiario
import controledeponto.model.SaldoTotalHoras
import grails.transaction.Transactional
import org.joda.time.LocalDate

@Transactional
class SalarioService {

	private static final BigDecimal BONUS_HORA_NOTURNA = 1.2
	private static final BigDecimal BONUS_HORA_EXTRA_COMUM = 1.5
	private static final BigDecimal BONUS_HORA_FERIADO = 2

	def saldoHorasService

	SaldoTotalHoras getSaldoTotalHorasPeriodoMensal(Funcionario funcionario, Date mes){
		return saldoHorasService.getSaldoTotalHorasByFuncionarioAndPeriodo(funcionario, getDataInicioRelatorioMensal(mes), getDataFimRelatorioMensal(mes))
	}

    BigDecimal getSalarioPeriodoMensal(Funcionario funcionario, SaldoTotalHoras saldoTotalHorasPeriodo){
		BigDecimal salarioPeriodo = 0.0
		BigDecimal valorHora = funcionario.getSalario() / saldoTotalHorasPeriodo.getTotalEsperadoPeriodo()

		saldoTotalHorasPeriodo.saldosDiarios.each {
			salarioPeriodo += getSalarioDia(valorHora, it)
		}
		return salarioPeriodo
	}

	BigDecimal getSalarioDia(BigDecimal valorHora, RegistroDiario registroDiario){
		BigDecimal salarioDoDia = 0.0

		if(!registroDiario.diaUtil){
			salarioDoDia += getSalarioDiaDomingosFeriados(valorHora, registroDiario)
		}else{
			salarioDoDia += getSalarioDiasUteis(valorHora, registroDiario)
		}

		return salarioDoDia
	}

	BigDecimal getSalarioDiasUteis(BigDecimal valorHora, RegistroDiario registroDiario){
		def (salarioDoDia, horasNoturnasExtras, horasNoturnasComuns, horasExtrasComuns, horasComuns)  = [0.0, 0.0, 0.0, 0.0, 0.0]
		BigDecimal horaExtrasTotais = registroDiario.horasTrabalhadas - registroDiario.horasEsperadas
		BigDecimal horasNoturnasTotais = registroDiario.horasNoturnas

		if(horaExtrasTotais > 0){
			if(registroDiario.horasNoturnas > horaExtrasTotais){
				horasNoturnasExtras = horaExtrasTotais
			}else{
				horasNoturnasExtras = registroDiario.horasNoturnas
				horasExtrasComuns = horaExtrasTotais - registroDiario.horasNoturnas
			}
		}
		horasNoturnasComuns = horasNoturnasTotais - horasNoturnasExtras
		horasComuns = registroDiario.horasTrabalhadas - horasExtrasComuns - horasNoturnasExtras - horasNoturnasComuns

		salarioDoDia += valorHora * BONUS_HORA_NOTURNA * BONUS_HORA_EXTRA_COMUM * horasNoturnasExtras
		salarioDoDia += valorHora * BONUS_HORA_EXTRA_COMUM * horasExtrasComuns
		salarioDoDia += valorHora * BONUS_HORA_NOTURNA * horasNoturnasComuns
		salarioDoDia += valorHora * horasComuns
		return salarioDoDia
	}

	BigDecimal getSalarioDiaDomingosFeriados(BigDecimal valorHora, RegistroDiario registroDiario){
		BigDecimal salarioDia = 0.0
		BigDecimal horasComuns = registroDiario.horasTrabalhadas
		if(registroDiario.horasNoturnas > 0){
			horasComuns = horasComuns - registroDiario.horasNoturnas
		}
		salarioDia += valorHora * BONUS_HORA_NOTURNA * BONUS_HORA_FERIADO * registroDiario.horasNoturnas
		salarioDia += valorHora * BONUS_HORA_FERIADO * horasComuns

		return salarioDia
	}

	Date getDataInicioRelatorioMensal(Date mes){
		Integer diaFechamento = FechamentoMes.findAll().first().getDataFechamento()
		LocalDate inicio = new LocalDate(mes)
		inicio = inicio.minusMonths(1).withDayOfMonth(diaFechamento + 1)
		return inicio.toDate()
	}

	Date getDataFimRelatorioMensal(Date mes){
		Integer diaFechamento = FechamentoMes.findAll().first().getDataFechamento()
		LocalDate fim = new LocalDate(mes)
		fim = fim.withDayOfMonth(diaFechamento)
		return fim.toDate()
	}
}
