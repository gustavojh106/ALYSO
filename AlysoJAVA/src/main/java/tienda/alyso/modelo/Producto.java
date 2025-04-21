package tienda.alyso.modelo;

public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;  // nuevo campo


    public Producto(int idProducto, String nombre, double precio, String descripcion,int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    // Nuevo getter genérico
    public int getId() {
        return idProducto;
    }

    // Getters y Setters existentes
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
