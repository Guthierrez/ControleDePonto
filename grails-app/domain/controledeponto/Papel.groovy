package controledeponto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
class Papel implements Serializable {

	private static final long serialVersionUID = 1

	String authority
	String descricao

	Papel(String authority, String descricao) {
		this.authority = authority
		this.descricao = descricao
	}

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}

	@Override
	String toString() {
		return this.getDescricao()
	}
}
