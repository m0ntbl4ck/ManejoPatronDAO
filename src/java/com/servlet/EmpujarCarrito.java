
package com.servlet;

import com.beans.CarritoDeCompras;
import com.model.Producto;
import com.utilidades.EmpujarException;
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

/**
 *
 * @author mont_
 */
@WebServlet(name = "EmpujarCarrito", urlPatterns = {"/EmpujarCarrito"})
public class EmpujarCarrito extends HttpServlet {

    private static final Logger logger = Logger.getLogger("com.servlet.EmpujarCarrito");
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
            out.println("<title>Supermercado Java Carrito de Compras</title>");            
            out.println("</head>");
            out.println("<body>");
            try{
                mostrarCarritoVentas(out);
            }catch  (SQLException ex){
                out.println("<p>"+ex+"</p>");
            }  
            
            out.println("<form method=\"post\">");
            out.println("<input type=\"submit\" name=\"action\" value=\"Comprar\">");
            out.println("<input type=\"submit\" name=\"action\" value=\"Cancelar\">");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private void mostrarCarritoVentas(PrintWriter out)throws SQLException{
        out.println("<h2>Su carrito Contiene: </h2>");
         out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<td>ID</td>");
       out.println("<td>Nombre</td>");
       out.println("<td>Descripcion</td>");
       out.println("<td>Precio</td>");
       out.println("<td>Cantidad Disponible</td>");
       out.println("<td>Subtotal</td>");
       out.println("</tr>");
       Map<Producto, Integer> carrito = carritoDeCompras.getCarrito();
       Set<Producto> productos = carrito.keySet();
       double ventaTotal = 0.0,subTotal=0.0;
       for(Producto p: productos){
           out.println("<tr>");
           out.println("<td>"+p.getIdProducto()+"</td>");
       out.println("<td>"+p.getNombreProducto()+"</td>");
       out.println("<td>"+p.getDescripcionProducto()+"</td>");
       out.println("<td>"+p.getPrecio()+"</td>");
       out.println("<td>"+carrito.get(p)+"</td>");
       subTotal = carrito.get(p)*p.getPrecio();
       out.println("<td>"+subTotal+"</td>");
       out.println("</tr>");
       ventaTotal +=subTotal;
       }
          out.println("</table>");
          out.println("<p>Venta Total: "+ String.format("$%.2f",ventaTotal)+"</p>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try(PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Supermercado Java Carrito de Compras</title>");            
            out.println("</head>");
            out.println("<body>");
            try{
                switch (action){
                    case"Comprar":
                        carritoDeCompras.empujarCarrito();
                        out.println("<h1>Exito! Su pedido será enviado pronto</h1>");
                        break;
                    case "Cancelar":
                        carritoDeCompras.resetCarrito();
                        out.println("<h1>Su pedido ha sido cancelado</h1>");
                         break;
                         }  
            }catch(EmpujarException ex){
                logger.log(Level.INFO,ex.getMessage());
                out.println("<h2>"+ex.getMessage()+"</h2>");
            } catch(Exception e){
                out.println("<h2>Error al ejecutar la transaccion</h2>");
                logger.log(Level.INFO,e.getMessage());
            }
            out.println("<form action=\""+request.getContextPath()+"/SupermercadoMart\"><input type=\"submit\" value=\"Volver a Comprar\"></form>");
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
