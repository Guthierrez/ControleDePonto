import controledeponto.CargaHoraria
import controledeponto.DiaSemana
import controledeponto.FechamentoMes
import controledeponto.Funcionario
import controledeponto.Papel
import controledeponto.Usuario

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
		}

		if (!Usuario.findByUsername('maquina')) {
			Usuario maquina = new Usuario(username: 'maquina', password: 'maquina').save()

			maquina.addToPapeis(papelMaquina)
			maquina.save()
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
