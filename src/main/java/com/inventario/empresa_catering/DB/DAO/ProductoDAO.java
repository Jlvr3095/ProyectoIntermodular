package com.inventario.empresa_catering.DB.DAO;

import com.inventario.empresa_catering.DB.DataBaseConnector;
import com.inventario.empresa_catering.models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO {
    public ArrayList<Producto> getAllProductos() throws SQLException {

        ArrayList<Producto> products = new ArrayList<>();

        String query = "SELECT * FROM productos";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Producto product = new Producto();
            product.setId_producto(rs.getInt("id_producto"));
            product.setNombre_producto(rs.getString("nombre_producto"));
            product.setDescripcion(rs.getString("descripcion"));
            product.setPrecio(rs.getDouble("precio"));
            product.setStock(rs.getInt("stock"));

            products.add(product);
        }
        return products;
    }

    public Producto getProductoById(int id) throws SQLException {
        Producto product = null;
        String query = "SELECT id_producto, nombre_producto, descripcion, precio, stock FROM productos WHERE id_producto = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            product = new Producto();
            product.setId_producto(rs.getInt("id_producto"));
            product.setNombre_producto(rs.getString("nombre_producto"));
            product.setDescripcion(rs.getString("descripcion"));
            product.setPrecio(rs.getDouble("precio"));
            product.setStock(rs.getInt("stock"));
        }
        return product;
    }

    public boolean addProducto (Producto Producto) throws SQLException {

        String query = "INSERT INTO Productos (nombre_producto, descripcion, precio, stock) VALUES (?, ?, ?, ?)";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, Producto.getNombre_producto());
        ps.setString(2, Producto.getDescripcion());
        ps.setDouble(3, Producto.getPrecio());
        ps.setInt(4, Producto.getStock());

        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public boolean updateProdcuto(Producto Producto) throws SQLException {

        String query = "UPDATE Productos SET nombre_producto = ?, descripcion = ?, precio = ?, stock = ? WHERE id_producto = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, Producto.getNombre_producto());
        ps.setString(2, Producto.getDescripcion());
        ps.setDouble(3, Producto.getPrecio());
        ps.setInt(4, Producto.getStock());
        ps.setInt(5, Producto.getId_producto());
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public boolean deleteProdcuto(int id) throws SQLException {

        String query = "DELETE FROM Productos WHERE id_producto = ?";

        Connection conn = DataBaseConnector.GetInstance().GetConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, id);

        ps.executeUpdate();

        int rows = ps.executeUpdate();
        return rows > 0;
    }
}
