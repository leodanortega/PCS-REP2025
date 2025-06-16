package construccionfinal.modelo.pojo;

public class OrganizacionVinculada {

    private int idOrganizacion;
    private String nombre;
    private String correo;
    private String descripcion;
    private String RFC;
    private String telefono;
    private String tipo;

    public OrganizacionVinculada() {
    }

    public OrganizacionVinculada(int idOrganizacion, String nombre, String correo, String descripcion, String RFC, String telefono, String tipo) {
        this.idOrganizacion = idOrganizacion;
        this.nombre = nombre;
        this.correo = correo;
        this.descripcion = descripcion;
        this.RFC = RFC;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}
