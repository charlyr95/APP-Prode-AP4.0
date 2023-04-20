import modules.*;
import java.util.ArrayList;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		// INSTANCIAR LOS EQUIPOS
		ArrayList<Equipo> equipos = new ArrayList<>();
		Scanner contenido = Archivos.leer("./src/references/resultados.csv");
		contenido.nextLine(); // Omito el encabezado
		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] data = linea.split(";");
			if (verificarEquipo(data[1], equipos)){
				agregarEquipo(data[1], equipos);
			}
			if (verificarEquipo(data[3], equipos)){
				agregarEquipo(data[3], equipos);
			}
		}

		System.out.println(equipos.size());

		// INSTANCIAR LOS PARTIDOS
		ArrayList<Partido> partidos = new ArrayList<>();
		contenido = Archivos.leer("./src/references/resultados.csv");
		contenido.nextLine(); // Omito el encabezado
		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] data = linea.split(";");
			Equipo equipo1 = buscarEquipo(data[1],equipos);
			Equipo equipo2 = buscarEquipo(data[3],equipos);
			int golesEquipo1 = Integer.parseInt(data[2]);
			int golesEquipo2 = Integer.parseInt(data[4]);
			Partido p = new Partido(equipo1, equipo2, golesEquipo1, golesEquipo2);
			partidos.add(p);
		}

		// INSTANCIAR LOS PARTICIPANTES
		ArrayList<Participante> pronosticos = new ArrayList<Pronostico>();
		contenido = Archivos.leer("./src/references/pronostico.csv");
		contenido.nextLine(); // Omito el encabezado

		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] data = linea.split(";");
			Equipo equipo1 = buscarEquipo(data[1],equipos);
			Equipo equipo2 = buscarEquipo(data[2],equipos);
			Partido partido = null;

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

















		// INSTANCIAR LOS PRONOSTICOS
		ArrayList<Pronostico> pronosticos = new ArrayList<Pronostico>();
		contenido = Archivos.leer("./src/references/pronostico.csv");
		contenido.nextLine(); // Omito el encabezado

		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] data = linea.split(";");
			Equipo equipo1 = buscarEquipo(data[1],equipos);
			Equipo equipo2 = buscarEquipo(data[2],equipos);
			Partido partido = null;

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

		// CALCULAR PUNTAJE
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

	public static boolean verificarEquipo(String nombre, ArrayList equipos){
		Equipo verify;
		for (int i = 0; i<equipos.size(); i++){
			verify = (Equipo)equipos.get(i);
			if (verify.getNombre().toLowerCase().equals(nombre.toLowerCase())){
				return false;
			}
		}
		return true;
	}

	public static void agregarEquipo(String nombre, ArrayList equipos){
		Equipo equipo = new Equipo(nombre.toLowerCase(), "");
		equipos.add(equipo);
	}

	public static Equipo buscarEquipo(String nombre, ArrayList equipos){
		Equipo equipo;
		for (int i = 0; i<equipos.size(); i++){
			equipo = (Equipo)equipos.get(i);
			if (equipo.getNombre().toLowerCase().equals(nombre.toLowerCase())){
				return equipo;
			}
		}
		return null;
	}

	public static Partido buscarPartido(Equipo equipo1, Equipo equipo2, ArrayList partidos) {
		for (int i = 0; i < partidos.size(); i++) {
			Partido p = (Partido) partidos.get(i);
			if (p.getEquipo1().getNombre().equals(equipo1.getNombre()) &&
					p.getEquipo2().getNombre().equals(equipo2.getNombre())) {
				return p;
			}
		}
		return null;
	}
}
