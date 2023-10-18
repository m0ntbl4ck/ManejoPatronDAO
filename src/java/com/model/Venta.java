
package com.model;

import java.util.Date;

/**
 *
 * @author mont_
 */
public class Venta {
    
    private int idVenta;
    private Date fechaVenta;
    private double totalVenta;

    public Venta(int idVenta, Date fechaVenta, double totalVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    
    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }


    public int getIdVenta() {
        return idVenta;
    }

}
