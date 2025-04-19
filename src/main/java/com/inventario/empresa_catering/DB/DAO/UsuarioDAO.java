package com.inventario.empresa_catering.DB.DAO;

import com.inventario.empresa_catering.DB.DataBaseConnector;
import com.inventario.empresa_catering.models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    public ArrayList<Usuario> GetAllUsuarios() throws SQLException {
        ArrayList<Usuario> users = new ArrayList<>();

        String query = "SELECT * FROM Usuarios";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Usuario user = new Usuario();
            user.setId_usuario(rs.getInt("id_usuario"));
            user.setNombre(rs.getString("nombre"));
            user.setApellidos(rs.getString("apellidos"));
            user.setEmail(rs.getString("email"));
            user.setRol(rs.getString("rol"));

            users.add(user);
        }
        return users;
    }
    public Usuario GetUsuarioById(int id) throws SQLException {
        Usuario user = null;
        String query = "SELECT id_usuario, nombre, apellidos, email, rol, password FROM usuarios WHERE id_usuario = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new Usuario();
            user.setId_usuario(rs.getInt("id_usuario"));
            user.setNombre(rs.getString("nombre"));
            user.setApellidos(rs.getString("apellidos"));
            user.setEmail(rs.getString("email"));
            user.setRol(rs.getString("rol"));
            user.setPassword(rs.getString("password"));
        }
        return user;
    }
    public boolean AddUsuario(Usuario Usuario) throws SQLException {
        String passwordHash = BCrypt.hashpw(Usuario.getPassword(), BCrypt.gensalt());
        String query = "INSERT INTO Usuarios (nombre, apellidos, email, rol, password) VALUES (?, ?, ?, ?, ?)";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, Usuario.getNombre());
        ps.setString(2, Usuario.getApellidos());
        ps.setString(3, Usuario.getEmail());
        ps.setString(4, Usuario.getRol());
        ps.setString(5, passwordHash);

        int rows = ps.executeUpdate();
        return rows > 0;
    }
    public boolean UpdateUsuario(Usuario Usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, apellidos = ?, email = ?, rol = ?, password = ? WHERE id_usuario = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, Usuario.getNombre());
        ps.setString(2, Usuario.getApellidos());
        ps.setString(3, Usuario.getEmail());
        ps.setString(4, Usuario.getRol());
        ps.setString(5, Usuario.getPassword());
        ps.setInt(6, Usuario.getId_usuario());
        int rows = ps.executeUpdate();
        return rows > 0;
    }
    public boolean DeleteUsuario(int id) throws SQLException {

        String query = "DELETE FROM usuarios WHERE id_usuario = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ps.executeUpdate();

        int rows = ps.executeUpdate();
        return rows > 0;
    }
}

