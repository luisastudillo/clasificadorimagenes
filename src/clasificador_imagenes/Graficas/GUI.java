/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificador_imagenes.Graficas;

import clasificador_imagenes.Anio;
import clasificador_imagenes.Clasificador;
import clasificador_imagenes.Imagen;
import clasificador_imagenes.Mes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Ventana Principal del Sistema
 * @author Luis Astudillo
 */
public class GUI  extends JFrame{

    /**
     * Autoasignacion del JFrame para uso de los metodos
     */
    public GUI gui = this;

    /**
     * Lista donde se agregaran los años que devuelva la busqueda de imagenes
     */
    public ArrayList<Anio> anios;
    
    /**
     * Arbol compuesto por los anños y los meses donde se ubiquen la imagenes
     */
    public JTree arbol;

    /**
     * Imagen que se pintara en la vista previa
     */
    public ImageIcon imagen;

    /**
     * Label para la unidad de disco
     */
    public JLabel unidad;
    /**
     * Label para los tipos de archivos
     */
    public JLabel tipo;

    /**
     * Label para el nombre de la vista previa
     */
    public JLabel vistaPrevia;


    /**
     * Label para la ubicacion del archivo de imagen
     */
    public JLabel ubicacion;

    /**
     * Label para indicar el estado de la busqueda
     */
    public JLabel buscando;

    /**
     * Label para la carpeta actual de busqueda
     */
    public JLabel buscandoEn;

    /**
     * Boton para iniciar la busqueda
     */
    public JButton escogerUnidad;

    /**
     * Indica que el tipo de archivo seleccionado es jpg
     */
    public JRadioButton jpg;

    /**
     * Indica que el tipo de archivo seleccionado es png
     */
    public JRadioButton png;

    /**
     * Indica que el tipo de archivo seleccionado es gif
     */
    public JRadioButton gif;

    ButtonGroup grupo;

    /**
     *
     */
    public JScrollPane panelLista;

    /**
     *
     */
    public PanelImagen panelImagen;

    /**
     * Tabla donde se ubicara en nombre de las imagenes y su fecha de creacion
     */
    public JTable tabla;

    /**
     * Tabla logica de las imagenes
     */
    public MyTableModel modeloTabla;

    /**
     *
     */
    public JScrollPane panelScroll;

    /**
     * comboBox con las unidades de disco disponibles en ese equipo
     */
    public JComboBox combo;
    String[] unidades = {"A","C","D","E","F","G","H","I","J","K","L"};

    /**
     * Instaciacion de innerclass
     */
    public CreadorArbol creadorArbol;

    /**
     * indica si ya se ha pintado algun arbol
     */
    public boolean arbolIniciado = false;

    /**
     * indica si ya se ha pintado alguna tabla
     */
    public boolean tablaIniciada = false;

    /**
     *COnstructor de la ventana principal
     */
    public GUI(){
        super("Clasificador de Imagenes");

        initComponents();
        

        this.setVisible(true);

    }


