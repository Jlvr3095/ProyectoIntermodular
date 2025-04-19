<%@ page import="com.inventario.empresa_catering.models.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>
</head>
<body>
<h2>Lista de Usuarios</h2>
<a href="usuario?action=new">Nuevo Usuario</a>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Email</th>
        <th>Rol</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Obtener lista de usuarios
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
        if (usuarios != null) {
            for (Usuario usuario : usuarios) {
    %>
    <tr>
        <td><%= usuario.getId_usuario() %></td>
        <td><%= usuario.getNombre() + " " + usuario.getApellidos() %></td>
        <td><%= usuario.getEmail() %></td>
        <td><%= usuario.getRol() %></td>
        <td>
            <a href="usuario?action=edit&id=<%= usuario.getId_usuario() %>">Editar</a>
            <a href="usuario?action=delete&id=<%= usuario.getId_usuario() %>" onclick="return confirm('¿Seguro que quieres eliminar este usuario?')">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>