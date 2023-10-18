
package com.model;

/**
 *
 * @author mont_
 */
public class VentaProducto {
    private int idVenta;
    private String idProducto;
    private int cantidadVendida;

    public VentaProducto(int idVenta, String idProducto, int cantidadVendida) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidadVendida = cantidadVendida;
    }

    public VentaProducto() {
    }

    public int getIdVenta() {
        return idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
    
    
}
