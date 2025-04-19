package com.inventario.empresa_catering.controllers;
import com.inventario.empresa_catering.DB.DAO.UsuarioDAO;
import com.inventario.empresa_catering.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuario"})
public class UserController extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                editUsuario(request, response);
                break;
            case "delete":
                deleteUsuario(request, response);
                break;
            case "list":
            default:
                listUsuarios(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "save";

        if ("save".equals(action)) {
            saveUsuario(request, response);
        }
    }

    private void listUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Usuario> usuarios = null;

        try {
            usuarios = usuarioDAO.GetAllUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listaUsuarios", usuarios);

        RequestDispatcher dispatcher = request.getRequestDispatcher("lista_eventos.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("RegistroEdicion.jsp");
        dispatcher.forward(request, response);
    }

    private void editUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = null;

        try {
            usuario = usuarioDAO.GetUsuarioById(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("usuario", usuario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("RegistroEdicion.jsp");
        dispatcher.forward(request, response);

    }

    private void saveUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id_usuario");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String rol = request.getParameter("rol");
        String password = request.getParameter("password");

        if (rol == null || rol.isEmpty()) {
            rol = "cliente";
        }

        int idUser = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);

        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setApellidos(apellidos);
        user.setEmail(email);
        user.setRol(rol);
        user.setPassword(password);

        try {
            if (idUser == 0) {
                usuarioDAO.AddUsuario(user);
            } else {
                user.setId_usuario(idUser);
                usuarioDAO.UpdateUsuario(user);
            }
            user.setId_usuario(idUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("index.jsp");
    }

    private void deleteUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("id"));

        try {
            usuarioDAO.DeleteUsuario(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("evento?action=list");
    }
}

