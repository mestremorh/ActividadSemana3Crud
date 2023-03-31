package modelo;
/**
 * @author Grupo 8 NRC: 50392
 * DAO: Objeto de Acceso a Datos
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    Conexion conectar=new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
     List<Persona>datos=new ArrayList<>();
     String sql="select * from persona";
     try{
         con=conectar.getConnection();
         ps=con.prepareStatement(sql); //hndyhdoiqwioedqbahsdlsa
         rs=ps.executeQuery();
         while(rs.next()){
             Persona p = new Persona();
             p.setId(rs.getInt(1));
             p.setNombre(rs.getString(3)); 
             p.setDocumento(rs.getString(2));
             p.setRol(rs.getString(4));
             datos.add(p);
         }
     }catch(Exception e){
     }
     return datos;
    }
    
    public int agregar(Persona p){
        String sql="insert into persona(nombres,documento,rol) values(?,?,?)";
        try{
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDocumento());
            ps.setString(3, p.getRol());
            ps.executeUpdate();
        }catch(Exception e){
        }
        return 1;
    }
    public int actualizar(Persona p){
       int respuesta = 0; 
       String sql="update persona set nombres = ?, documento = ?, rol = ? where id = ?";
       try{
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDocumento());
            ps.setString(3, p.getRol());
            ps.setInt(4, p.getId());
            respuesta = ps.executeUpdate();
            if(respuesta==1){
                return 1;
            }else{
                return 0;
            }
       }catch(Exception e){
       }
       return respuesta;
    }
    public void eliminar(int id){
        String sql = "delete from  persona where id ="+id;
        try{
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();          
        }catch(Exception e){
        }
    }
}
