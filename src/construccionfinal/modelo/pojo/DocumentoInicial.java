package construccionfinal.modelo.pojo;

import java.sql.Date;

public class DocumentoInicial {

    private int idDocumentoInicial;
    private String nombre;
    private String estado;
    private String tipoDocumento;
    private Date fecha;
    private byte[] archivo;
    private int idExpediente;

    public DocumentoInicial() {
    }

    public DocumentoInicial(int idDocumentoInicial, String nombre, String estado, String tipoDocumento, Date fecha, byte[] archivo, int idExpediente) {
        this.idDocumentoInicial = idDocumentoInicial;
        this.nombre = nombre;
        this.estado = estado;
        this.tipoDocumento = tipoDocumento;
        this.fecha = fecha;
        this.archivo = archivo;
        this.idExpediente = idExpediente;
    }

    public int getIdDocumentoInicial() {
        return idDocumentoInicial;
    }

    public void setIdDocumentoInicial(int idDocumentoInicial) {
        this.idDocumentoInicial = idDocumentoInicial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
}
