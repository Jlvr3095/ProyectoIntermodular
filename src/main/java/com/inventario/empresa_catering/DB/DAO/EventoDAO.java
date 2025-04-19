package com.inventario.empresa_catering.DB.DAO;
import com.inventario.empresa_catering.DB.DataBaseConnector;
import com.inventario.empresa_catering.models.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EventoDAO {

    Connection conn = DataBaseConnector.GetInstance().GetConnection();

    public ArrayList<Evento> GetAllEventos() throws SQLException {

        ArrayList<Evento> evens = new ArrayList<>();

        String query = "SELECT * FROM eventos";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Evento even = new Evento();
              even.setId_evento(rs.getInt("id_evento"));
              even.setNombre_evento(rs.getString("nombre_evento"));
              even.setFecha_evento(rs.getString("fecha_evento"));
              even.setLugar_evento(rs.getString("lugar_evento"));
              even.setId_usuario(rs.getInt("id_usuario"));

            evens.add(even);
        }
        return evens;
    }

    public ArrayList<Evento> GetEventosByUsuario(int idUsuario) throws SQLException {

        ArrayList<Evento> evens = new ArrayList<>();

        String query = "SELECT id_evento, nombre_evento, fecha_evento, lugar_evento, id_usuario FROM eventos WHERE id_usuario = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Evento even = new Evento();
            even.setId_evento(rs.getInt("id_evento"));
            even.setNombre_evento(rs.getString("nombre_evento"));
            even.setFecha_evento(rs.getString("fecha_evento"));
            even.setLugar_evento(rs.getString("lugar_evento"));
            even.setId_usuario(rs.getInt("id_usuario"));

            evens.add(even);
        }
        return evens;
    }

    public Evento GetEventoById(int id) throws SQLException {

        Evento even = null;
        String query = "SELECT id_evento, nombre_evento, fecha_evento, lugar_evento, id_usuario FROM eventos WHERE id_evento = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            even = new Evento();
            even.setId_evento(rs.getInt("id_evento"));
            even.setNombre_evento(rs.getString("nombre_evento"));
            even.setFecha_evento(rs.getString("fecha_evento"));
            even.setLugar_evento(rs.getString("lugar_evento"));
            even.setId_usuario(rs.getInt("id_usuario"));
        }
        return even;
    }

    public boolean AddEvento(Evento Evento) throws SQLException {

        String query = "INSERT INTO Eventos (nombre_evento, fecha_evento, lugar_evento, id_usuario) VALUES (?, ?, ?,?)";

        conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, Evento.getNombre_evento());
        ps.setString(2, Evento.getFecha_evento());
        ps.setString(3, Evento.getLugar_evento());
        ps.setInt(4, Evento.getId_usuario());

        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public boolean UpdateEvento(Evento Evento) throws SQLException {

        String query = "UPDATE Eventos SET nombre_evento = ?, fecha_evento = ?, lugar_evento = ?, id_usuario = ? WHERE id_evento = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, Evento.getNombre_evento());
        ps.setString(2, Evento.getFecha_evento());
        ps.setString(3, Evento.getLugar_evento());
        ps.setInt(4, Evento.getId_usuario());
        ps.setInt(5, Evento.getId_evento());

        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public boolean DeleteEvento(int id) throws SQLException {

        String query = "DELETE FROM eventos WHERE id_evento = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, id);
        ps.executeUpdate();

        int rows = ps.executeUpdate();
        return rows > 0;
    }
}
