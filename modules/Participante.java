package modules;

import java.util.ArrayList;

public class Participante {
    //private int ID; // Para usar a futuro
    private String nombre;
    private ArrayList pronosticos;

    public Participante(String nombre, ArrayList pronosticos) {
        //this.ID = ID;
        this.nombre = nombre;
        this.pronosticos = pronosticos;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList getPronosticos() {
        return pronosticos;
    }

    public int calcularTotal(){
        ArrayList<Pronostico> pronosticos = this.getPronosticos();
        int puntos = 0;
        for(Pronostico p : pronosticos){
            puntos += p.puntos();
        }
        return puntos;
    }
    
    public int cantidadPronosticos(){
        return this.getPronosticos().size();
    }
}
