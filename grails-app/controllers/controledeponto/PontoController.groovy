package controledeponto

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PontoController {

    def pontoService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",
                             registrarPontoPorCPF: "POST"]

    def index(){
    }

    @Transactional
    @Secured(['ROLE_MAQUINA'])
    def registrarPontoPorCPF(String cpf){
        Funcionario funcionario = Funcionario.findByCpf(cpf)
        Date horario = new Date()
        if(funcionario){
            try{
                Ponto ponto = pontoService.criarPontoParaFuncionario(horario, funcionario)
                render(status: 201, text: "Ponto registrado para ${ponto.funcionario.nome} ás ${ponto.horario.format('HH:mm')}")
            }catch (Exception e){
                render(status: 400, text: e.getMessage())
            }
        }else{
            render(status: 400, text: 'Funcionário não encontrado!')
        }
    }

    @Transactional
    @Secured(['ROLE_FUNCIONARIO'])
    def registrarPontoFuncionarioLogado(){
        String cpf = springSecurityService.getCurrentUser().cpf
        Funcionario funcionario = Funcionario.findByCpf(cpf)
        Date horario = new Date()
        if(funcionario){
            Ponto ponto = pontoService.registrarPonto(horario, funcionario)
            flash.message = "Ponto registrado para ${ponto.funcionario.nome} registrado as ${horario.format("HH:mm")}!"
        }else{
            flash.error = "Problemas ao encontrar funcionário para usuário logado!"
        }
        redirect(controller: 'ponto', view: 'index')
    }

    protected void notFound() {
        render status: NOT_FOUND
    }
}
