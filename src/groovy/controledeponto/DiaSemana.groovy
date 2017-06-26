package controledeponto

/**
 * Created by guthierrezsouza on 07/06/17.
 */
enum DiaSemana {
	SEGUNDA(1, "Segunda-Feira"),
	TERCA(2, "Terça-Feira"),
	QUARTA(3, "Quarta-Feira"),
	QUINTA(4, "Quinta-Feira"),
	SEXTA(5, "Sexta-Feira"),
	SABADO(6, "Sábado"),
	DOMINGO(7, "Domingo")

	Integer valor
	String descricao

	DiaSemana(Integer valor, String descricao){
		this.valor = valor
		this.descricao = descricao
	}

	static DiaSemana getByValor (Integer valor ) {
		this.values().find({ it.valor == valor })
	}

	@Override
	String toString() {
		return this.descricao;
	}
}