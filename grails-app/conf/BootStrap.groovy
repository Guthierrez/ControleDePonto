import controledeponto.CargaHoraria
import controledeponto.DiaSemana
import controledeponto.FechamentoMes
import controledeponto.Funcionario
import controledeponto.Papel
import controledeponto.Ponto
import controledeponto.Usuario
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

class BootStrap {

    def init = { servletContext ->
		def papelFuncionario = Papel.findOrSaveByAuthorityAndDescricao('ROLE_FUNCIONARIO', "Funcionário")
		def papelAdmin = Papel.findOrSaveByAuthorityAndDescricao('ROLE_ADMINISTRADOR', "Administrador")
		def papelMaquina = Papel.findOrSaveByAuthorityAndDescricao('ROLE_MAQUINA', "Máquina")

		if (!Usuario.findByUsername('admin')) {
			Usuario admin = new Usuario(username: 'admin', password: 'admin').save()

			admin.addToPapeis(papelAdmin)
			admin.save()
		}

		if (!Funcionario.findByUsername('func')) {
			Funcionario funcionario = new Funcionario(username: 'func', password: 'func', cargaHorariaMensal: 200, salario: 2000, nome: "Guthierrez", cpf: "04923523131").save()

			funcionario.addToPapeis(papelFuncionario)

			funcionario.save()

			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 26, 8, 0, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 26, 16, 0, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 27, 3, 30, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 27, 23, 45, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 28, 8, 0, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 28, 18, 0, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 29, 8, 0, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 29, 14, 0, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 30, 8, 15, 0).toDate())
			Ponto.findOrSaveByFuncionarioAndHorario(funcionario, new LocalDateTime(2017, 6, 30, 16, 30, 0).toDate())
		}

		if (!Usuario.findByUsername('maquina')) {
			Usuario maquina = new Usuario(username: 'maquina', password: 'maquina').save()

			maquina.addToPapeis(papelMaquina)
			maquina.save()
		}

		if(!Usuario.findByUsername('fulano')){
			Funcionario funcionario = new Funcionario(username: 'fulano', password: 'fulano', cargaHorariaMensal: 200, salario: 2000, nome: "Fulano", cpf: "12345678910").save()
			funcionario.addToPapeis(papelFuncionario)

			funcionario.save()
		}

		FechamentoMes.findOrSaveByDataFechamento(25);

		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.SEGUNDA, true, 8)
		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.TERCA, true, 8)
		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.QUARTA, true, 8)
		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.QUINTA, true, 8)
		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.SEXTA, true, 8)
		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.SABADO, true, 4)
		CargaHoraria.findOrSaveByDiaSemanaAndDiaUtilAndQuantidadeHoras(DiaSemana.DOMINGO, false, 0)
    }

    def destroy = {
    }
}
