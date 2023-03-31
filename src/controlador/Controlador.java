package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Persona;
import modelo.PersonaDAO;
import vista.Vista;

/**
 * @author Grupo 8 NRC: 50392
 */
public class Controlador implements ActionListener {

    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista vista = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();

   /**
     * Este método es el contructor propio del Controlador, el cual recibe como parametro una
     * instancia de la vista. Esto para adicionar los listener y quedar a la espera de eventos que 
     * sucedan en la vista producto de la interaccion del usuario
     * @param v Recibe como parametro una instancia de la Vista 
     * @return Integer 
    */
    public Controlador(Vista v) {
        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnOk.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

    }

     /**
     * Este método se encarga de escuchar y capturar todos los eventos desencadenados
     * en los botones de la vista que le dan funcionalidad a la aplicacion 
     * @param 
     * @return void
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnListar) {
            refrescarTabla();
            listar(vista.tabla);
            limpiar();
        }
        //Se ejecuta en caso de que el usuario oprima el boton Guardar en la vista
        if (e.getSource() == vista.btnGuardar) {
            agregar();
            refrescarTabla();
            listar(vista.tabla);
            limpiar();
        }
        //Se ejecuta en caso de que el usuario oprima el boton Editar en la vista
        if (e.getSource() == vista.btnEditar) {
            int fila = vista.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe primero seleccionar un usuario!");
            } else {
                int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
                String nombres = (String) vista.tabla.getValueAt(fila, 1);
                String documento = (String) vista.tabla.getValueAt(fila, 2);
                String rol = (String) vista.tabla.getValueAt(fila, 3);
                vista.txtId.setText("" + id);
                vista.txtNombres.setText(nombres);
                vista.txtDocumento.setText(documento);
                vista.txtRol.setText(rol);
            }
        }
        // Se ejecuta en caso de que el usaurio decida modificar el registro oprimiendo el Botón OK
        if (e.getSource() == vista.btnOk) {
            actualizar();
            refrescarTabla();
            listar(vista.tabla);
            limpiar();
        }
        // Se ejecuta en caso de que el usaurio decida eliminar un registro oprimiendo el Botón Eliminar
        if (e.getSource() == vista.btnEliminar) {
            eliminar();
            refrescarTabla();
            listar(vista.tabla);
            limpiar();
        }
        // Se ejecuta en caso de que el usaurio decida resetear los campos de texto en pantalla
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }

  
        /**
     * Este método se encarga de capturar los campos Nombre, Documento y rol de la vista
     * cuando un evento es desencadenado desde el Jbutton btnGuardar
     * Para enviarlos al DAO y de esta manera agregar un nueva Persona a la Base de datos
     * @param 
     * @return void
    */
    public void agregar() {
        String nombre = vista.txtNombres.getText();
        String documento = vista.txtDocumento.getText();
        String rol = vista.txtRol.getText();
        p.setNombre(nombre);
        p.setDocumento(documento);
        p.setRol(rol);
        int r = dao.agregar(p);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Se agregó el usuario " + p.getNombre() + "");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar el usuario " + p.getNombre() + "");
        }
    }

       /**
     * Este método se encarga de capturar los campos Nombre, Documento y rol de la vista
     * cuando un evento es desencadenado desde el Jbutton btnOk
     * Para enviarlos al DAO y de esta Modificar la informacion de una Persona en la Base de datos
     * @param
     * @return void 
    */
    public void actualizar() {
        int id = Integer.parseInt(vista.txtId.getText());
        String nombre = vista.txtNombres.getText();
        String documento = vista.txtDocumento.getText();
        String rol = vista.txtRol.getText();
        p.setId(id);
        p.setNombre(nombre);
        p.setDocumento(documento);
        p.setRol(rol);

        int resultado = dao.actualizar(p);
        if (resultado == 1) {
            JOptionPane.showMessageDialog(vista, "Se actualizó el usuario " + p.getNombre() + " de forma correcta!");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar el usuario " + p.getNombre() + "");
        }
    }

     /**
     * Este método se encarga de actualizar los campos de la tabla, eliminando los registros de 
     *  consultas anteriores de manera que la tabla solo se cargue con la informacion requerida
     * @param
     * @return void 
    */
    void refrescarTabla() {
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

     /**
    * Este método se encarga de enviar al DAO el id del registro persona seleccionado 
    * y de esa manera eliminar el registro en la base de datos
    * @param
    * @return void 
   */
    void eliminar() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe primero seleccionar un usuario!");
        } else {
            int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
            dao.eliminar(id);
            JOptionPane.showMessageDialog(vista, "Se eliminó el usurio usuario!");
        }
    }
    
      /**
     * Este metodo se encarga de hacer la peticiòn al DAO de todos los registros
     * de Personas que haya en Base de Datos y luego los actualiza en la vista.
     * @param 
     * @return void
    */
    public void listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        List<Persona> lista = dao.listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getDocumento();
            object[3] = lista.get(i).getRol();
            modelo.addRow(object);
        }
        vista.tabla.setModel(modelo);
    }

      /**
     * Este método se encarga de limpiar los campos de textos en caso de que 
     * se requiera.
     * @param 
     * @return void
    */
    public void limpiar() {
        vista.txtId.setText("");
        vista.txtNombres.setText("");
        vista.txtDocumento.setText("");
        vista.txtRol.setText("");
    }
    // ---------------------------------------------------------------------------------------------------------
}
