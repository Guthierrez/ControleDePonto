package controledeponto

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by guthierrezsouza on 20/06/17.
 */
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RequisicaoPontoService)
@Mock([Ponto, RequisicaoPonto, Funcionario])
class RequisicaoPontoServiceSpec extends Specification{

	void "teste da aprovação de uma requisicão de ponto passado"(){

		given: "Funcionário e requisição de ponto cadastradas"
		Funcionario funcionario = Funcionario.findOrSaveByCpfAndNome("123456789", "Guthierrez")
		RequisicaoPonto requisicaoPonto = RequisicaoPonto.findOrSaveByFuncionarioAndHoraAndStatusRequisicaoAndJustificativa(funcionario, new Date(), StatusRequisicao.PENDENTE, "Esqueci de bater!")

		and: "Mock para pontoService, retornando ponto gerado para requisição aprovada"
		def pontoService = Stub(PontoService){
			registrarPonto(_, _) >> Ponto.findOrSaveByFuncionarioAndHorario(requisicaoPonto.getFuncionario(), requisicaoPonto.getHora())
		}
		service.pontoService = pontoService

		when: "Aprovar requisição de ponto"
		requisicaoPonto = service.processarRequisicao(requisicaoPonto, true)

		then:
		requisicaoPonto != null
		requisicaoPonto.statusRequisicao == StatusRequisicao.APROVADO
		Ponto.findAllByFuncionario(funcionario).size() == 1
	}

	void "teste da reprovação de uma requisicão de ponto passado"(){

		given: "Funcionário e requisição de ponto cadastradas"
		Funcionario funcionario = Funcionario.findOrSaveByCpfAndNome("123456789", "Guthierrez")
		RequisicaoPonto requisicaoPonto = RequisicaoPonto.findOrSaveByFuncionarioAndHoraAndStatusRequisicaoAndJustificativa(funcionario, new Date(), StatusRequisicao.PENDENTE, "Esqueci de bater!")

		and: "Mock para pontoService"
		def pontoService = Mock(PontoService)
		service.pontoService = pontoService

		when: "Reprovar requisição de ponto"
		requisicaoPonto = service.processarRequisicao(requisicaoPonto, false)

		then: "Requisição de ponto salva com status reprovado e nenhum ponto adicional registrado para o funcionário"
		requisicaoPonto != null
		requisicaoPonto.statusRequisicao == StatusRequisicao.REPROVADO
		Ponto.findAllByFuncionario(funcionario).size() == 0
		0 * pontoService.registrarPonto(_,_)
	}

	void "teste da modificação de uma requisicão de ponto já aprovada ou reprovada"(){

		given: "Funcionário e requisição de ponto cadastradas"
		Funcionario funcionario = Funcionario.findOrSaveByCpfAndNome("123456789", "Guthierrez")
		RequisicaoPonto requisicaoPonto = RequisicaoPonto.findOrSaveByFuncionarioAndHoraAndStatusRequisicaoAndJustificativa(funcionario, new Date(), StatusRequisicao.APROVADO, "Esqueci de bater!")

		and: "Mock para pontoService"
		def pontoService = Mock(PontoService)
		service.pontoService = pontoService

		when: "Tentando reprovar requisição de ponto já aprovada"
		requisicaoPonto = service.processarRequisicao(requisicaoPonto, false)

		then: "Requisição de ponto continua com status anterior e nenhum ponto adicional registrado para o funcionário"
		requisicaoPonto != null
		requisicaoPonto.statusRequisicao == StatusRequisicao.APROVADO
		Ponto.findAllByFuncionario(funcionario).size() == 0
		0 * pontoService.registrarPonto(_,_)
	}
}
