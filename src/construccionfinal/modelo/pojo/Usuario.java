package construccionfinal.modelo.pojo;


public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String correo;
    private String telefono;
    private String rol;
    private String identificador;
    private String contrasenia;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apePaterno, String apeMaterno, String correo, String telefono, String rol, String identificador, String contrasenia) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.identificador = identificador;
        this.contrasenia = contrasenia;
    }

    
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    @Override
    public String toString() {
        return nombre + " " + apePaterno + " " + apeMaterno;
    }
}
