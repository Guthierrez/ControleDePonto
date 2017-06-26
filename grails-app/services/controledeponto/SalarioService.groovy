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
		def (salarioDoDia, horasNoturnaExtra, horasNoturnaComum, horasExtraComum, horasComum)  = [0.0, 0.0, 0.0, 0.0, 0.0];
		BigDecimal horaExtraTotal = registroDiario.horasTrabalhadas - registroDiario.horasEsperadas
		BigDecimal horasNoturnasTotal = registroDiario.horasNoturnas

		if(horaExtraTotal > 0){
			if(registroDiario.horasNoturnas > horaExtraTotal){
				horasNoturnaExtra = horaExtraTotal
			}else{
				horasNoturnaExtra = registroDiario.horasNoturnas
				horasExtraComum = horaExtraTotal - registroDiario.horasNoturnas
			}
		}
		horasNoturnaComum = horasNoturnasTotal - horasNoturnaExtra
		horasComum = registroDiario.horasTrabalhadas - horasExtraComum - horasNoturnaExtra - horasNoturnaComum

		salarioDoDia += valorHora * BONUS_HORA_NOTURNA * BONUS_HORA_EXTRA_COMUM * horasNoturnaExtra
		salarioDoDia += valorHora * BONUS_HORA_EXTRA_COMUM * horasExtraComum
		salarioDoDia += valorHora * BONUS_HORA_NOTURNA * horasNoturnaComum
		salarioDoDia += valorHora * horasComum
		return salarioDoDia
	}

	BigDecimal getSalarioDiaDomingosFeriados(BigDecimal valorHora, RegistroDiario registroDiario){
		if(registroDiario.horasNoturnas > 0){
			valorHora *= BONUS_HORA_NOTURNA
		}
		return registroDiario.horasTrabalhadas * valorHora * BONUS_HORA_FERIADO
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
