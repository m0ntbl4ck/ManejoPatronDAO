
package com.model;

import java.util.Objects;

/**
 *
 * @author mont_
 */

public class Producto {
    private String idProducto;
    private String nombreProducto;
    private double precio;
    private String descripcionProducto;

    public Producto(String idProducto, String nombreProducto, double precio, String descripcionProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.descripcionProducto = descripcionProducto;
    }

    public Producto() {
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    @Override
    public String toString() {
        String s = String.format("ID del producto:  %s\n"
        + "Nombre: %s\n"
        +"Descripción: %s\n"
        +"Precio: $%.2f",idProducto,nombreProducto, descripcionProducto,precio );
                
        return s;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.idProducto);
        hash = 59 * hash + Objects.hashCode(this.nombreProducto);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.precio) ^ (Double.doubleToLongBits(this.precio) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.descripcionProducto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (Double.doubleToLongBits(this.precio) != Double.doubleToLongBits(other.precio)) {
            return false;
        }
        if (!Objects.equals(this.idProducto, other.idProducto)) {
            return false;
        }
        if (!Objects.equals(this.nombreProducto, other.nombreProducto)) {
            return false;
        }
        return Objects.equals(this.descripcionProducto, other.descripcionProducto);
    }

    
    
    
    
    
}
