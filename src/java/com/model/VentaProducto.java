
package com.model;

/**
 *
 * @author mont_
 */
public class VentaProducto {
    private int idVenta;
    private String idProducto;
    private int cantidadVendida;

    public VentaProducto() {
    }

    public VentaProducto(int idVenta, String idProducto, int cantidad) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidadVendida = cantidad;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getcantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidad(int cantidad) {
        this.cantidadVendida = cantidad;
    }
    
    
    
}
