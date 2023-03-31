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
public class Controlador implements ActionListener{

    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista vista = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();
    //--------------------------------------------------------------------------
    public Controlador(Vista v){
        this.vista=v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnOk.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        
    }
    //--------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnListar){
             refrescarTabla();
             listar(vista.tabla);
             limpiar();
        }
        if(e.getSource()==vista.btnGuardar){
            agregar();
            refrescarTabla();
            listar(vista.tabla);
            limpiar();
        }
        if(e.getSource()==vista.btnEditar){
            int fila=vista.tabla.getSelectedRow();
            if(fila==-1){
                JOptionPane.showMessageDialog(vista, "Debe primero seleccionar un usuario!");
            }else{
                int id=Integer.parseInt((String)vista.tabla.getValueAt(fila, 0).toString());
                String nombres=(String)vista.tabla.getValueAt(fila, 1);
                String documento=(String)vista.tabla.getValueAt(fila, 2);
                String rol=(String)vista.tabla.getValueAt(fila, 3);
                vista.txtId.setText(""+id);
                vista.txtNombres.setText(nombres);
                vista.txtDocumento.setText(documento);
                vista.txtRol.setText(rol);                 
            }
        }
        if(e.getSource()==vista.btnOk){
             actualizar();
             refrescarTabla();
             listar(vista.tabla);
             limpiar();
         }
         if(e.getSource()==vista.btnEliminar){
             eliminar();
             refrescarTabla();
             listar(vista.tabla);
             limpiar();
         }
         if(e.getSource()==vista.btnLimpiar){
            limpiar();
        }
    }
   //----------------------------------------------------------------------------------------------- agregar
    public void agregar(){
        String nombre=vista.txtNombres.getText();
        String documento=vista.txtDocumento.getText();
        String rol=vista.txtRol.getText();
        p.setNombre(nombre);
        p.setDocumento(documento);
        p.setRol(rol);
        int r=dao.agregar(p);
        if(r==1){
            JOptionPane.showMessageDialog(vista, "Se agregó el usuario "+p.getNombre()+"");
        }else{
            JOptionPane.showMessageDialog(vista, "Error al agregar el usuario "+p.getNombre()+"");
        }
    }
    //------------------------------------------------------------------------------------------ actualizar
    public void actualizar(){
        int id=Integer.parseInt(vista.txtId.getText());
        String nombre=vista.txtNombres.getText();
        String documento=vista.txtDocumento.getText();
        String rol=vista.txtRol.getText();
        p.setId(id);
        p.setNombre(nombre);
        p.setDocumento(documento);
        p.setRol(rol);
        
        int resultado=dao.actualizar(p);
        if(resultado==1){
            JOptionPane.showMessageDialog(vista, "Se actualizó el usuario "+p.getNombre()+" de forma correcta!");
        }else{
             JOptionPane.showMessageDialog(vista, "Error al agregar el usuario "+p.getNombre()+"");
        }  
    }
    //------------------------------------------------------------------------------------------ refrescar tabla
    void refrescarTabla(){
        for (int i = 0; i < vista.tabla.getRowCount(); i++){
            modelo.removeRow(i);
            i=i-1;
        }
    }
    //--------------------------------------------------------------------------------------------------- eliminar
    void eliminar(){
             int fila = vista.tabla.getSelectedRow();
             if(fila==-1){
                JOptionPane.showMessageDialog(vista, "Debe primero seleccionar un usuario!"); 
             }
             else{
                 int id=Integer.parseInt((String)vista.tabla.getValueAt(fila, 0).toString());
                 dao.eliminar(id);
                 JOptionPane.showMessageDialog(vista, "Se eliminó el usurio usuario!");
             } 
    }
    //--------------------------------------------------------------------------------------------------  listar
    public void listar(JTable tabla){
        modelo=(DefaultTableModel)tabla.getModel();
        List<Persona>lista=dao.listar();
        Object[]object = new Object[4];
        for(int i=0;i<lista.size();i++){
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getNombre();
            object[2]=lista.get(i).getDocumento();
            object[3]=lista.get(i).getRol();
            modelo.addRow(object); 
        }
        vista.tabla.setModel(modelo);
    }
    //------------------------------------------------------------------------------------------------- limpiar
    public void limpiar(){
        vista.txtId.setText("");
        vista.txtNombres.setText("");
        vista.txtDocumento.setText("");
        vista.txtRol.setText("");
    }
    //---------------------------------------------------------------------------------------------------------
}
