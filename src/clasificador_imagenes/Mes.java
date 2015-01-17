/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificador_imagenes;

import java.util.ArrayList;

/**
 *
 * @author Luis Astudillo
 */
public class Mes implements Comparable{
    private int mes;

    private ArrayList <Imagen> imagenes;

    /**
     * Cosntructor del Mes con unico parametro el mes
     * @param mes El numero del mes
     */
    public Mes(int mes) {
        this.mes = mes;
        this.imagenes = new ArrayList<Imagen>();
    }
  
    /**
     *
     * @return Retorna el numero de este mes
     */
    public int getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     *
     * @return Retorna el arreglo de imagenes de este mes
     */
    public ArrayList<Imagen> getImagenes() {
        return imagenes;
    }

    /**
     *
     * @param imagenes
     */
    public void setImagenes(ArrayList<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public int compareTo(Object otherObject){
        Mes otroMes = (Mes)otherObject;
        if(this.mes < otroMes.mes)
            return -1;
        if(this.mes > otroMes.mes)
            return 1;
        return 0;
    }


    public String toString(){
        if(mes == 0) return "Enero";
        else if(mes == 1) return "Febrero";
        else if(mes == 2) return "Marzo";
        else if(mes == 3) return "Abril";
        else if(mes == 4) return "Mayo";
        else if(mes == 5) return "Junio";
        else if(mes == 6) return "Julio";
        else if(mes == 7) return "Agosto";
        else if(mes == 8) return "Septiembre";
        else if(mes == 9) return "Octubre";
        else if(mes == 10) return "Noviembre";
        return "Diciembre";
    }


}
