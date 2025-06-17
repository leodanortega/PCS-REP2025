package construccionfinal.modelo.pojo;

import construccionfinal.modelo.pojo.OrganizacionVinculada;
import construccionfinal.modelo.pojo.ResponsableProyecto;

public class Proyecto {

    //Relacion con ORGANIZACION VINCULADA
    private int idOrganizacion;
    private OrganizacionVinculada organizacionVinculada;
    //Relacion con RESPONSABLE DE PROYECTO
    private int idResponsable;
    private ResponsableProyecto responsableProyecto;
    
    //Datos de PROYECTO
    private int idProyecto;
    private String nombre;
    private String departamento;
    private String descripcion;
    private String metodologia;
    private String espacios;
    
    private int idEstudiante;

    public Proyecto() {
    }

    public Proyecto(int idOrganizacion, int idResponsable, int idProyecto, String nombre, String departamento, String descripcion, String metodologia, String espacios, int idEstudiante) {
        this.idOrganizacion = idOrganizacion;
        this.idResponsable = idResponsable;
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.metodologia = metodologia;
        this.espacios = espacios;
        this.idEstudiante = idEstudiante;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public OrganizacionVinculada getOrganizacionVinculada() {
        return organizacionVinculada;
    }

    public void setOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) {
        this.organizacionVinculada = organizacionVinculada;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public ResponsableProyecto getResponsableProyecto() {
        return responsableProyecto;
    }

    public void setResponsableProyecto(ResponsableProyecto responsableProyecto) {
        this.responsableProyecto = responsableProyecto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getEspacios() {
        return espacios;
    }

    public void setEspacios(String espacios) {
        this.espacios = espacios;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
