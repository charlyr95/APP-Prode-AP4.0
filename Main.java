import modules.*;
import java.util.*;
import java.sql.*;


public class Main {

    public static void main(String[] args) {

        // LEER TABLA DE PARTIDOS E INSTANCIAR EQUIPOS Y PARTIDOS
        ResultSet rs = conectarDB( "dataset_mundial_2022", "root", "admin", "partidos");
        ArrayList<Equipo> equipos = new ArrayList<>();
        ArrayList<Partido> partidos = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("partido_id");
                String equipo1 = rs.getString("team1");
                String equipo2 = rs.getString("team2");
                String stage = rs.getString("stage");
                int goles1 = rs.getInt("goals1");
                int goles2 = rs.getInt("goals2");
                if (verificarEquipo(equipo1, equipos)) {
                    agregarEquipo(equipo1, equipos);
                }
                if (verificarEquipo(equipo2, equipos)) {
                    agregarEquipo(equipo2, equipos);
                }
                Equipo e1 = buscarEquipo(equipo1,equipos);
                Equipo e2 = buscarEquipo(equipo2,equipos);
                if (stage.equals("group")) {
                    Partido p = new Partido(e1, e2, goles1, goles2);
                    partidos.add(p);
                }
            }
            rs.close();
        } catch(Exception e) {
            System.out.println("Error al cargar base de datos.");
        };


        // INSTANCIAR LOS PARTICIPANTES
        ArrayList<Participante> participantes = new ArrayList<>();
        Scanner contenido = Archivos.leer("./src/references/pronostico.csv");
        contenido.nextLine(); // Omito el encabezado
        try {
            for (Scanner it = contenido; it.hasNext(); ) {
                String linea = it.nextLine();
                String[] data = linea.split(";");
                if (verificarParticipante(data[0], participantes)) {
                    agregarParticipante(data[0], participantes);
                }
            }
        } catch (Exception e){
            System.out.println("Ocurrió un error al cargar los participantes. Revisa los valores e inténtalo de nuevo.");
        }


        // INSTANCIAR LOS PRONOSTICOS
        ArrayList<Pronostico> pronosticos = new ArrayList<Pronostico>();
        contenido = Archivos.leer("./src/references/pronostico.csv");
        contenido.nextLine(); // Omito el encabezado
        try {
            for (Scanner it = contenido; it.hasNext(); ) {
                String linea = it.nextLine();
                String[] data = linea.split(";");
                Equipo equipo1 = buscarEquipo(data[1],equipos);
                Equipo equipo2 = buscarEquipo(data[2],equipos);
                Partido partido = buscarPartido(equipo1,equipo2,partidos);
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
                Participante participante = buscarParticipante(data[0],participantes);
                participante.agregarPronostico(pronostico);
            }
        } catch (Exception e){
            System.out.println("Ocurrió un error al cargar los pronósticos. Revisa los valores e inténtalo de nuevo.");
        }


        // CALCULAR PUNTAJE
        for(Participante p : participantes){
            System.out.println("El participante " + capitalize(p.getNombre()) + " tiene " + p.calcularTotal() + " puntos");
        }
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

    public static Partido buscarPartido(Equipo equipo1, Equipo equipo2, ArrayList<Partido> partidos) {
        for (Partido partido : partidos) {
            if (partido.getEquipo1().getNombre().equals(equipo1.getNombre()) &&
                    partido.getEquipo2().getNombre().equals(equipo2.getNombre())) {
                return partido;
            }
        }
        return null;
    }

    public static boolean verificarParticipante(String nombre, ArrayList<Participante> participantes){
        for (Participante p : participantes){
            if (p.getNombre().toLowerCase().equals(nombre.toLowerCase()))
                return false;
        }
        return true;
    }

    public static void agregarParticipante(String nombre, ArrayList<Participante> participantes){
        ArrayList<Pronostico> pronostico = new ArrayList<>();
        Participante p = new Participante(nombre.toLowerCase(), pronostico);
        participantes.add(p);
    }

    public static Participante buscarParticipante(String nombre, ArrayList<Participante> participantes){
        for (Participante participante : participantes){
            if (participante.getNombre().toLowerCase().equals(nombre.toLowerCase())){
                return participante;
            }
        }
        return null;
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static ResultSet conectarDB(String database, String user, String password, String table){
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, user, password);
            String consulta = "SELECT * FROM " + table;
            Statement stmt = conexion.createStatement();
            return stmt.executeQuery(consulta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