    /**
     * Aqui se inicializan todos los componentes del JFrame
     */
    public void initComponents(){
        this.setSize(900, 610);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        imagen = new ImageIcon(getClass().getResource("/clasificador_imagenes/Graficas/fondo.jpg"));

        escogerUnidad = new JButton("Buscar");
        escogerUnidad.setBounds(105, 25, 90, 20);
        escogerUnidad.addActionListener(new BuscarImagenes());

        combo = new JComboBox();
        llenarUnidades();
        combo.setBounds(0, 25, 100, 20);

        unidad = new JLabel("Seleccione la unidad:");
        unidad.setHorizontalTextPosition(JLabel.CENTER);
        unidad.setFont(new Font("Algerian", Font.PLAIN, 14));
        unidad.setBounds(10, 5, 180, 20);

        tipo = new JLabel("Escoja el tipo de imagen:");
        tipo.setHorizontalTextPosition(JLabel.CENTER);
        tipo.setFont(new Font("Algerian", Font.PLAIN, 13));
        tipo.setBounds(10, 50, 180, 20);

        jpg = new JRadioButton("jpg");
        jpg.setBounds(10, 70, 50, 20);
        jpg.setSelected(true);

        png = new JRadioButton("png");
        png.setBounds(70, 70, 50, 20);

        gif = new JRadioButton("gif");
        gif.setBounds(130, 70, 50, 20);

        vistaPrevia = new JLabel();
        vistaPrevia.setBounds(130, 20, 290, 30);
        vistaPrevia.setHorizontalTextPosition(JLabel.CENTER);
        vistaPrevia.setFont(new Font("Algerian", Font.PLAIN, 16));
        vistaPrevia.setText("Vista Previa");

        buscando = new JLabel("Carpeta de Búsqueda");
        buscando.setBounds(70, 560, 550, 20);

        buscandoEn = new JLabel();
        buscandoEn.setBounds(0, 560, 70, 20);

        ubicacion = new JLabel();
        ubicacion.setBounds(560, 420, 350, 20);


        panelImagen = new PanelImagen();
        panelImagen.setBounds(550, 0, 350, 560);
        panelImagen.setBackground(Color.LIGHT_GRAY);


        panelImagen.add(vistaPrevia);
        panelImagen.setLayout(null);
        panelImagen.add(ubicacion, null);

        grupo = new ButtonGroup();
        grupo.add(jpg);
        grupo.add(png);
        grupo.add(gif);
        

        this.setLayout(null);
        this.add(combo, null);
        this.add(unidad, null);
        this.add(escogerUnidad);
        this.add(tipo);
        this.add(jpg);
        this.add(png);
        this.add(gif);
        this.add(ubicacion);
        this.add(buscando);
        this.add(buscandoEn);
        this.add(panelImagen, null);
        

    }


    /**
     * Busca todas las unidades de disco instaladas en el equipo y
	 * las agraga al comboBox
     */
    public void llenarUnidades(){
        File ruta;
        combo.removeAllItems();
        for(String s : unidades){
//            ruta = new File("/media/Cosas/");
            ruta = new File(s +":\\");
            if(ruta.isDirectory()){
                combo.addItem(ruta.toString());
                System.out.println(ruta);
            }
        }
    }


    

    /**
     * Crea un JTable a partir del mes pasado por parametro, con los nombres y la fecha de creacion 
	 * de los archivos de imagen, y lo agraga al JFrame
     * @param mes El mes que contiene las imagenes que van a ser mostradas en la tabla
     */
    public void crearTabla(Mes mes){

        modeloTabla = new MyTableModel(mes);
        tabla = new JTable(modeloTabla);
        
        tabla.setPreferredScrollableViewportSize(new Dimension(300, 600));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel listSeleccion = tabla.getSelectionModel();

        listSeleccion.addListSelectionListener(new ListSelectionListener(){

            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();

                imagen = new ImageIcon( ((Imagen)modeloTabla.getValueAt(lsm.getMinSelectionIndex(), 0)).getRuta() );

                ubicacion.setText("Ubicacion: " + ((Imagen)modeloTabla.getValueAt(lsm.getMinSelectionIndex(), 0)).getRuta());
                ubicacion.setToolTipText(ubicacion.getText());
                ubicacion.repaint();
                panelImagen.repaint();
                

           }

        });


