package modules;

import java.io.*;
import java.util.Scanner;

public class Archivos {
    public static Scanner leer(String ubicacion){
        try {
            File archivo = new File(ubicacion);
            Scanner contenido = new Scanner(archivo);
            return contenido;

        } catch (FileNotFoundException e) {
            System.out.println("No se puede leer el archivo.");
        }
        return null;
    }
}
