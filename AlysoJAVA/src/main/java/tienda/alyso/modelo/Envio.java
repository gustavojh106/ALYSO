// Envio.java (modelo)
package tienda.alyso.modelo;

public class Envio {
    private int idEnvio;
    private int idPedido;
    private String fechaEnvio;
    private String empresa;
    private String estadoEnvio;
    private String detalle;

    public Envio(int idEnvio, int idPedido, String fechaEnvio,
                 String empresa, String estadoEnvio, String detalle) {
        this.idEnvio = idEnvio;
        this.idPedido = idPedido;
        this.fechaEnvio = fechaEnvio;
        this.empresa = empresa;
        this.estadoEnvio = estadoEnvio;
        this.detalle = detalle;
    }

    public int getIdEnvio() { return idEnvio; }
    public void setIdEnvio(int idEnvio) { this.idEnvio = idEnvio; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public String getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(String fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getEstadoEnvio() { return estadoEnvio; }
    public void setEstadoEnvio(String estadoEnvio) { this.estadoEnvio = estadoEnvio; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
