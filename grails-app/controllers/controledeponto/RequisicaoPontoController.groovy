package controledeponto

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RequisicaoPontoController {
    def requisicaoPontoService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        if(springSecurityService.getCurrentUser().getAuthorities().any {it.authority == "ROLE_FUNCIONARIO"}){
            String cpf = springSecurityService.getCurrentUser()?.cpf
            Funcionario funcionario = Funcionario.findByCpf(cpf)
            respond RequisicaoPonto.findAllByFuncionario(funcionario)
        }else{
            params.max = Math.min(max ?: 10, 100)
            respond RequisicaoPonto.list(params), model:[requisicaoPontoInstanceCount: RequisicaoPonto.count()]
        }
    }

    def show(RequisicaoPonto requisicaoPontoInstance) {
        respond requisicaoPontoInstance
    }

    @Transactional
    def analisarSolicitacao(RequisicaoPonto requisicaoPonto, Boolean aprovar){
        requisicaoPonto = requisicaoPontoService.processarRequisicao(requisicaoPonto, aprovar)
        flash.message = "Salvo com sucesso!"
        render(view: 'show', model: [requisicaoPontoInstance: requisicaoPonto])
    }

    def create() {
        String cpf = springSecurityService.getCurrentUser().cpf
        Funcionario funcionario = Funcionario.findByCpf(cpf)
        respond new RequisicaoPonto(funcionario: funcionario, statusRequisicao: params.statusRequisicao, hora: params.hora, justificativa: params.justificativa, id: params.id)
    }

    @Transactional
    def save(RequisicaoPonto requisicaoPontoInstance) {
        if (requisicaoPontoInstance == null) {
            notFound()
            return
        }

        if (requisicaoPontoInstance.hasErrors()) {
            respond requisicaoPontoInstance.errors, view:'create'
            return
        }

        requisicaoPontoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'requisicaoPonto.label', default: 'RequisicaoPonto'), requisicaoPontoInstance.id])
                redirect requisicaoPontoInstance
            }
            '*' { respond requisicaoPontoInstance, [status: CREATED] }
        }
    }

    def edit(RequisicaoPonto requisicaoPontoInstance) {
        respond requisicaoPontoInstance
    }

    @Transactional
    def update(RequisicaoPonto requisicaoPontoInstance) {
        if (requisicaoPontoInstance == null) {
            notFound()
            return
        }

        if (requisicaoPontoInstance.hasErrors()) {
            respond requisicaoPontoInstance.errors, view:'edit'
            return
        }

        requisicaoPontoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'RequisicaoPonto.label', default: 'RequisicaoPonto'), requisicaoPontoInstance.id])
                redirect requisicaoPontoInstance
            }
            '*'{ respond requisicaoPontoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(RequisicaoPonto requisicaoPontoInstance) {

        if (requisicaoPontoInstance == null) {
            notFound()
            return
        }

        requisicaoPontoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'RequisicaoPonto.label', default: 'RequisicaoPonto'), requisicaoPontoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicaoPonto.label', default: 'RequisicaoPonto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
