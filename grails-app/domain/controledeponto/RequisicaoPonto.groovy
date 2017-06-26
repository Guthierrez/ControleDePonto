package controledeponto

/**
 * Created by guthierrezsouza on 14/06/17.
 */
class RequisicaoPonto {
	Long id
	Date hora
	StatusRequisicao statusRequisicao = StatusRequisicao.PENDENTE
	String justificativa
	static belongsTo = [funcionario : Funcionario]

	static constraints = {
		hora nullable: false
		statusRequisicao nullable: false
		justificativa blank: false, nullable: false
	}
}
