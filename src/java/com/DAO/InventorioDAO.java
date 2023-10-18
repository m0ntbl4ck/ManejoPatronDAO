
package com.DAO;

import com.model.Inventario;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.sql.DataSource;

@Model
public class InventorioDAO implements Serializable {
   
    @Resource(lookup = "mysql/javasupermercadodb")
    private DataSource dataSource;
    
    public Inventario obtenerInventario(String idProducto) throws SQLException{
        
        Inventario inventario = null;
        try(Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventario WHERE idProducto = '"+idProducto + "'");
            rs.next();
            inventario = new Inventario(idProducto,rs.getInt("disponible"));
            stmt.close();
            rs.close();
        }
        finally{
            
        }
        return inventario;
    }
    
    public void actualizarInventario(String idProducto,int cantidad) throws SQLException{
        try(Connection conn = dataSource.getConnection()){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE inventario SET disponible = "+cantidad+" WHERE idProducto = '"+idProducto+"'");
            
            stmt.close();
        }
    }
    
}
