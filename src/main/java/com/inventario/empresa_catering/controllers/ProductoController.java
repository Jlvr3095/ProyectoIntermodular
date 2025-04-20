package com.inventario.empresa_catering.controllers;

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
    // Creamos la instancia del producto DAO para poder llamar a todos sus metodos
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAO();
    }

    @Override
    // Manejo de las solicitudes entrantes
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
    // Manejo de las solicitudes de envío
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
            // Llamamos al metodo del DAO para obtener todos los productos
            productos = productoDAO.getAllProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Guardamos la vista de los productos para usarla en los JSP
        request.setAttribute("ListaProductos", productos);
        // Redirigimos a la lista de usuarios para que posteriormente nos envie a la página del administrador
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario?action=list");
        dispatcher.forward(request, response);
    }
    private void newProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigimos a la página para crear un nuevo producto
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtenemos el ID del producto que vamos a editar
        int idProducto = Integer.parseInt(request.getParameter("id"));

        Producto producto = null;

        try {
            // LLamamos al metodo del DAO para obtener el producto por ID
            producto = productoDAO.getProductoById(idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Guardamos la vista del producto para poder usarla en el JSP
        request.setAttribute("producto", producto);
        // Redirigimos al JSP de editar un producto
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
        // Obtenemos el rol del usuario que haya iniciado sesion para que solo el administrador pueda crear o editar un producto
        String rol = (String) request.getSession().getAttribute("rol");

        int idProducto = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);
        // Crea un producto nuevo con los datos recibidos
        Producto product = new Producto();
        product.setNombre_producto(nombre_producto);
        product.setDescripcion(descripcion);
        product.setPrecio(precio);
        product.setStock(stock);

        try{
            if (idProducto == 0) {
                // Llamamos al metodo del DAO para insertar un producto nuevo en la base de datos
                productoDAO.addProducto(product);
            } else {
                product.setId_producto(idProducto);
                productoDAO.updateProdcuto(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            // Redirigimos a la pagina del administrador
            response.sendRedirect("evento?action=list");
    }


    private void deleteProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("id"));

        try {
            // Llamamos al metodo del DAO para eliminar un producto de la base de datos
            productoDAO.deleteProdcuto(idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Redirigimos a la pagina del administrador
        response.sendRedirect("evento?action=list");
    }

}
