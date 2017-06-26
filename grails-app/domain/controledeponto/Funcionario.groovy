package controledeponto

class Funcionario extends Usuario{

    String nome
	String cpf
    Integer cargaHorariaMensal
    BigDecimal salario
    //static hasMany = [pontos : Ponto]

	static constraints = {
		nome blank: false, nullable: false
		cpf blank: false, nullable: false
	}

    @Override
    String toString() {
        return nome
    }

	boolean equals(o) {
		if (this.is(o)) return true
		if (getClass() != o.class) return false

		Funcionario that = (Funcionario) o

		if (cpf != that.cpf) return false

		return true
	}

	int hashCode() {
		return cpf.hashCode()
	}
}
