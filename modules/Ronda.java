package modules;
import java.util.ArrayList;

public class Ronda {
    private String numero;
    private ArrayList<Partido> partidos;

    public Ronda(String numero, ArrayList<Partido> partidos) {
        this.numero = numero;
        this.partidos = partidos;
    }

    public int puntos(ArrayList<Pronostico> pronosticos) {
        int puntos = 0;
        for (int i = 0; i < partidos.size(); i++) {
            Partido partido = partidos.get(i);
            Pronostico pronostico = pronosticos.get(i);
            if (pronostico.getResultado() == partido.resultado(pronostico.getEquipo())) {
                puntos++;
            }
        }
        return puntos;
    }
}
