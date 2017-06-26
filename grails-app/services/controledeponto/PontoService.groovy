package controledeponto

import grails.transaction.Transactional

@Transactional
class PontoService {

    Ponto criarPontoParaFuncionario(Date hora, Funcionario funcionario) {
        Ponto ultimoPonto = findUltimoPontoByFuncionario(funcionario)
        if(ultimoPonto?.getHorario() > hora){
            throw new Exception("Erro ao cadastrar ponto!gitgit")
        }else{
            return registrarPonto(hora, funcionario)
        }
    }

    Ponto registrarPonto(Date hora, Funcionario funcionario){
        Ponto ponto = new Ponto(horario: hora, funcionario: funcionario)
		ponto = ponto.save()
        return ponto
    }

    Ponto findUltimoPontoByFuncionario(Funcionario funcionario){
        def criteria = Ponto.createCriteria()
        return criteria.get {
            eq("funcionario", funcionario)
            maxResults(1)
            order("horario", "desc")
        }
    }

    List<Ponto> findPontosByFuncionarioAndPeriodo(Funcionario funcionario, Date inicio, Date fim){
        fim += 1
		def criteria = Ponto.createCriteria()
		return criteria.list {
			eq("funcionario", funcionario)
			ge("horario", inicio)
            le("horario", fim)
			order("horario", "asc")
		}
    }
}
