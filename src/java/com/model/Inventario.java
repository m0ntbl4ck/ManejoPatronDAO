
package com.model;

public class Inventario {
   private String idProducto;
   private int cantidadDisponible;

    public Inventario(String idProducto, int cantidadDisponible) {
        this.idProducto = idProducto;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Inventario() {
    }
   
   public void agregar(int cantidad){
       cantidadDisponible +=cantidad;
   }
   
   public boolean restar(int cantidad){
       if(cantidadDisponible - cantidad >= 0){
           cantidadDisponible -= cantidad;
           return true;
       }else{
           return false;
       }
   }
   
   
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public String getIdProducto() {
        return idProducto;
    }
   
   
}
