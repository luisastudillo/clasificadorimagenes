/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificador_imagenes;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.JLabel;

/**
 * Clase abstracta que dada una ruta y un tipo de archivo de
 * imagen, devuelve una lista con todas las imagenes de
 * ese tipo ubicadas en todas las subcarpetas de dicha ruta,
 * usando procedimientos recursivos.
 * Esta clase hace uso de la libreria open source metadata-extractor-2.3.1
 * @author Luis Astudillo
 */
public class Clasificador {
    
    /**
     * Dados una ruta y una extension, devuelve una lista con todos
     * los archivos de esa extension que se encuentre en dicha ruta
     * y en sus subcarpetas
     * @param ruta La ruta donde se hara la busqueda
     * @param extension El tipo de archivos a buscar
     * @param buscandoEn Label que indica el estado de la busqueda
     * @param buscando Label donde se indicara la carpeta de busqueda actual
     * @return Lista ordenada que contiene los años en los que se
     * encontraron imagenes, los que a su vez contienen Meses donde
     * se ubicaran las Imagenes que se encontraron
     */
    public static ArrayList<Anio> ListaOrdenadaUnidad(String ruta, String extension, JLabel buscandoEn, JLabel buscando){
        ArrayList <Anio> listaOrdenada = new ArrayList<Anio>();
        getListaUnidad(ruta, listaOrdenada, extension, buscandoEn, buscando);

        if(buscandoEn.getText().equals("Detenido")) return listaOrdenada;

        Collections.sort(listaOrdenada);
        for(Anio a : listaOrdenada){
            System.out.println("****** El año es :  " + String.valueOf(a.getAnio()));
            Collections.sort(a.getMeses());
            for(Mes m: a.getMeses()){
                System.out.println("       El mes es :  " + String.valueOf(m.getMes() + 1));
                if(buscandoEn.getText().equals("Detenido")) return listaOrdenada;
                Collections.sort(m.getImagenes());
                for(Imagen i : m.getImagenes()){
                    System.out.println("          Nombre: " + i.getNombre() + "  Fecha : " +  i.getFechacreacion().toString() + "  donde: " + i.getRuta());

                }
            }
        }
        return listaOrdenada;
    }

    /**
     * Funcion recursiva que dado una ruta y una extension, llena una lista
     * con todas las imagenes que se encuentren en dicha ruta y en sus
     * subcarpetas.
     * La fecha de las imagenes las obtiene por medio de tres metodos,
     * dependiendo de las condiciones de la ejecucion
     * @param path La ruta donde se hará la busqueda
     * @param listaOrdenada Lista que se llenará con las imagenes
     * @param extension Extension por la que se buscara las imagenes
     * @param buscando En Label que indica el estado de la busqueda
     * @param buscando Label donde se indicara la carpeta de busqueda actual
     */
    public static void getListaUnidad(String path, ArrayList<Anio> listaOrdenada, String extension, JLabel buscandoEn, JLabel buscando){
        File ruta = new File(path);
        File tmp;
        Date fecha;      
        Imagen imagen;
        int i = 0;
        String[] archivos = ruta.list();

        System.out.println(path);
        buscando.setText(path);

        if(buscandoEn.getText().equals("Detenido")) return;
        
        if(archivos == null) return;

        for(i=0; i<archivos.length; i++){
            if(buscandoEn.getText().equals("Detenido")){
                System.err.println("Se detuvo");
                return;
            }
            tmp = new File(path + "/" + archivos[i]);
            if(tmp.isDirectory())
                getListaUnidad((path + "/" + archivos[i]), listaOrdenada, extension, buscandoEn, buscando);

            else{


                if(isValido(archivos[i], extension)){

                    //Busca la fecha de modificacion por medio de los metodos de la clase File
                    fecha = fechaModificacion(tmp.lastModified());


                    // Asigna la fecha de creacion en caso de que el programa se ejecute en Windows XP
//                    fechaCreacionWindowsXP(tmp, fecha);
                   

                    // Asigna la fecha en que la imagen fue tomada en caso de que se trate de una imagen JPG
                    if(extension.equalsIgnoreCase("jpg"))                       
                        fechaCreacionJPG(tmp, fecha);
//                    try {
//                        BufferedImage img = ImageIO.read(tmp);
//                        System.out.println(img.getHeight());
//                    } catch (IOException ex) {
//                        Logger.getLogger(Clasificador.class.getName()).log(Level.SEVERE, null, ex);
//                    }

                    imagen = new Imagen(archivos[i], tmp.toString(), fecha);

                    ubicarImagen(imagen, listaOrdenada);
                    
                }
            }
        }
    }


