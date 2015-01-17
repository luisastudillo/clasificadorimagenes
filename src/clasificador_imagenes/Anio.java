/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificador_imagenes;

import java.util.ArrayList;

/**
 * Clase que representa un año y que contiene todos los
 * meses dentro de eseaño donde se encuentren imagenes
 * @author Luis Astudillo
 */
public class Anio implements Comparable{
    private ArrayList<Mes> meses;
    private int anio;


    /**
     * Constructor de la clase Anio que recibe como parametro el
     * año que representa
     * @param anio Año que va a ser representado por este objeto
     */
    public Anio(int anio) {
        this.meses = new ArrayList<Mes>();
        this.anio = anio;
    }

    
    
    /**
     * Retorna el año representado por este objeto
     * @return El año representado por este objeto
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Asigna el año representado por este objeto
     * @param anio El año a asignar
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Retorna la lista de meses que contiene este año
     * @return La lista de meses que contiene este año
     */
    public ArrayList<Mes> getMeses() {
        return meses;
    }

    /**
     * Asigna lista de meses que contiene este año
     * @param meses Lista de meses que va a contener este año
     */
    public void setMeses(ArrayList<Mes> meses) {
        this.meses = meses;
    }

    public int compareTo(Object otherObject){
        Anio otroAnio = (Anio)otherObject;
        if(this.anio < otroAnio.anio)
            return -1;
        if(this.anio > otroAnio.anio)
            return 1;
        return 0;
    }

    public String toString(){
        return String.valueOf(anio);
    }



}
