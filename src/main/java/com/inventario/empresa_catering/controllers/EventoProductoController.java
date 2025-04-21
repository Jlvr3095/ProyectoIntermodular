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

@WebServlet(name = "AddProductoEventoServlet", urlPatterns = {"/add-product"})
public class EventoProductoController extends HttpServlet {

    private ProductoDAO productoDAO;
    private EventoProductoDAO eventoProductoDAO;

    @Override
    // Obtenemos la instancia de productoDAO y eventoProductoDAO para poder llamar sus metodos
    public void init() throws ServletException {

        super.init();
        productoDAO = new ProductoDAO();
        eventoProductoDAO = new EventoProductoDAO(); // Inicializamos el DAO para gestionar la relación Producto-Evento
    }

    @Override
    // Manejo de las solicitudes entrantes
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showAddForm(request, response);
                break;
            case "delete":
                deleteProductoFromEvento(request, response);
                break;
            default:
        }
    }

    @Override
    // Manejo de las solicitudes de envío
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        if (action == null) action = "add";

        if ("add".equals(action)) {
            addProductoToEvento(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtenemos el ID del evento
        Integer id_evento = Integer.parseInt(request.getParameter("id_evento"));
        List<Producto> productos = null;

        try {
            // Llamamos al metodo del producto DAO para obtener todos los productos de la base de datos
            productos = productoDAO.getAllProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Guardamos la vista del id del evento selecionado y todos los productos disponibles
        request.setAttribute("productos", productos);
        request.setAttribute("id_evento", id_evento);
        // Redirigimos a la página para añadir un producto a un evento
        RequestDispatcher dispatcher = request.getRequestDispatcher("add-product.jsp");
        dispatcher.forward(request, response);
    }

    private void addProductoToEvento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Obtenemos el id del evento y el id del producto
        int idEvento = Integer.parseInt(request.getParameter("id_evento"));
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));

        try {
            // Llamamos al metodo del eventoProducto DAO para asociar un producto a un evento
            eventoProductoDAO.addProductoToEvento(idProducto, idEvento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Redirigimos a la pagina del list cliente para recuperar los datos y que posteriormente nos redirija a la pagina principal del cliente
        response.sendRedirect("evento?action=listCliente");
    }

    private void deleteProductoFromEvento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //  Obtenemos el id del evento y el id del producto
        int idEvento = Integer.parseInt(request.getParameter("id_evento"));
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));

        try {
            // LLamamos al metodo del eventoProducto DAO para eliminar un producto asociado a un evento
            eventoProductoDAO.removeProductoFromEvento(idEvento, idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Redirigimos a la pagina del list cliente para recuperar los datos y que posteriormente nos redirija a la pagina principal del cliente
        response.sendRedirect("evento?action=listCliente");
    }

}
