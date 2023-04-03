package modules;

public class Partido {
	private Equipo equipo1;
	private Equipo equipo2;
	private int goles1;
	private int goles2;

	public Partido(Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2){
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.goles1 = golesEquipo1;
		this.goles2 = golesEquipo2;
	}

	public void resultado(Equipo equipo) {
		if (equipo == this.equipo1) {
			if (this.goles1 > this.goles2) {
				System.out.print(equipo.getNombre() + " ganador");
			} else if (this.goles1 == this.goles2) {
				System.out.print("empate");
			} else {
				System.out.print(equipo.getNombre() + " perdedor");
			}
		}
		if (equipo == this.equipo2) {
			if (this.goles1 < this.goles2) {
				System.out.print(equipo.getNombre() + " ganador");
			} else if (this.goles1 == this.goles2) {
				System.out.print("empate");
			} else {
				System.out.print(equipo.getNombre() + " perdedor");
			}
		}
	}
	public void participantes() {
		System.out.println(this.equipo1.getNombre() + " vs " + this.equipo2.getNombre());
	}
}
