package modelo;
/**
 * @author Grupo 8 NRC: 50392
 */
public class Persona {
    // lista de atributos, provados, publicos, protegidos -> modificadores de acceso
    private int id;
    private String nombre;
    private String documento;
    private String rol;
    
    public Persona() {
    }

    public Persona(int id, String nombre, String documento, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
    }
    
    // Sevicios publicos de la clase o la interfaz publica set -> Poner, get -> Obtener 
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getRol() {
        return rol;
    }

}
