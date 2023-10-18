
package com.servlet;

import com.beans.CarritoDeCompras;
import com.model.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.PurchaseException;

/**
 *
 * @author mont_
 */
@WebServlet(name = "PurchaseCarrito", urlPatterns = {"/PurchaseCarrito"})
public class PurchaseCarrito extends HttpServlet {

  private static final Logger logger = Logger.getLogger("com.servlet.PruchaseCarrito");
  
  @Inject
  private CarritoDeCompras carritoDeCompras;
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PurchaseCarrito</title>");            
            out.println("</head>");
            out.println("<body>");
            try {
                mostrarCarritoVentas(out);
                
            } catch (SQLException e) {
                out.println("<p>"+e+"<p>");
            }
             out.println("<form method=\"post\">");
             out.println("<input type=\"submit\" name=\"action\" value=\"Comprar\">");
             out.println("<input type=\"submit\" name=\"action\" value=\"Cancelar\">");
            out.println("</body>");
            out.println("</html>");
        }
    }
public void mostrarCarritoVentas(PrintWriter out) throws SQLException{
    out.println("<h2>Su carrito Contiene: </h2>");
    out.println("<table border =\"1\">");
    out.println("<tr>");
    out.println("<td>ID</td>");
    out.println("<td>Nombre</td>");
    out.println("<td>Descripcion</td>");
    out.println("<td>Precio</td>");
    out.println("<td>Cantidad</td>");
    out.println("<td>SubTotal</td>");
    out.println("</tr>");
    Map<Producto,Integer> carrito =carritoDeCompras.obtenercarrito();
    Set<Producto> productos = carrito.keySet();
    double ventaTotal = 0, subTotal = 0;
    for(Producto p : productos){
        out.println("<tr>");
             out.println("<td>" + p.getIdProducto()+"</td>");
             out.println("<td>" + p.getNombreProducto()+"</td>");
             out.println("<td>" + p.getDescripcionProducto()+"</td>");
             out.println("<td>" + p.getPrecio()+"</td>");
             out.println("<td>"+carrito.get(p)+"</td>");
             subTotal = carrito.get(p) *p.getPrecio();
             
             out.println("<td>"+ subTotal + "</td>");
            out.println("<tr>");
            ventaTotal += subTotal;
    }
     out.println("</table>");
     out.println("<p>Venta Total : "+String.format("$%.2f",ventaTotal)+"</p>");
}
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try(PrintWriter out = response.getWriter()){
             out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PurchaseCarrito</title>");            
            out.println("</head>");
            out.println("<body>");
            try{
            switch(action){
                case "Comprar":
                    carritoDeCompras.purchaseCarrito();
                    out.println("<h1>Exito! Su pedido sera enviado pronto</h1>");
                    break;
                    case "Cancelar":
                    carritoDeCompras.reiniciarCarrito();
                    out.println("<h1>Su pedido fue cancelado</h1>");
                    break;
            }
        }catch(PurchaseException ex){
            logger.log(Level.INFO, ex.getMessage());
            out.println("<h2>"+ex.getMessage()+"</h2>");
        }
                        out.println("<form action=\""+request.getContextPath()+"/SuperMartServlet\"><input type=\"submit\" value=\"Volver a Comprar\"</form>");
                       out.println("</body>");
            out.println("</html>"); 
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
