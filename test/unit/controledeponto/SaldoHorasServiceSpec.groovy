package controledeponto

import controledeponto.model.RegistroDiario
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import spock.lang.Specification
import spock.lang.Unroll
import static controledeponto.DiaSemana.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SaldoHorasService)
@Mock([PontoService, CargaHoraria, Feriado])
class SaldoHorasServiceSpec extends Specification {

	void "teste geração registro diário de saldo de horas do funcionário"(){
		given:
		LocalDate dia = new LocalDate(2017, 1, 6)
		LocalDateTime pontoUm = new LocalDateTime(2017, 1, 6, 8, 0, 0)
		LocalDateTime pontoDois = new LocalDateTime(2017, 1, 6, 12, 15, 0)
		LocalDateTime pontoTres = new LocalDateTime(2017, 1, 6, 13,  0, 0)
		LocalDateTime pontoQuatro = new LocalDateTime(2017, 1, 6, 17, 15, 0)
		List<LocalDateTime> pontos = [pontoUm, pontoDois, pontoTres, pontoQuatro]
		CargaHoraria cargaHorariaDoDia = new CargaHoraria(diaSemana: DiaSemana.QUINTA, diaUtil: true, quantidadeHoras: 8)

		when:
		RegistroDiario registroDiario = service.getSaldoDiario(dia, pontos, cargaHorariaDoDia)

		then:
		registroDiario.dia == dia
		registroDiario.diaUtil == true
		registroDiario.pontoRegistrados.containsAll(pontos)
		registroDiario.horasEsperadas == 8
		registroDiario.horasTrabalhadas == 8.50
	}

	@Unroll
	void "teste número horas trabalhadas no dia"(){
		given:
		BigDecimal quantidadeHoras = service.getHorasTrabalhadasDia(horariosDoDia)

		expect:
		quantidadeHoras == resultado

		where:
		resultado | horariosDoDia
		0.0       | []
		8.0       | [new LocalTime(8, 0, 0), new LocalTime(16, 0 ,0)]
		4.0       | [new LocalTime(8, 0, 0), new LocalTime(12, 0 ,0), new LocalTime(14, 0, 0)]
		4.5       | [new LocalTime(8, 0, 0), new LocalTime(12, 0 ,0), new LocalTime(14, 0, 0), new LocalTime(14, 30, 0)]
		0.0       | [new LocalTime(8, 0, 0)]
	}

	@Unroll
	void "teste quantidade de horas esperadas para um determinado dia"(){
		given:
		CargaHoraria.findOrSaveByDiaSemanaAndQuantidadeHorasAndDiaUtil(TERCA, 8, true)
		CargaHoraria.findOrSaveByDiaSemanaAndQuantidadeHorasAndDiaUtil(SABADO, 4, true)
		CargaHoraria.findOrSaveByDiaSemanaAndQuantidadeHorasAndDiaUtil(DOMINGO, 0, false)
		List<CargaHoraria> cargasHorarias = CargaHoraria.findAll()

		when:
		DiaSemana diaSemana = DiaSemana.getByValor(dia.getDayOfWeek())
		CargaHoraria cargaHorariaDoDia = cargasHorarias.find{it.getDiaSemana() == diaSemana}
		BigDecimal quantidadehoras = service.getHorasEsperadasParaDia(cargaHorariaDoDia, cargaHorariaDoDia.getDiaUtil())

		then:
		quantidadehoras == resultado

		where:
		resultado |                 dia
		  8       |   new LocalDate(2017, 6 , 20)
		  4       |   new LocalDate(2017, 6 , 24)
		  0       |   new LocalDate(2017, 6 , 25)
	}

	@Unroll
	void "teste quantidade de horas esperadas para um feriado (simulando ser hoje)"(){
		given:
		LocalDate hojeFeriado = new LocalDate()
		CargaHoraria cargaHorariaDoDia = new CargaHoraria(quantidadeHoras: 8, diaSemana: DiaSemana.getByValor(hojeFeriado.dayOfWeek), diaUtil: false)
		Feriado.findOrSaveByDescricaoAndDia("Dia da Preguiça!", hojeFeriado.toDate())

		when:
		BigDecimal horas = service.getHorasEsperadasParaDia(cargaHorariaDoDia, cargaHorariaDoDia.getDiaUtil())

		then:
		horas == 0.0
	}

	@Unroll
	void "teste se um determinado dia é um dia útil"(){
		given:
		CargaHoraria.findOrSaveByDiaSemanaAndQuantidadeHorasAndDiaUtil(TERCA, 8, true)
		CargaHoraria.findOrSaveByDiaSemanaAndQuantidadeHorasAndDiaUtil(SABADO, 4, true)
		CargaHoraria.findOrSaveByDiaSemanaAndQuantidadeHorasAndDiaUtil(DOMINGO, 0, false)
		List<CargaHoraria> cargasHorarias = CargaHoraria.findAll()

		when:
		DiaSemana diaSemana = DiaSemana.getByValor(dia.getDayOfWeek())
		CargaHoraria cargaHorariaDoDia = cargasHorarias.find{it.getDiaSemana() == diaSemana}
		Boolean isDiaUtil = cargaHorariaDoDia.getDiaUtil()

		then:
		isDiaUtil == resultado

		where:
		resultado |                 dia
		true      |   new LocalDate(2017, 6 , 20)
		true      |   new LocalDate(2017, 6 , 24)
		false     |   new LocalDate(2017, 6 , 25)
	}

	@Unroll
    void "teste calculo número de datas entre periodo"() {
		given:
		Map<LocalDate, List<LocalDateTime>> datas = service.getDatasVaziasEntrePeriodo(inicio, fim)

		expect:
		datas.size() == size
		datas.containsKey(inicio)
		datas.containsKey(fim)

		where:
		size  |                inicio               | fim
		7     |new LocalDate(2017, 6 ,1)  | new LocalDate(2017, 6 ,7)
		31    |new LocalDate(2017, 7 ,25) | new LocalDate(2017, 8 ,24)
		45    |new LocalDate(2017, 7 ,25) | new LocalDate(2017, 9 ,7)
     }
}
