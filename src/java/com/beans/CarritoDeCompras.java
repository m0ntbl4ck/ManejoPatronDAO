
package com.beans;

import com.DAO.InventorioDAO;
import com.DAO.ProductoDAO;
import com.DAO.VentaDAO;
import com.DAO.VentaProductoDAO;
import com.model.Inventario;
import com.model.Producto;
import com.model.Venta;
import com.model.VentaProducto;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import utilidades.PurchaseException;

/**
 *
 * @author mont_
 */
@SessionScoped
public class CarritoDeCompras implements Serializable{
    private static final Logger logger = Logger.getLogger("com.beans.CarritoDeCompras");
    
    @Inject
    private ProductoDAO productoDAO;
    @Inject
    private InventorioDAO inventarioDAO;
    @Inject
    private VentaProductoDAO ventaProductoDAO;
    @Inject
    private VentaDAO ventaDAO;
    
    private Map<Producto, Integer> carrito;

    public CarritoDeCompras() {
        this.carrito = new HashMap<>();
    }
    
    public void agregarItem(String idProducto, int cantidad){
        try{
            Producto producto = productoDAO.obtenerProductoPorId(idProducto);
            
            if(carrito.containsKey(producto)){
                int cantidadActual=carrito.get(producto);
                cantidadActual += cantidad;
                carrito.put(producto, cantidadActual);
            }else{
                carrito.put(producto, cantidad);
            }
        }catch(SQLException ex){
            System.out.println("Bean Carrito: agregarITEM: "+ex);
        }
    }
    
    public void removerItem(Producto producto)
    {
        carrito.remove(producto);
    }
    
    
    public int obtenerConteodeItems(){
        int count =0;
        Set<Producto> productos = carrito.keySet();
        for(Producto p : productos){
            count += carrito.get(p);
        }
        return count;
    }
    
    
    public Map<Producto, Integer> obtenercarrito(){
        return carrito;
    }
    
    
    public Venta purchaseCarrito() throws PurchaseException{
        Venta ventas = null;
        try{
            ventas = ventaDAO.crearRegistroVentas();
            
            Set<Producto> productos = carrito.keySet();
            double costTotal = 0.0;
            
            for(Producto producto : productos){
                int cantidad = carrito.get(producto);
                Inventario inventario = inventarioDAO.obtenerInventario(producto.getIdProducto());
                
                if(!inventario.restar(cantidad)){
                    String error ="No se puede comprar: "+producto.getIdProducto()
                            +" : cantidad deseada "+ cantidad + " : Cantidad disponible: "
                            +inventario.getCantidadDisponible();
                    logger.log(Level.INFO,error);
                    throw new PurchaseException(error);
    
                }else{
                    costTotal += producto.getPrecio() *cantidad;
                    VentaProducto ventaProducto = new VentaProducto(ventas.getIdVenta(),producto.getIdProducto(),cantidad);
                    
                    ventaProductoDAO.agregarRegistroVentaProducto(ventaProducto);
                    inventarioDAO.actualizarInventario(producto.getIdProducto(), inventario.getCantidadDisponible());
                }
            }
            
            ventas.setFechaVenta(new Date());
            ventas.setTotalVenta(costTotal);
            ventaDAO.actualizarRegistroVentas(ventas);
        }catch(SQLException ex){
            logger.log(Level.INFO,"CarritoDeCompras: purchaseCarrito "+ ex);
                    
            throw new PurchaseException("SQLException en PurchaseCarrito "+ex);
        }finally{
            carrito = new HashMap<>();
        }
        return ventas;
    }
    
    public void reiniciarCarrito(){
        carrito = new HashMap<>();  
    }
}
