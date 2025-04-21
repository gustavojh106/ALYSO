package tienda.alyso.modelo;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private String fecha; // formato YYYY-MM-DD
    private double total;
    private String estado; // nuevo campo

    /** Constructor original (sin estado) */
    public Pedido(int idPedido, int idCliente, String fecha, double total) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
    }

    /** Nuevo constructor que incluye estado */
    public Pedido(int idPedido, int idCliente, String fecha, double total, String estado) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /** Getter para el nuevo campo estado */
    public String getEstado() {
        return estado;
    }

    /** Setter para el nuevo campo estado */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
