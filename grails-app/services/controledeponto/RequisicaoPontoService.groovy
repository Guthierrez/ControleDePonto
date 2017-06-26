package controledeponto

import grails.transaction.Transactional

@Transactional
class RequisicaoPontoService {

    def pontoService

    def processarRequisicao(RequisicaoPonto requisicaoPonto, Boolean aprovar) {
		if(requisicaoPonto.statusRequisicao == StatusRequisicao.PENDENTE){
			if(aprovar){
				requisicaoPonto.setStatusRequisicao(StatusRequisicao.APROVADO)
				pontoService.registrarPonto(requisicaoPonto.getHora(), requisicaoPonto.getFuncionario())
			}else{
				requisicaoPonto.setStatusRequisicao(StatusRequisicao.REPROVADO)
			}
		}
		return requisicaoPonto.save(failOnError: true	)
    }
}