    /**
     * Dado el nombre de un archivo y una extension, comprueba si se
     * trata de un archivo valido
     * @param name El nombre del archivo
     * @param extension Extension valida
     * @return Si es valido o no
     */
    public static boolean isValido(String name, String extension){
        if (name.regionMatches(true, name.length() - 3, extension, 0, 3))
            return true;
        return false;
    }


    
    /**
     * Recibe un String que contiene la fecha en el formato que se encuentra en los
     * archivos JPG y retorna un Date con dicha fecha
     * @param fechaString Fecha en formato que se encuentra en los archivos JPG
     * @return Date coteniendo la fecha pasada como parametro
     */
    public static Date convertirADate(String fechaString){
        
        Calendar fecha = new GregorianCalendar(Integer.parseInt(fechaString.substring(0, 4)), Integer.parseInt(fechaString.substring(5, 7))-1, Integer.parseInt(fechaString.substring(8, 10)));

        return fecha.getTime();

    }


    /**
     * Recibe la fecha de modificacion de un archivo en una variable Long
     * y returna un Date con dicha fecha
     * @param milis La Fecha en milisegundos transcurridps desde 1970
     * @return La fecha dada, en formato Date
     */
    public static Date fechaModificacion(Long milis){
        Date tmp = new Date();
        tmp.setTime(milis);
//        System.out.println(tmp);
        return tmp;

    }

    /**
     * Asigna la fecha en que la imagen fue tomada en caso de que se trate de una imagen JPG
     * Trabaja haciendo uso de la libreria open source metadata-extractor-2.3.1
     * @param imagen La ruta donde se encuentra la imagen JPG
     * @param fecha Aqui se guardara la fecha en que la imagen fue tomada
     */
    public static void fechaCreacionJPG(File imagen, Date fecha){
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(imagen);
            Iterator directories = metadata.getDirectoryIterator();

            while (directories.hasNext()) {
                Directory directory = (Directory)directories.next();

                try {
                    if(directory.getDescription(ExifDirectory.TAG_DATETIME)!= null){
                        fecha = convertirADate(directory.getDescription(ExifDirectory.TAG_DATETIME));

                    }
                } catch (MetadataException ex) {
                    System.err.println("Falló directorio : " + imagen.toString());
                }

            }
            
        } catch (JpegProcessingException ex) {
            System.err.println("Falló metadata : " + imagen.toString());
        }
    }


    /**
     * Asigna la fecha de creacion del archivo dado, en caso de que el programa se ejecute en Windows XP
     * @param imagen La ruta en la que se encuentra el archivo
     * @param fecha Aqui se guardara la fecha en la que se creo el archivo
     */
    public static void fechaCreacionWindowsXP(File imagen, Date fecha){


        try
        {
            Process ls_proc = Runtime.getRuntime().exec("cmd.exe /c dir \"C:\\HelloWorldApp.java\" /tc");
            DataInputStream in = new DataInputStream(ls_proc.getInputStream());
            for (int i = 0; i < 5; i++ )
            {
                    in.readLine();
            }

            String stuff = in.readLine();
            StringTokenizer st = new StringTokenizer(stuff);
            String dateC = st.nextToken();//DATE CREATED
            in.close();
            
            fecha = new GregorianCalendar(Integer.parseInt(dateC.substring(6, 10)), Integer.parseInt(dateC.substring(3, 5))-1, Integer.parseInt(dateC.substring(0, 2))).getTime();

        }
        catch (IOException e1)
        {
                System.out.println("Error creando tiempo");
        }

    }


    /**
     * Dada una imagen y una lista de años, ubica esta imagen en el año y el
     * mes correspondientes, en caso de que no existan, los crea
     * @param im Imagen a ubicar
     * @param lista Lista que contiene los años
     */
    public static void ubicarImagen(Imagen im, ArrayList<Anio> lista){
        Anio anio = new Anio(0);
        Mes mes= new Mes(0);
        int ban = 0;

        for(Anio a : lista){
            if(a.getAnio() == (im.getFechacreacion().getYear() + 1900)){
                anio= a;
                ban = 1;
                break;
            }
        }

        if(ban == 0){
            anio = new Anio(im.getFechacreacion().getYear() + 1900);
            lista.add(anio);
        }
        ban = 0;

        for(Mes m : anio.getMeses()){
            if(m.getMes() == (im.getFechacreacion().getMonth())){
                mes = m;
                ban = 1;
                break;
            }
        }

        if(ban == 0){
            mes = new Mes(im.getFechacreacion().getMonth());
            anio.getMeses().add(mes);
        }

        mes.getImagenes().add(im);
    }


}
