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
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAO();
        eventoProductoDAO = new EventoProductoDAO(); // Inicializamos el DAO para gestionar la relación Producto-Evento
    }

    @Override
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
            case "listCliente":
                listProductosByEvento(request, response);
                break;
            default:
                showProductList(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        if (action == null) action = "add"; // Default action to "add"

        if ("add".equals(action)) {
            addProductoToEvento(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id_evento = Integer.parseInt(request.getParameter("id_evento"));
        List<Producto> productos = null;

        try {
            productos = productoDAO.GetAllProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("productos", productos);
        request.setAttribute("id_evento", id_evento);

        RequestDispatcher dispatcher = request.getRequestDispatcher("add-product.jsp");
        dispatcher.forward(request, response);
    }

    private void listProductosByEvento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id_evento = Integer.parseInt(request.getParameter("id_evento"));
        List<Producto> productosEvento = null;

        try {
            productosEvento = eventoProductoDAO.getProductosPorEvento(id_evento);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("productosEvento", productosEvento);
        request.setAttribute("id_evento", id_evento);

        RequestDispatcher dispatcher = request.getRequestDispatcher("event-product-list.jsp");
        dispatcher.forward(request, response);
    }

    private void addProductoToEvento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int idEvento = Integer.parseInt(request.getParameter("id_evento"));
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));

        try {
            eventoProductoDAO.addProductoToEvento(idProducto, idEvento);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }

        response.sendRedirect("evento?action=listCliente"); // Redirigir al listado de eventos para el cliente
    }

    private void deleteProductoFromEvento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int idEvento = Integer.parseInt(request.getParameter("id_evento"));
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));

        try {
            eventoProductoDAO.removeProductoFromEvento(idEvento, idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("evento?action=listCliente"); // Redirigir al listado de eventos para el cliente
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> productos = null;

        try {
            productos = productoDAO.GetAllProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("productos", productos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("bienvenida-cliente.jsp");
        dispatcher.forward(request, response);
    }
}
