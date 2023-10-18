
package com.DAO;

import com.model.Venta;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.sql.DataSource;

@Model
public class VentaDAO implements Serializable{
     @Resource(lookup = "mysql/javasupermercadodb")
    private DataSource dataSource;
     
     public Venta crearRegistroVentas()throws SQLException{
         try(Connection conn = dataSource.getConnection()){
              Statement stmt = conn.createStatement();
              stmt.executeUpdate("INSERT INTO venta (totalVenta) VALUES (0)",Statement.RETURN_GENERATED_KEYS);
              ResultSet rs = stmt.getGeneratedKeys();
              rs.next();
              return new Venta(rs.getInt(1),new Date(),0);
         }
     }
     
     public void actualizarRegistroVentas(Venta ventaProducto)throws SQLException{
         try(Connection conn = dataSource.getConnection()){
              PreparedStatement pstmt = conn.prepareStatement("UPDATE venta SET fechaVenta = ?, totalVenta = ? WHERE idVenta = ?");
              pstmt.setInt(3,ventaProducto.getIdVenta());
              pstmt.setDate(1,new java.sql.Date(ventaProducto.getFechaVenta().getTime()));
              pstmt.setDouble(2,ventaProducto.getTotalVenta());
              if(pstmt.executeUpdate() != 1){
                  throw new SQLException("Error al actualizar el registro de ventas");
              }
             
         }
     }
     
     public void removerRegistrosDeenta(Venta ventaProducto) throws SQLException{
         try(Connection conn = dataSource.getConnection()){
         Statement stmt = conn.createStatement();
         stmt.execute("DELETE FROM venta WHERE idVenta = "+ ventaProducto.getIdVenta());
         }
     }
}
