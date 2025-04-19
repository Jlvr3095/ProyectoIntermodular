package com.inventario.empresa_catering.controllers;
import com.inventario.empresa_catering.DB.DAO.EventoProductoDAO;
import com.inventario.empresa_catering.DB.DAO.ProductoDAO;
import com.inventario.empresa_catering.models.Producto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/producto"})
public class ProductoController extends HttpServlet {

    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "new":
                newProduct(request, response);
                break;
            case "edit":
                editProduct(request, response);
                break;
            case "delete":
                deleteProducto(request, response);
                break;
            case "listProduct":
            default:
                listProductos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "save";

        if ("save".equals(action)) {
            saveProducto(request, response);
        }
    }

    private void listProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> productos = null;

        try {
            productos = productoDAO.GetAllProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("ListaProductos", productos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("lista_eventos.jsp");
        dispatcher.forward(request, response);
    }
    private void newProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("id"));

        Producto producto = null;

        try {
            producto = productoDAO.GetProductoById(idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("producto", producto);

        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void saveProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id_producto");
        String nombre_producto = request.getParameter("nombre_producto");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String rol = (String) request.getSession().getAttribute("rol");

        int idProducto = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);

        Producto product = new Producto();
        product.setNombre_producto(nombre_producto);
        product.setDescripcion(descripcion);
        product.setPrecio(precio);
        product.setStock(stock);

        try{
            if (idProducto == 0) {
                productoDAO.AddProducto(product);
            } else {
                product.setId_producto(idProducto);
                productoDAO.UpdateProdcuto(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if ("administrador".equals(rol)) {
            response.sendRedirect("evento?action=list");
        }
    }


    private void deleteProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("id"));

        try {
            productoDAO.DeleteProdcuto(idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("evento?action=list");
    }

}
