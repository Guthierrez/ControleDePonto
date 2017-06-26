package controledeponto

class SaldoHorasTagLib {
    static namespace = "saldoHoras"
    static defaultEncodeAs = [taglib:'html']

    def bigDecimalToHoras = {attrs, body ->
        BigDecimal quantidadeHoras = attrs.quantidadeHoras as BigDecimal
		String result = "${quantidadeHoras.intValue()}hrs"
		BigDecimal minutos = quantidadeHoras.remainder(1).multiply(60).intValue()
		if(minutos != 0){
			result += " ${minutos.intValue()}min"
		}
		out << result
    }

	def bigDecimalIntervalToHoras = {attrs, body ->
		BigDecimal saldo = attrs.horasTrabalhadas - attrs.horasEsperadas
		out << bigDecimalToHoras([quantidadeHoras: saldo], body)
	}
}
