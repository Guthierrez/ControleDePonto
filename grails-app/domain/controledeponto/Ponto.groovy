package controledeponto

class Ponto {

    Long id
    Date horario
    static belongsTo = [funcionario : Funcionario]

    static constraints = {
    }

    @Override
    String toString() {
        return "Ponto Nº ${id} as ${horario}"
    }
}
