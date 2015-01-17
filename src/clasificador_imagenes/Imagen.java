/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificador_imagenes;

import java.util.Date;

/**
 * Clase que representa una imagen ubucada en el equipo
 * y que contiene como atributos el nombre, la ruta y la
 * fecha de creacion de la imagen
 * @author Luis Astudillo
 */
public class Imagen implements Comparable{

    private String nombre;
    private String ruta;
    private Date fechacreacion;


    /**
     * Crea una nueva Imagen
     * @param nombre El nombre de la imagen
     * @param ruta La ruta donde se encuentra el archivo de imagen
     * @param fechacreacion La fecha en que se creo el archivo
     * de imagen
     */
    public Imagen(String nombre, String ruta, Date fechacreacion) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.fechacreacion = fechacreacion;
    }

    public String getFecha(){
        return(String.valueOf(this.fechacreacion.getDate()) + "/" +String.valueOf(this.fechacreacion.getMonth()+ 1) + "/" + String.valueOf(this.fechacreacion.getYear() + 1900)
               + " " +String.valueOf(this.fechacreacion.getHours() + ":" +String.valueOf(this.fechacreacion.getMinutes()))  );
    }

    



    public boolean equals(Object otherObject){
        if (otherObject == null)
            return false;
        else if(this.getClass() != otherObject.getClass() )
            return false;
        else{
                Imagen imagen = (Imagen)otherObject;
                return(this.fechacreacion.equals(imagen.fechacreacion));
        }

    }

    public int compareTo(Object obj){
         Imagen imagen = (Imagen)obj;
        return (fechacreacion.compareTo(imagen.fechacreacion));

    }

    /**
     * Retorna la fecha de creacion del archivo representado
     * por esta imagen
     * @return La fecha de creacion del archivo de imagen
     */
    public Date getFechacreacion() {
        return fechacreacion;
    }

    /**
     * Asigna una fecha a la Imagen
     * @param fechacreacion Fecha a asignar
     */
    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    /**
     * Retorna El nombre del archivo representado
     * por esta imagen
     * @return El nombre del archivo de imagen
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna una nombre a la Imagen
     * @param nombre Nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la ruta en que se encuentra el
     * archivo representado por esta imagen
     * @return La ruta del archivo de imagen
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Asigna una ruta a la Imagen
     * @param ruta La ruta a asignar
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String toString(){
        return this.nombre;
    }








}
