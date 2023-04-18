package modules;

public class Partido {
	private Equipo equipo1;
	private Equipo equipo2;
	private int golesEquipo1;
	private int golesEquipo2;

	public Partido(Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.golesEquipo1 = golesEquipo1;
		this.golesEquipo2 = golesEquipo2;
	}

	public ResultadoEnum resultado(Equipo equipo) {
		String str = equipo.getNombre();
		String str1 = this.equipo1.getNombre();
		String str2 = this.equipo2.getNombre();
		if (str.equals(str1)) {
			if (this.golesEquipo1 > this.golesEquipo2) {
				return ResultadoEnum.GANADOR;
			} else if (this.golesEquipo1 < this.golesEquipo2) {
				return ResultadoEnum.PERDEDOR;
			} else {
				return ResultadoEnum.EMPATE;
			}
		} else if (str.equals(str2)) {
			if (this.golesEquipo2 > this.golesEquipo1) {
				return ResultadoEnum.GANADOR;
			} else if (this.golesEquipo2 < this.golesEquipo1) {
				return ResultadoEnum.PERDEDOR;
			} else {
				return ResultadoEnum.EMPATE;
			}
		} else {
			return null;
		}
	}


	public Equipo getEquipo1() {
		return this.equipo1;
	}

	public Equipo getEquipo2() {
		return this.equipo2;
	}
}
