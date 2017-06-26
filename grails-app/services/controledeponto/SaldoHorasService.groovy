package controledeponto

import controledeponto.model.RegistroDiario
import controledeponto.model.SaldoTotalHoras
import grails.transaction.Transactional
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.Period

import java.math.RoundingMode

@Transactional
class SaldoHorasService {

	private static final LocalTime finalHoraNoturna = new LocalTime(5, 0, 0)
	private static final LocalTime inicioHoraNoturna = new LocalTime(22, 0, 0)
	private static final LocalTime finalDoDia = new LocalTime(23, 59, 59)

	def pontoService

	SaldoTotalHoras getSaldoTotalHorasByFuncionarioAndPeriodo(Funcionario funcionario, Date inicio, Date fim) {
		List<Ponto> pontos = pontoService.findPontosByFuncionarioAndPeriodo(funcionario, inicio, fim)
		List<CargaHoraria> cargasHorarias = CargaHoraria.findAll()
		Map<LocalDate, List<LocalDateTime>> pontosRegistradosPorDia = this.getPontosPorDiaEntrePeriodo(pontos, new LocalDate(inicio), new LocalDate(fim))
		SaldoTotalHoras saldoTotalPeriodo = new SaldoTotalHoras(saldosDiarios: [], totalEsperadoPeriodo: 0.0, totalTrabalhadoPeriodo: 0.0)

		pontosRegistradosPorDia.each {dia, horarios ->
			DiaSemana diaSemana = DiaSemana.getByValor(dia.getDayOfWeek())
			CargaHoraria cargaHorariaDoDia = cargasHorarias.find{it.getDiaSemana() == diaSemana}
			RegistroDiario saldoDiario = this.getSaldoDiario(dia, horarios, cargaHorariaDoDia)

			saldoTotalPeriodo.saldosDiarios.add(saldoDiario)
			saldoTotalPeriodo.totalEsperadoPeriodo += saldoDiario.getHorasEsperadas()
			saldoTotalPeriodo.totalTrabalhadoPeriodo += saldoDiario.getHorasTrabalhadas()
		}
		return saldoTotalPeriodo
	}

	RegistroDiario getSaldoDiario(LocalDate dia, List<LocalDateTime> horarios, CargaHoraria cargaHorariaDoDia){
		Boolean isDiaUtil = this.isDiaUtil(dia, cargaHorariaDoDia)
		BigDecimal horasEsperadas = this.getHorasEsperadasParaDia(cargaHorariaDoDia, isDiaUtil)
		BigDecimal horasTrabalhadas = this.getHorasTrabalhadasDia(horarios)
		BigDecimal horasNoturnasTrabalhadas = this.getHorasNoturnasTrabalhadasDia(horarios)

		return new RegistroDiario(dia: dia, pontoRegistrados: horarios, horasTrabalhadas: horasTrabalhadas,
				horasEsperadas: horasEsperadas, horasNoturnas: horasNoturnasTrabalhadas, diaUtil: isDiaUtil)
	}

	Boolean isDiaUtil(LocalDate dia, CargaHoraria cargaHorariaDoDia){
		if(dia.toDate().clearTime() in Feriado.findAll().collect{it.getDia().clearTime()}){
			return false
		}else{
			return cargaHorariaDoDia.getDiaUtil()
		}
	}

	BigDecimal getHorasEsperadasParaDia(CargaHoraria cargaHorariaDoDia, Boolean isDiaUtil){
		return isDiaUtil ? cargaHorariaDoDia.getQuantidadeHoras() : 0
	}

	BigDecimal getHorasTrabalhadasDia(List<LocalDateTime> horariosDoDia){
		BigDecimal horas = 0.0
		List<List<LocalDateTime>> periodos = horariosDoDia ? horariosDoDia.collate(2) : []

		periodos.each { periodo ->
			Period horasDoPeriodo = new Period(periodo.first(), periodo.last())
			horas += this.convertPeriodToBigDecimal(horasDoPeriodo)
		}
		return horas
	}

	BigDecimal getHorasNoturnasTrabalhadasDia(List<LocalDateTime> horariosDoDia){
		BigDecimal horas = 0.0
		List<List<LocalDateTime>> periodos = horariosDoDia ? horariosDoDia.collate(2) : []
		periodos.each { periodo ->
			if(periodo.size() == 2){
				horas += this.calcularHorasNoturnasPeriodo(periodo.first().toLocalTime(), periodo.last().toLocalTime())
			}
		}
		return horas
	}

	BigDecimal calcularHorasNoturnasPeriodo(LocalTime entrada, LocalTime saida){
		Period horasNoturnas = new Period()

		if(entrada < finalHoraNoturna){
			if(saida < finalHoraNoturna){
				horasNoturnas += new Period(entrada, saida)
			}else{
				horasNoturnas += new Period(entrada, finalHoraNoturna)
			}
		}
		if(entrada > inicioHoraNoturna){
			if(saida < finalDoDia){
				horasNoturnas += new Period(entrada, saida)
			}else{
				horasNoturnas += new Period(entrada, finalDoDia)
			}
		}else{
			if(saida > inicioHoraNoturna){
				horasNoturnas += new Period(inicioHoraNoturna, saida)
			}
		}
		println(horasNoturnas)
		return convertPeriodToBigDecimal(horasNoturnas)
	}

	Map<LocalDate, List<LocalDateTime>> getPontosPorDiaEntrePeriodo(List<Ponto> pontos, LocalDate inicio, LocalDate fim){
		List<LocalDateTime> diasHorarios = pontos.collect{new LocalDateTime(it.getHorario())}
		Map<LocalDate, List<LocalDateTime>> pontosRegistradosPorDia = diasHorarios.groupBy {it.toLocalDate()}
		Map<LocalDate, List<LocalDateTime>> periodoTotalDeTrabalho = this.getDatasVaziasEntrePeriodo(inicio,fim)

		periodoTotalDeTrabalho.putAll(pontosRegistradosPorDia)
		return periodoTotalDeTrabalho
	}

	Map<LocalDate, List<LocalDateTime>> getDatasVaziasEntrePeriodo(LocalDate inicio, LocalDate fim){
		Integer tamanhoIntervalo = Days.daysBetween(inicio, fim).getDays()
		Map<LocalDate, List<LocalDateTime>> datasEntrePeriodo = [:]

		(0..tamanhoIntervalo).each {
			datasEntrePeriodo.put(inicio.plusDays(it), [])
		}
		return datasEntrePeriodo
	}

	private BigDecimal convertPeriodToBigDecimal(Period period){
		BigDecimal horas = period.getHours()
		horas += period.getMinutes() / 60.0
		horas += period.getSeconds() / 60.0 / 60.0
		return horas.setScale(3, RoundingMode.HALF_UP)
	}
}