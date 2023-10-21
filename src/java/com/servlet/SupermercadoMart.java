
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
@WebServlet(name = "SupermercadoMart", urlPatterns = {"/SupermercadoMart"})
public class SupermercadoMart extends HttpServlet {

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
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN\" crossorigin=\"anonymous\">");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL\" crossorigin=\"anonymous\"></script>");
            out.println("<title>Servlet SupermercadoMart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 class=\"h2\">Bievenidos al Supermercado Java</h1>");
            try{
                List<Producto> productos = productoDAO.obtenerTodosLosProductos();
                
                mostrarProductos(context,out,productos);
                out.println("<p class=\"text-center\">Articulos en el carrito: "+carrito.getConteoArticulos()+"</p>");         
            }catch(SQLException ex){
                out.println("SQLException: "+ex);
            }
            out.println("<form action="+context+"/EmpujarCarrito><input type=\"submit\" value=\"Comprar Carrito\"></form>");
            out.println("</body>");
            out.println("</html>");
        }
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
        String id = request.getParameter("id");
        if(id!= null){
            carrito.agregarArticulo(id, 1);
        }
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
        processRequest(request, response);
    }

            
    private void mostrarProductos(String context, PrintWriter out, List<Producto> productos) throws SQLException{
        out.println("<table class=\"table table-secondary table-hover\" ");
        out.println("<tr>");
        out.println("<td>ID</td>");
       out.println("<td>Nombre</td>");
       out.println("<td>Descripcion</td>");
       out.println("<td>Precio</td>");
       out.println("<td>Cantidad Disponible</td>");
       out.println("<td>Acciones</td>");
       out.println("</tr>");
       
       for(Producto p : productos){
           Inventario i = inventarioDAO.obtenerInventario(p.getIdProducto());
           out.println("<tr>");
        out.println("<td>"+p.getIdProducto()+"</td>");
       out.println("<td>"+p.getNombreProducto()+"</td>");
       out.println("<td>"+p.getDescripcionProducto()+"</td>");
       out.println("<td>"+p.getPrecio()+"</td>");
       out.println("<td>"+i.getCantidadDisponible()+"</td>");
       if(i.getCantidadDisponible()>0){
           out.println("<td><a href=\""+context+"/SupermercadoMart?id="+p.getIdProducto()+"\">Comprar</a></td>");
       }else{
           out.println("<td style=\"color:red\">Agotado</td>");
       }
       out.println("</tr>");
       }
       out.println("</table>");
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
