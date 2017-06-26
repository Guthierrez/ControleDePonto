package controledeponto

/**
 * Created by guthierrezsouza on 20/06/17.
 */
class Feriado {
	Date dia
	String descricao

	static constraints = {
		dia nullable: false
		descricao blank: false, nullable: false
	}


}
