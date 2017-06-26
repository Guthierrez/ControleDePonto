package controledeponto.model

import controledeponto.Ponto
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime

/**
 * Created by guthierrezsouza on 16/06/17.
 */
class RegistroDiario {
	LocalDate dia
	Boolean diaUtil
	List<LocalDateTime> pontoRegistrados
	BigDecimal horasEsperadas
	BigDecimal horasTrabalhadas
	BigDecimal horasNoturnas
}
