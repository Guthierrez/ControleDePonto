package controledeponto

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PontoService)
@Mock([Ponto, Funcionario])
class PontoServiceSpec extends Specification {

    @Shared
    Funcionario funcionario

    void "teste cadastro ponto funcionario"() {

		given:
		funcionario = Funcionario.findOrSaveByCpfAndNome("123456789", "Guthierrez")

		when:
		Ponto ponto = service.criarPontoParaFuncionario(new Date(), funcionario)

		then:
		ponto != null

	}

	void "teste registro ponto menor que ultimo ponto cadastrado"() {

		given:
		funcionario = Funcionario.findOrSaveByCpfAndNome("123456789", "Guthierrez")
		Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new Date())

		when:
		service.criarPontoParaFuncionario(new Date() -1, funcionario)

		then:
		thrown(Exception)

	}

	void "teste busca de pontos por funcionario e periodo"(){
		given:
		funcionario = Funcionario.findOrSaveByCpfAndNome("123456789", "Guthierrez")
		Ponto.saveAll(new Ponto(funcionario: funcionario, horario:  new Date()))
		Date inicio = new Date() - 10
		Date fim = new Date() + 10

		when:
		Integer results = Ponto.findAllByFuncionarioAndHorarioBetween(funcionario, inicio, fim).size()

		then:
		results == 1
	}
}
