import modules.*;
import java.util.ArrayList;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		ArrayList equipos = new ArrayList<>();
		ArrayList partidos = new ArrayList<>();

		// LEER ARCHIVO DE EQUIPOS
		Scanner contenido = Archivos.leer("./src/references/equipos.csv");

		// CARGO LOS EQUIPOS EN UN ARRAY
		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.next();
			Equipo e = new Equipo(linea);
			equipos.add(e);
		}

		// PARTIDOS
		contenido = Archivos.leer("./src/references/resultados.csv");
		contenido.nextLine(); // Omito el encabezado

		// CARGO LOS EQUIPOS EN UN ARRAY
		for (Scanner it = contenido; it.hasNext(); ) {
			String linea = it.nextLine();
			String[] array = linea.split(";");

			String ronda = array[0];
			String fecha = array[1];
			String hora = array[2];
			String equipo1 = array[3];
			String equipo2 = array[4];
			int goles1 = Integer.parseInt(array[5]);
			int goles2 = Integer.parseInt(array[6]);
			Equipo e1 = null;
			Equipo e2 = null;

			// Comparo las intancias de equipos con los string de equipos del archivo resultado.csv
			for (int i = 0; i < equipos.size(); i++){
				Equipo e = (Equipo) equipos.get(i);
				String compare = e.getNombre();
				if (compare.equals(equipo1)) e1 = e;
				if (compare.equals(equipo2)) e2 = e;
			};

			Partido p = new Partido(e1, e2, goles1, goles2);
			partidos.add(p);
		}

		Partido partido = (Partido)partidos.get(0);
		partido.participantes();
		partido.resultado((Equipo) equipos.get(14));

	}
}
