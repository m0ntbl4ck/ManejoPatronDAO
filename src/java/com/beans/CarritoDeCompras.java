
package com.beans;

import com.DAO.InventorioDAO;
import com.DAO.ProductoDAO;
import com.DAO.VentaDAO;
import com.DAO.VentaProductoDAO;
import com.model.Inventario;
import com.model.Producto;
import com.model.Venta;
import com.model.VentaProducto;
import com.servlet.EmpujarCarrito;
import com.utilidades.EmpujarException;
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
import javax.transaction.Transactional;

/**
 *
 * @author mont_
 */
@SessionScoped
public class CarritoDeCompras implements Serializable {
    
    private static final Logger logger = Logger.getLogger("com.beans.CarritoDeCompras");
    
    @Inject
    private ProductoDAO productoDAO;
    @Inject
    private InventorioDAO inventarioDAO;
    @Inject
    private VentaDAO ventaDAO;
    @Inject
    private VentaProductoDAO ventaproductoDAO;
    
   // @Resource
   // private UserTransaction ut;
    
    private Map<Producto, Integer> carrito;

    public CarritoDeCompras() {
        this.carrito = new HashMap<>();
    }
    
    public void agregarArticulo(String idProducto, int cantidad){
        try {
            Producto producto = productoDAO.obtenerProductoPorId(idProducto);
            
            if(carrito.containsKey(producto)){
                int cantidadActual = carrito.get(producto);
                cantidadActual += cantidad;
                carrito.put(producto, cantidadActual);
            }else{
                carrito.put(producto, cantidad);
            }
        } catch (SQLException e) {
            System.out.println("CaritoBean: agregarCarrito: "+ e);
        }
    }
    
    public void removerArticulo(Producto producto){
        carrito.remove(producto);
    }
    
    public int getConteoArticulos(){
        int count =0;
        Set<Producto> productos = carrito.keySet();
        for(Producto p : productos){
            count += carrito.get(p);
        }
        return count;
    }

    public Map<Producto, Integer> getCarrito() {
        return carrito;
    }
    
    
  @Transactional(value = Transactional.TxType.REQUIRED,
              rollbackOn = {EmpujarException.class})
    public Venta empujarCarrito()throws EmpujarException{
        Venta ventas = null;
        try{
            
         //   ut.begin();
            ventas = ventaDAO.crearRegistroVentas();
            
            Set<Producto> productos= carrito.keySet();
            double costoTotal = 0.0;
            
            for(Producto producto: productos){
                int cantidad = carrito.get(producto);
                Inventario inventario = inventarioDAO.obtenerInventario(producto.getIdProducto());
                if(!inventario.restar(cantidad)){
               //     ut.rollback();
                    String error = "No se puede comprar: "+producto.getIdProducto()
                            +" : cantidad deseada: "+ cantidad + " : Cantidad disponible: "
                            +inventario.getCantidadDisponible();
                    logger.log(Level.INFO,error);
                    throw new EmpujarException(error);
                }else{
                    costoTotal += producto.getPrecio()* cantidad;
                   VentaProducto ventasProducto = new VentaProducto(ventas.getIdVenta(),producto.getIdProducto(),cantidad);
                   ventaproductoDAO.agregarRegistroVentaProducto(ventasProducto);
                   inventarioDAO.actualizarInventario(producto.getIdProducto(), inventario.getCantidadDisponible());
                   
                }
            }
            ventas.setFechaVenta(new Date());
            ventas.setTotalVenta(costoTotal);
            ventaDAO.actualizarRegistroVentas(ventas);
         //   ut.commit();
        }catch(/*RollbackException | HeuristicMixedException |HeuristicRollbackException |
                NotSupportedException| SystemException|*/SQLException ex){
            logger.log(Level.INFO,"CarritoDeCompras : empujarCarrito "+ex);
            throw new EmpujarException ("SQLException al comprarCarrito: "+ex.getMessage());
        }finally{
            carrito = new HashMap<>();
        }
        return ventas;
    }
    
    public void resetCarrito()
    {
        carrito = new HashMap<>();
    }
}
