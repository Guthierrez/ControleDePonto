package controledeponto



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FechamentoMesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FechamentoMes.list(params), model:[fechamentoMesInstanceCount: FechamentoMes.count()]
    }

    def show(FechamentoMes fechamentoMesInstance) {
        respond fechamentoMesInstance
    }

    def create() {
        respond new FechamentoMes(params)
    }

    @Transactional
    def save(FechamentoMes fechamentoMesInstance) {
        if (fechamentoMesInstance == null) {
            notFound()
            return
        }

        if (fechamentoMesInstance.hasErrors()) {
            respond fechamentoMesInstance.errors, view:'create'
            return
        }

        fechamentoMesInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fechamentoMes.label', default: 'FechamentoMes'), fechamentoMesInstance.id])
                redirect fechamentoMesInstance
            }
            '*' { respond fechamentoMesInstance, [status: CREATED] }
        }
    }

    def edit(FechamentoMes fechamentoMesInstance) {
        respond fechamentoMesInstance
    }

    @Transactional
    def update(FechamentoMes fechamentoMesInstance) {
        if (fechamentoMesInstance == null) {
            notFound()
            return
        }

        if (fechamentoMesInstance.hasErrors()) {
            respond fechamentoMesInstance.errors, view:'edit'
            return
        }

        fechamentoMesInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'FechamentoMes.label', default: 'FechamentoMes'), fechamentoMesInstance.id])
                redirect fechamentoMesInstance
            }
            '*'{ respond fechamentoMesInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(FechamentoMes fechamentoMesInstance) {

        if (fechamentoMesInstance == null) {
            notFound()
            return
        }

        fechamentoMesInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'FechamentoMes.label', default: 'FechamentoMes'), fechamentoMesInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fechamentoMes.label', default: 'FechamentoMes'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
