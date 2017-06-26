package controledeponto



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CargaHorariaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CargaHoraria.list(params), model:[cargaHorariaInstanceCount: CargaHoraria.count()]
    }

    def show(CargaHoraria cargaHorariaInstance) {
        respond cargaHorariaInstance
    }

    def create() {
        respond new CargaHoraria(params)
    }

    @Transactional
    def save(CargaHoraria cargaHorariaInstance) {
        if (cargaHorariaInstance == null) {
            notFound()
            return
        }

        if (cargaHorariaInstance.hasErrors()) {
            respond cargaHorariaInstance.errors, view:'create'
            return
        }

        cargaHorariaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cargaHoraria.label', default: 'CargaHoraria'), cargaHorariaInstance.id])
                redirect cargaHorariaInstance
            }
            '*' { respond cargaHorariaInstance, [status: CREATED] }
        }
    }

    def edit(CargaHoraria cargaHorariaInstance) {
        respond cargaHorariaInstance
    }

    @Transactional
    def update(CargaHoraria cargaHorariaInstance) {
        if (cargaHorariaInstance == null) {
            notFound()
            return
        }

        if (cargaHorariaInstance.hasErrors()) {
            respond cargaHorariaInstance.errors, view:'edit'
            return
        }

        cargaHorariaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CargaHoraria.label', default: 'CargaHoraria'), cargaHorariaInstance.id])
                redirect cargaHorariaInstance
            }
            '*'{ respond cargaHorariaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CargaHoraria cargaHorariaInstance) {

        if (cargaHorariaInstance == null) {
            notFound()
            return
        }

        cargaHorariaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CargaHoraria.label', default: 'CargaHoraria'), cargaHorariaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cargaHoraria.label', default: 'CargaHoraria'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
