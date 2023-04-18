import modules.*;
import java.util.ArrayList;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		ArrayList equipos = new ArrayList<>();


		// PARTIDOS
		Scanner contenido = Archivos.leer("./src/references/resultados.csv");
		contenido.nextLine(); // Omito el encabezado

		// INSTANCIAR LOS PARTIDOS
		ArrayList partidos = new ArrayList<>();
		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] data = linea.split(";");
			Equipo equipo1 = new Equipo(data[0], "");
			Equipo equipo2 = new Equipo(data[2], "");
			int golesEquipo1 = Integer.parseInt(data[1]);
			int golesEquipo2 = Integer.parseInt(data[3]);
			Partido p = new Partido(equipo1, equipo2, golesEquipo1, golesEquipo2);
			partidos.add(p);
		}

		// INSTANCIAR LOS PRONOSTICOS
		ArrayList<Pronostico> pronosticos = new ArrayList<Pronostico>();

		contenido = Archivos.leer("./src/references/pronostico.csv");
		contenido.nextLine(); // Omito el encabezado

		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] data = linea.split(";");
			Equipo equipo1 = new Equipo(data[0], "");
			Equipo equipo2 = new Equipo(data[1], "");
			Partido partido = null;

			for (int i = 0; i<partidos.size();i++){
				Partido p = (Partido) partidos.get(i);
				if (p.getEquipo1().getNombre().equals(equipo1.getNombre()) &&
					p.getEquipo2().getNombre().equals(equipo2.getNombre())) {
					partido = p;
					break;
				}
			}
			boolean ganaEquipo1 = Boolean.parseBoolean(data[2]);
			boolean ganaEquipo2 = Boolean.parseBoolean(data[3]);
			boolean empate = Boolean.parseBoolean(data[4]);
			ResultadoEnum resultado;
			if (ganaEquipo1) {
				resultado = ResultadoEnum.GANADOR;
			} else if (ganaEquipo2) {
				resultado = ResultadoEnum.PERDEDOR;
			} else {
				resultado = ResultadoEnum.EMPATE;
			}
			Pronostico pronostico = new Pronostico(partido, equipo1, resultado);
			pronosticos.add(pronostico);
		}

		// Calcular puntaje
		int puntajeTotal = 0;
		for (Pronostico p : pronosticos) {
			int puntaje = p.puntos();
			puntajeTotal += puntaje;
			System.out.println("Partido: " + p.getPartido().getEquipo1().getNombre() + " vs " + p.getPartido().getEquipo2().getNombre());
			System.out.println("Pronóstico: " + p.getEquipo().getNombre() + " " + p.getResultado());
			System.out.println("Resultado: " + p.getEquipo().getNombre() + " " + p.getPartido().resultado(p.getEquipo()));
			System.out.println("Puntaje: " + puntaje);
			System.out.println("");
		}
		System.out.println("Puntaje total: " + puntajeTotal);

	}
}
