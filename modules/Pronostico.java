package modules;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultado;

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }

    public int puntos() {
        if (this.resultado == this.partido.resultado(this.equipo)) {
            return 1;
        } else if (this.resultado == ResultadoEnum.EMPATE && this.partido.resultado(this.equipo) == ResultadoEnum.EMPATE) {
            return 1;
        } else {
            return 0;
        }
    }

    public Partido getPartido() {
        return partido;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public ResultadoEnum getResultado() {
        return resultado;
    }
}
