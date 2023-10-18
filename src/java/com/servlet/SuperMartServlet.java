
package com.servlet;

import com.DAO.InventorioDAO;
import com.DAO.ProductoDAO;
import com.beans.CarritoDeCompras;
import com.model.Inventario;
import com.model.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mont_
 */
@WebServlet(name = "SuperMartServlet", urlPatterns = {"/SuperMartServlet"})
public class SuperMartServlet extends HttpServlet {

    
    @Inject
   private ProductoDAO productoDAO;
    @Inject
   private InventorioDAO inventarioDAO;
   @Inject
    private CarritoDeCompras carrito;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String context = request.getContextPath();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SuperMartServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Bienvenido al Supermercado Java </h1>");
            try {
                List<Producto> productos = productoDAO.obtenerTodosLosProductos();
                mostrarProductos(context,out,productos);
                out.println("<p>Articulos en el carrito: "+ carrito.obtenerConteodeItems()+"</p>");
      
            } catch (SQLException e) {
                out.println("SQLException: "+e);
            }
            out.println("<form action= "+context+"/PurchaseCarrito><input type=\"submit\" value=\"Comprar Carrito\"></form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id != null){
            carrito.agregarItem(id, 1);
        }
        processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
//| id | nombre   | precio|disponible |accion |
    
    private void mostrarProductos(String context, PrintWriter out,List<Producto> productos)throws SQLException{
        out.println("<table border=\"1\"> ");
        out.println("<tr>");
        out.println("<td>ID</td>");
        out.println("<td>Nombre</td>");
        out.println("<td>Precio</td>");
        out.println("<td>Disponible</td>");
        out.println("<td>Acción</td>");
        out.println("</tr>");
        
        for(Producto p : productos ){
            Inventario i = inventarioDAO.obtenerInventario(p.getIdProducto());
             out.println("<tr>");
             out.println("<td>" + p.getIdProducto()+"</td>");
             out.println("<td>" + p.getNombreProducto()+"</td>");
             out.println("<td>" + p.getDescripcionProducto()+"</td>");
             out.println("<td>" + p.getPrecio()+"</td>");
             out.println("<td>" + i.getCantidadDisponible()+"</td>");
             if(i.getCantidadDisponible() >0){
                 out.println("<td><a href=\""+context+"/SuperMartServlet?id="+p.getIdProducto()+"\">Comprar</a></td>");          
             }else{
                 out.println("<td style=\"color:red\">Agotado</td>");
             }
             out.println("</tr>");
        }
        out.println("</table>"); 
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
