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
    public void init() throws ServletException {
        super.init();
        eventoDAO = new EventoDAO();
        eventoProductoDAO = new EventoProductoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("id_usuario") == null) {

            response.sendRedirect("evento-form.jsp");
            // Redirigir al login si no está autenticado
            return;
        }

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
            eventos = eventoDAO.GetAllEventos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaEventos", eventos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("producto?action=listProduct");
        dispatcher.forward(request, response);
    }

    private void listEventosCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer id_usuario = (Integer) request.getSession().getAttribute("id_usuario");

        List<Evento> eventosCliente = null;
        List<Producto> productosPorEvento = null;

        try {
            eventosCliente = eventoDAO.GetEventosByUsuario(id_usuario);
            for (Evento evento : eventosCliente) {
                productosPorEvento = eventoProductoDAO.getProductosPorEvento(evento.getId_evento());
                evento.setProductos(productosPorEvento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("eventos", eventosCliente);

        RequestDispatcher dispatcher = request.getRequestDispatcher("bienvenida-cliente.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("evento-form.jsp");
        dispatcher.forward(request, response);
    }

    private void editEventos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idEvento = Integer.parseInt(request.getParameter("idEvento"));
        Evento evento = null;

        try {
            evento = eventoDAO.GetEventoById(idEvento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String rol = (String) request.getSession().getAttribute("rol");

        int idEven = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);

        Evento even = new Evento();

            even.setNombre_evento(nombre_evento);
            even.setFecha_evento(fecha_evento);
            even.setLugar_evento(lugar_evento);
            even.setId_usuario(id_usuario);

        try{
            if (idEven == 0) {
                eventoDAO.AddEvento(even);
            } else {
                even.setId_evento(idEven);
                eventoDAO.UpdateEvento(even);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String rol = (String) request.getSession().getAttribute("rol");

        try {
            eventoDAO.DeleteEvento(idEvento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ("administrador".equals(rol)) {
            response.sendRedirect("evento?action=list");
        }
        else if ("cliente".equals(rol)) {
            response.sendRedirect("evento?action=listCliente");
        }
    }

}