        if(panelLista != null){
            this.remove(panelLista);
        }
        panelLista = new JScrollPane(tabla);
        panelLista.setBounds(200, 0, 350, 560);
        this.add(panelLista);
    }

    

    /**
     * Inner class que implementa la interfaz TreeSelectionListener
     */
    public class CambioJtree implements TreeSelectionListener{
        public void valueChanged(TreeSelectionEvent evt){
            DefaultMutableTreeNode tmp = (DefaultMutableTreeNode)evt.getPath().getLastPathComponent();

            if(tmp.getUserObject().getClass() == Mes.class){
                System.out.println(tmp.getUserObject().getClass());
                crearTabla((Mes)tmp.getUserObject());
                tablaIniciada = true;
            }
        }
    }



    /**
     * Manejador de eventos del boton buscar.
     * Busca las imagenes de acuerdo a los parametros dados por el usuario y manda a crear el JTree
     * Si se estan buscando imagenes, este boton le permie detener la busqueda
     */
    public class BuscarImagenes implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(escogerUnidad.getText().equals("Buscar")){

                String unidad = ((String)combo.getSelectedItem());
                String tipo;

                if(tablaIniciada)
                    gui.remove(panelLista);

                if(arbolIniciado)
                    gui.remove(panelScroll);

                imagen = new ImageIcon(getClass().getResource("/clasificador_imagenes/Graficas/fondo.jpg"));

                ubicacion.setText("");

                ubicacion.repaint();
                
                gui.paintAll(gui.getGraphics());


                escogerUnidad.setText("Detener");
                buscandoEn.setText("Buscando:");
                
                if(jpg.isSelected()) tipo = "jpg";
                else if(png.isSelected()) tipo = "png";
                else tipo = "gif";

                creadorArbol = new CreadorArbol(unidad, tipo);
                creadorArbol.start();

            } else{
                buscandoEn.setText("Detenido");
                escogerUnidad.setText("Buscar");
            }
        }

    }


    /**
     * Clase que extiende de Thread y crea el arbol de años en otro proceso
     */
    public class CreadorArbol extends Thread{
        String unidad;
        String tipo;

        /**
         * COnstructor de la clase
         * @param unidad Unidad de disco donde se hará la busqueda
         * @param tipo tipo de archivo a buscar
         */
        public CreadorArbol(String unidad, String tipo){
            this.unidad = unidad;
            this.tipo = tipo;

        }

        public void run(){

            anios = Clasificador.ListaOrdenadaUnidad(unidad, tipo, buscandoEn, buscando);
            
//            if(buscandoEn.getText().equals("Buscando:")){
                int anio = 0;
                DefaultMutableTreeNode tmpAnio;
                DefaultMutableTreeNode tmpMes;
                DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Años");
                DefaultTreeModel modelo = new DefaultTreeModel(padre);
                for(Anio a : anios){
                    tmpAnio = new DefaultMutableTreeNode(a);
                    padre.add(tmpAnio);
                    for(Mes m : a.getMeses()){
                        tmpMes = new DefaultMutableTreeNode(m);
                        tmpAnio.add(tmpMes);
                    }
                    anio++;
                }

                arbol = new JTree(modelo);
                arbol.setAlignmentX(arbol.LEFT_ALIGNMENT);
                arbol.setAlignmentY(arbol.TOP_ALIGNMENT);

                arbol.addTreeSelectionListener(new CambioJtree());

                
                panelScroll = new JScrollPane(arbol);
                panelScroll.setBounds(0, 100, 200, 460);
                panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                gui.add(panelScroll, null);
                
                gui.paintAll(gui.getGraphics());
                escogerUnidad.setText("Buscar");
                arbolIniciado = true;
//            }

        }

    }



    /**
     *
     */
    public class MyTableModel extends AbstractTableModel {
        final String[] columnNames = {"Nombre",
                                "Fecha"
                                };
        Object[][] data;

        /**
         *Crea el moelo logico de la tabla a partir del Mes recibido
         * @param mes El mes a listar en la tabla
         */
        public MyTableModel(Mes mes){
            int pos = 0;
            data = new Object[mes.getImagenes().size()][mes.getImagenes().size() + 1];
            for(Imagen m : mes.getImagenes()){
                data[pos][0] = m;
                data[pos][1] = m.getFecha();
                pos++;
            }
        }

        //únicamente retornamos el numero de elementos del
        //array de los nombres de las columnas
        public int getColumnCount() {
            return columnNames.length;
        }

        //retormanos el numero de elementos
        //del array de datos
        public int getRowCount() {
            return data.length;
        }

        //retornamos el elemento indicado
        public String getColumnName(int col) {
            return columnNames[col];
        }

        //y lo mismo para las celdas
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * Este metodo sirve para determinar el editor predeterminado
         * para cada columna de celdas
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
    }


    /**
     * Panel donde se graficara la imagen seleccionada
     */
    public class PanelImagen extends JPanel{

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(imagen.getImage(), 0, 50, 350, 350, rootPane);            
        }

    }

}
