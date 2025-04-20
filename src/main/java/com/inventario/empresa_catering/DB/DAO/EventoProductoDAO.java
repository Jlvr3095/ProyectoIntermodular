package com.inventario.empresa_catering.DB.DAO;

import com.inventario.empresa_catering.DB.DataBaseConnector;
import com.inventario.empresa_catering.models.Evento;
import com.inventario.empresa_catering.models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EventoProductoDAO {
    // Metodo para asociar un producto a un evento
    public boolean addProductoToEvento(int idProducto, int idEvento) throws SQLException {

        String query = "INSERT INTO evento_producto (id_evento, id_producto) VALUES (?, ?)";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idEvento);
        ps.setInt(2, idProducto);

        int rows = ps.executeUpdate();
        return rows > 0;
    }
    // Metodo para desasociar un producto de un evento
    public boolean removeProductoFromEvento( int idEvento, int idProducto) throws SQLException {
        String query = "DELETE FROM evento_producto WHERE id_evento = ? AND id_producto = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, idEvento);
        ps.setInt(2, idProducto);

        int rows = ps.executeUpdate();
        return rows > 0;
    }
    // Metodo para obtener productos por Evento
    public ArrayList<Producto> getProductosPorEvento(int idEvento) throws SQLException {

        ArrayList<Producto> productos = new ArrayList<>();

        String queryProductos =
                "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, p.stock " +
                "FROM productos p " +
                "JOIN evento_producto ep ON p.id_producto = ep.id_producto " +
                "WHERE ep.id_evento = ?";

            Connection conn = DataBaseConnector.GetInstance().GetConnection();
            PreparedStatement ps = conn.prepareStatement(queryProductos);

            ps.setInt(1, idEvento);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));

                Evento evento = new Evento();
                evento.setId_evento(idEvento);
                producto.setEvento(evento);
                productos.add(producto);
            }
            return productos;
    }
}
