/*

    Trabajo Práctico Integrador
    Curso Java - Argentina Programa 4.0

    Participantes:
    Cinthia Huertas
    Damian Tristan
    Claudio Aragonez
    Carlos Romero

 */

import modules.*;
import java.util.*;
import java.sql.*;
import java.io.*;


public class Main {

    public static void main(String[] args) {
        // LECTURA ARCHIVO CONFIG
        String configFile = "./src/config.ini"; // Ubicación del archivo INI
        Properties props = new Properties();
        try {
            // Cargo las propiedades del archivo ini en variables
            props.load(new FileReader(configFile));
            String dbName = props.getProperty("databaseName");
            String dbTabla1 = props.getProperty("tablaPartidos");
            String dbTabla2 = props.getProperty("tablaPronosticos");

            // LEER TABLA DE PARTIDOS E INSTANCIAR EQUIPOS Y PARTIDOS
            ResultSet rs = conectarDB( dbName, "root", "admin", dbTabla1);
            ArrayList<Equipo> equipos = new ArrayList<>();
            ArrayList<Partido> partidos = new ArrayList<>();
            try {
                while (rs.next()) {
                    // Lectura MySQL
                    int id = rs.getInt("partido_id");
                    String equipo1 = rs.getString("team1");
                    String equipo2 = rs.getString("team2");
                    String stage = rs.getString("stage");
                    int goles1 = rs.getInt("goals1");
                    int goles2 = rs.getInt("goals2");
                    // Instanciar equipos
                    if (verificarEquipo(equipo1, equipos)) {
                        agregarEquipo(equipo1, equipos);
                    }
                    if (verificarEquipo(equipo2, equipos)) {
                        agregarEquipo(equipo2, equipos);
                    }
                    // Instanciar partidos
                    Equipo e1 = buscarEquipo(equipo1,equipos);
                    Equipo e2 = buscarEquipo(equipo2,equipos);
                    if (stage.equals("group")) { // Solo leo los partidos de fase de grupos, las eliminatorias no van.
                        Partido p = new Partido(e1, e2, goles1, goles2);
                        partidos.add(p);
                    }
                }
                rs.close();
            } catch(Exception e) {
                System.out.println("Error al cargar base de datos.");
            };


            // LEER TABLA DE PRONOSTICOS E INSTANCIAR PARTICIPANTES Y PRONOSTICOS
            ResultSet rs1 = conectarDB( dbName, "root", "admin", dbTabla2);
            ArrayList<Participante> participantes = new ArrayList<>();
            ArrayList<Pronostico> pronosticos = new ArrayList<>();
            try {
                while (rs1.next()) {
                    // Lectura de MySQL
                    int id = rs1.getInt("pronostico_id");
                    String participante = rs1.getString("participante");
                    String equipo1 = rs1.getString("equipo1");
                    String equipo2 = rs1.getString("equipo2");
                    boolean ganaEquipo1 = rs1.getBoolean("gana1");
                    boolean ganaEquipo2 = rs1.getBoolean("gana2");
                    boolean empate = rs1.getBoolean("empate");
                    // Comparación de pronóstico
                    ResultadoEnum resultado;
                    if (ganaEquipo1) {
                        resultado = ResultadoEnum.GANADOR;
                    } else if (ganaEquipo2) {
                        resultado = ResultadoEnum.PERDEDOR;
                    } else {
                        resultado = ResultadoEnum.EMPATE;
                    };
                    // Instanciar participante
                    if (verificarParticipante(participante, participantes)) {
                        agregarParticipante(participante, participantes);
                    };
                    // Instanciar pronóstico
                    Equipo e1 = buscarEquipo(equipo1,equipos);
                    Equipo e2 = buscarEquipo(equipo2,equipos);
                    Partido partido = buscarPartido(e1,e2,partidos);
                    Pronostico pronostico = new Pronostico(partido, e1, resultado);
                    pronosticos.add(pronostico);
                    Participante p = buscarParticipante(participante,participantes);
                    p.agregarPronostico(pronostico);
                }
                rs1.close();
            } catch(Exception e) {
                System.out.println("Error al cargar base de datos.");
            };


            // CALCULAR PUNTAJE
            for(Participante p : participantes){
                System.out.println("El participante " + capitalize(p.getNombre()) + " tiene " + p.calcularTotal() + " puntos");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
