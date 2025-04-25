package com.inventario.empresa_catering.controllers;

import com.inventario.empresa_catering.DB.DataBaseConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DataBaseConnector connector = DataBaseConnector.GetInstance();

        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String query = "SELECT * FROM usuarios WHERE email = ?";

        PreparedStatement ps = null;

        try {
            ps = connector.GetConnection().prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (BCrypt.checkpw(pass, rs.getString("password"))) {
                    //Si la contraseña y el correo coinciden, obtenemos el rol, id y nombre del usuario logeado
                    String rol = rs.getString("rol");
                    int idUsuario = rs.getInt("id_usuario");
                    String nombre = rs.getString("nombre");
                    //Guardamos  estos datos en la sesion iniciada
                    request.getSession().setAttribute("id_usuario", idUsuario);
                    request.getSession().setAttribute("nombre", nombre);
                    request.getSession().setAttribute("rol", rol);
                    //Dependiendo del rol lo enviamos a una pagina o a otra
                    if ("administrador".equals(rol)) {
                        response.sendRedirect("evento?action=list");
                    } else if ("cliente".equals(rol)) {
                        response.sendRedirect("evento?action=listCliente");
                    }
                } else {
                    request.setAttribute("MensajeError", "Contraseña incorrecta");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            else {
                request.setAttribute("MensajeError", "Correo o contraseña incorrectos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}