<%@ page import="com.inventario.empresa_catering.models.Evento" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inventario.empresa_catering.models.Producto" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="cliente.css">
    <title>Pagina Principal del cliente</title>
</head>
<p>
    <a href="index.jsp" title="Cerrar sesion">Cerrar sesion</a>
</p>
<body>
<header>
    <h1>Bienvenid@, ${sessionScope.nombre}</h1>
</header>

<section class="container">
    <h2>Estos son los eventos que tienes</h2>
    <table>
        <thead>
        <tr>
            <th>Nombre del evento</th>
            <th>Fecha</th>
            <th>Lugar</th>
            <th>Productos</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Evento> eventosCliente = (List<Evento>) request.getAttribute("eventos");

            if (eventosCliente != null) {
                for (Evento evento : eventosCliente) {
                    List<Producto> productos = evento.getProductos();
        %>
        <tr>
            <td><%= evento.getNombre_evento() %></td>
            <td><%= evento.getFecha_evento() %></td>
            <td><%= evento.getLugar_evento() %></td>
            <td>
                <ul>
                    <% if (productos != null && !productos.isEmpty()) {
                        for (Producto producto : productos) { %>
                    <li>
                        <%= producto.getNombre_producto() %>
                        <a href="add-product?action=delete&id_evento=<%= evento.getId_evento() %>&id_producto=<%= producto.getId_producto() %>"
                           onclick="return confirm('¿Deseas eliminar este producto del evento?');" title="Eliminar producto">
                            Eliminar
                        </a>
                    </li>
                    <% }} else { %>
                    <li>No hay productos asociados a tu evento.</li>
                    <% } %>
                </ul>
            </td>
            <td>
                <a href="evento?action=edit&idEvento=<%= evento.getId_evento() %>" title="Editar evento">Editar</a>
                <a href="evento?action=delete&id=<%= evento.getId_evento() %>" onclick="return confirm('¿Deseas eliminar este evento?');" title="Eliminar evento">Eliminar</a>
                <a href="add-product?action=new&id_evento=<%= evento.getId_evento() %>" title="Añadir producto">Añadir Producto</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</section>

<footer>
    <p>
        <a href="evento?action=new" title="Crear nuevo evento">Crear nuevo evento</a>
    </p>
</footer>
</body>
</html>