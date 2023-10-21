
package com.DAO;


import com.model.VentaProducto;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.sql.DataSource;

@Model
public class VentaProductoDAO implements Serializable{
    @Resource(lookup = "mysql/javasupermercadodb")
    private DataSource dataSource;
    
    
    public void agregarRegistroVentaProducto(VentaProducto registroVentas)throws SQLException{
        
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement pStmt = conn.prepareCall("INSERT INTO ventaproducto VALUES (?,?,?)");
            pStmt.setInt(1, registroVentas.getIdVenta());
             pStmt.setString(2, registroVentas.getIdProducto());
              pStmt.setInt(3, registroVentas.getcantidadVendida());
              pStmt.executeUpdate();
              pStmt.close();        
    }
        
    }  
    
    public List<VentaProducto> obtenerVentasProductoPorId(int idVenta) throws SQLException{
        List<VentaProducto> ventasProductos = new ArrayList<>();
        
        try(Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ventaproducto WHERE idVenta = "+idVenta);
            while(rs.next()){
                int id = rs.getInt("idVenta");
                String idProducto = rs.getString("idProducto");
                int cantidadVendida = rs.getInt("cantidad");
               
                ventasProductos.add(new VentaProducto(id,idProducto,cantidadVendida));
            }
    }
        return ventasProductos;
    }
}
