package controledeponto

import controledeponto.model.RegistroDiario
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SalarioService)
@Mock(FechamentoMes)
class SalarioServiceSpec extends Specification {

	@Unroll
	void "teste valor salário do dia em dias uteis com bonus de horas extras comum"(){
		when:
		RegistroDiario registroDiario = new RegistroDiario(horasTrabalhadas: horasTrabalhadas, horasEsperadas: horasEsperadas, horasNoturnas: 0, diaUtil: true)
		BigDecimal salario = service.getSalarioDia(valorHora, registroDiario)

		then:
		salario == salarioEsperado

		where:
		valorHora | horasTrabalhadas | horasEsperadas | salarioEsperado
		10        | 8                | 8              | 80
		10        | 8                | 4              | 100
		10        | 4                | 8              | 40
		10        | 0                | 8              | 0
		10        | 8                | 0              | 120
		5.50      | 8                | 8              | 44

	}

	@Unroll
	void "teste valor salário em domingos e feriados (dias nao uteis) com bonus de hora dobrada"(){
		when:
		RegistroDiario registroDiario = new RegistroDiario(horasTrabalhadas: horasTrabalhadas, horasEsperadas: 0, horasNoturnas: 0, diaUtil: false)
		BigDecimal salario = service.getSalarioDia(valorHora, registroDiario)

		then:
		salario == salarioEsperado

		where:
		valorHora | horasTrabalhadas | salarioEsperado
		10        | 0                | 0
		10        | 8                | 160
		0         | 4                | 0
		44.78     | 8                | 716.48
		0         | 8                | 0
		0         | 0                | 0
	}

	@Unroll
	void "teste valor salário em dias uteis com bonus de hora extra comum e adicional noturno"(){
		when:
		RegistroDiario registroDiario = new RegistroDiario(horasTrabalhadas: horasTrabalhadas, horasEsperadas: horasEsperadas, horasNoturnas: horasNoturnas, diaUtil: true)
		BigDecimal salario = service.getSalarioDia(valorHora, registroDiario)

		then:
		salario == salarioEsperado

		where:
		valorHora | horasTrabalhadas | horasEsperadas | horasNoturnas | salarioEsperado
		10        | 8                | 8              | 4             | 88
		10        | 10               | 8              | 2             | 116
		10        | 8                | 8              | 8             | 96
		10        | 8                | 0              | 8             | 144
		10        | 8                | 8              | 0             | 80
		10        | 0                | 8              | 0             | 0
	}


	@Unroll
    void "teste data de inicio do relatorio mensal"() {
        given:
        Date dataParaMes = new Date(2017, mes, 1)
        FechamentoMes.findOrSaveByDataFechamento(dia)

        expect:
        service.getDataInicioRelatorioMensal(dataParaMes) == resultado

        where:
        mes |  dia | resultado
         5  |  25  |   new Date(2017, 4, 26)
		 10 |  10  |   new Date(2017, 9, 11)
    }

	@Unroll
	void "teste data final do relatorio mensal"() {
		given:
		Date dataParaMes = new Date(2017, mes, 1)
		FechamentoMes.findOrSaveByDataFechamento(dia)

		expect:
		service.getDataFimRelatorioMensal(dataParaMes) == resultado

		where:
		mes |  dia | resultado
		5   |  25  |   new Date(2017, 5, 25)
		10  |  10  |   new Date(2017, 10, 10)
	}
}
