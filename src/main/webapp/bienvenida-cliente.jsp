<%@ page import="com.inventario.empresa_catering.models.Evento" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inventario.empresa_catering.models.Producto" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Pagina Principal</title>
    <link rel="stylesheet" href="bienvenidaCliente.css">
</head>
<body>
<h1>Bienvenido, ${sessionScope.nombre}</h1>
<div class="container">
    <h2>Estos son los eventos que tienes</h2>
    <table>
        <tr>
            <th>Nombre del evento</th>
            <th>Fecha</th>
            <th>Lugar</th>
            <th>Productos</th>
            <th>Acciones</th>
        </tr>
        <%
            List<Evento> eventosCliente =
                    (List<Evento>) request.getAttribute("eventos");

            if (eventosCliente != null ) {
                for (Evento even : eventosCliente) {
                    List<Producto> productos = even.getProductos();
        %>

        <tr>
            <td><%= even.getNombre_evento() %></td>
            <td><%= even.getFecha_evento() %></td>
            <td><%= even.getLugar_evento() %></td>
            <td>
                <ul>
                    <% if (productos != null && !productos.isEmpty()) {
                        for (Producto producto : productos) { %>
                    <li>
                        <%= producto.getNombre_producto() %>

                            <a href="add-product?action=delete&id_evento=<%= even.getId_evento() %>&id_producto=<%= producto.getId_producto() %>"
                               onclick="return confirm('¿Deseas eliminar este producto del evento?');">
                                Eliminar
                            </a>

                        <% }}else { %>
                    <li>No hay productos asociados a tu evento.</li>
                    <% } %>
                </ul>
            </td>
            <td>
                <a href="evento?action=edit&idEvento=<%= even.getId_evento() %>">Editar</a>
                <a href="evento?action=delete&id=<%= even.getId_evento() %>"
                   onclick="return confirm('¿Deseas eliminar este evento?');">
                    Eliminar
                </a>
                <a href="add-product?action=new&id_evento=<%= even.getId_evento() %>">Añadir Producto
                </a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<p>
    <a href="evento?action=new">Crear nuevo evento</a>
</p>
</body>
</html>