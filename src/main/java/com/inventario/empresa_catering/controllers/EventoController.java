package com.inventario.empresa_catering.controllers;

import com.inventario.empresa_catering.DB.DAO.EventoDAO;
import com.inventario.empresa_catering.DB.DAO.EventoProductoDAO;
import com.inventario.empresa_catering.models.Evento;
import com.inventario.empresa_catering.models.Producto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EventoServlet", urlPatterns = {"/evento"})
public class EventoController extends HttpServlet {
    private EventoDAO eventoDAO;
    private EventoProductoDAO eventoProductoDAO;

    @Override
    // Creamos la instancia del evento DAO y el eventoProducto DAO para poder llamar a sus metodos
    public void init() throws ServletException {

        super.init();
        eventoDAO = new EventoDAO();
        eventoProductoDAO = new EventoProductoDAO();
    }

    @Override
    // Manejo de las solicitudes entrantes
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                editEventos(request, response);
                break;
            case "delete":
                deleteEvento(request, response);
                break;
            case "listCliente":
                listEventosCliente(request, response);
            case "list":
            default:
                listEventos(request, response);
                break;
        }
    }

    @Override
    // Manejo de las solicitudes de envío
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        if (action == null) action = "save";

        if ("save".equals(action)) {
            saveEvento(request, response);
        }
    }

    private void listEventos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Evento> eventos = null;

        try {
            // Llamamos al metodo de eventoDAO para obetner la lista de eventos de la base de datos
            eventos = eventoDAO.getAllEventos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Guardamos la vista de la lista de eventos para poder usarla en el JSP
        request.setAttribute("listaEventos", eventos);
        // Redirigimos a la lista de productos que a su vez nos dirige a la lista de usuarios y posteriormente al JSP del administrador
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto?action=listProduct");
        dispatcher.forward(request, response);
    }

    private void listEventosCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id_usuario = (Integer) request.getSession().getAttribute("id_usuario");

        List<Evento> eventosCliente = null;
        List<Producto> productosPorEvento = null;

        try {
            // Llamamos al metodo de evento DAO y obtenemos todos los eventos por usuario
            eventosCliente = eventoDAO.getEventosByUsuario(id_usuario);
            // Recorremos los eventos que tenga el usuario
            for (Evento evento : eventosCliente) {
                // Llamamos al metodo de eventoProducto DAO para que obtener los productos por el ID del evento
                productosPorEvento = eventoProductoDAO.getProductosPorEvento(evento.getId_evento());
                evento.setProductos(productosPorEvento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Gaurdamos la vista de los eventos asociados con el cliente y los productos asociados con cada evento
        request.setAttribute("eventos", eventosCliente);
        // Redirigimos a la pagina del cliente
        RequestDispatcher dispatcher = request.getRequestDispatcher("bienvenida-cliente.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigimos al formulario para crear un evento nuevo
        RequestDispatcher dispatcher = request.getRequestDispatcher("evento-form.jsp");
        dispatcher.forward(request, response);
    }
    // No se usa ya que el administrador no tiene la opcion de editar un evento, pero lo dejo por si se necesita mas adelante
    private void editEventos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtenemos el id del evento que vamos a editar
        int idEvento = Integer.parseInt(request.getParameter("idEvento"));
        Evento evento = null;

        try {
            // LLamamos al metodo del evento DAO que nos permite editar un evento existente
            evento = eventoDAO.getEventoById(idEvento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Guardamos la vista para poder usarlo en el JSP y nos de los datos del evento existente
        request.setAttribute("evento", evento);

        RequestDispatcher dispatcher = request.getRequestDispatcher("evento-form.jsp");
        dispatcher.forward(request, response);
    }

    private void saveEvento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id_evento");
        String nombre_evento = request.getParameter("nombre_evento");
        String fecha_evento = request.getParameter("fecha_evento");
        String lugar_evento = request.getParameter("lugar_evento");
        Integer id_usuario = (Integer) request.getSession().getAttribute("id_usuario");
        // Guardamos el rol del usuario que tenga la sesion iniciada
        String rol = (String) request.getSession().getAttribute("rol");

        int idEven = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);

        Evento even = new Evento();

            even.setNombre_evento(nombre_evento);
            even.setFecha_evento(fecha_evento);
            even.setLugar_evento(lugar_evento);
            even.setId_usuario(id_usuario);

        try{
            if (idEven == 0) {
                eventoDAO.addEvento(even);
            } else {
                even.setId_evento(idEven);
                eventoDAO.updateEvento(even);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Dependiendo del rol lo enviamos a una página o a otra
        if ("administrador".equals(rol)) {
            response.sendRedirect("evento?action=list");
        }
        else if ("cliente".equals(rol)) {
            response.sendRedirect("evento?action=listCliente");
        }

    }

    private void deleteEvento(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {

        int idEvento = Integer.parseInt(request.getParameter("id"));
        // Guardamos el rol del usuario que tenga la sesion iniciada
        String rol = (String) request.getSession().getAttribute("rol");

        try {
            // LLamamos al metodo del evento DAO para eliminar un evento registrado en la base de datos
            eventoDAO.deleteEvento(idEvento);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Dependiendo del rol enviamos a una página o a otra
        if ("administrador".equals(rol)) {
            response.sendRedirect("evento?action=list");
        }
        else if ("cliente".equals(rol)) {
            response.sendRedirect("evento?action=listCliente");
        }
    }

}
