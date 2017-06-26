package controledeponto

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(SaldoHorasTagLib)
class SaldoHorasTagLibSpec extends Specification {

    @Unroll
    void "teste conversão de horas em valor decimal para minutos e segundos"() {
        when:
        String resultado = tagLib.bigDecimalToHoras([quantidadeHoras: quantidade])

        then:
        resultado == esperado

        where:
        quantidade | esperado
        8          | "8hrs"
		4.5        | "4hrs 30min"
		4.51       | "4hrs 30min"
		0          | "0hrs"
		0.75       | "0hrs 45min"
		0.99       | "0hrs 59min"
		0.90       | "0hrs 54min"
    }

	@Unroll
	void "teste saldo horas em intervalo decimal para minutos e segundos"() {
		when:
		String resultado = tagLib.bigDecimalIntervalToHoras([horasTrabalhadas: trabalhadas, horasEsperadas: esperadas])

		then:
		resultado == resultadoEsperado

		where:
		esperadas  |trabalhadas    | resultadoEsperado
		8          |4              | "-4hrs"
		4.5        |9              | "4hrs 30min"
		0          |9              | "9hrs"
		9          |0              | "-9hrs"
		0          |0              | "0hrs"
		4.51       |4.75           | "0hrs 14min"
	}
}
