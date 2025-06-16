package construccionfinal.modelo.pojo;

public class ResponsableProyecto {

    private int idResponsable;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String correo;
    private String telefono;
    private String puesto;
    private int idOrganizacion;

    public ResponsableProyecto() {
    }

    public ResponsableProyecto(int idResponsable, String nombre, String apePaterno, String apeMaterno,
                       String correo, String telefono, String puesto, int idOrganizacion) {
        this.idResponsable = idResponsable;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.puesto = puesto;
        this.idOrganizacion = idOrganizacion;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public String getApeMaterno() {
        return apeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    @Override
    public String toString() {
        return nombre + " " + apePaterno + " " + apeMaterno;
    }
}
