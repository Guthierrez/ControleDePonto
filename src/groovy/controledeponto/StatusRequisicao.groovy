package controledeponto

/**
 * Created by guthierrezsouza on 14/06/17.
 */
enum StatusRequisicao {
	APROVADO("Aprovado"), REPROVADO("Recusado"), PENDENTE("Pendente")

	String descricao

	StatusRequisicao(String descricao){
		this.descricao = descricao
	}

	@Override
	String toString() {
		return this.descricao
	}
}