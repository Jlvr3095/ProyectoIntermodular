<%@ page import="com.inventario.empresa_catering.models.Evento" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inventario.empresa_catering.models.Producto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="admin.css">
    <title>Listado de Eventos</title>
</head>
<body>
<h1>Bienvenido, ${sessionScope.nombre}</h1>
<div class="container">
    <h2>Listado de Eventos</h2>
    <table>
        <tr>
            <th>Nombre</th>
            <th>Fecha del evento</th>
            <th>Lugar del evento</th>
            <th>ID del usuario</th>
        </tr>
        <%
            List<Evento> listaEventos =
                    (List<Evento>) request.getAttribute("listaEventos");

            if (listaEventos != null) {
                for (Evento even : listaEventos) {
        %>
        <tr>
            <td><%= even.getNombre_evento() %></td>
            <td><%= even.getFecha_evento() %></td>
            <td><%= even.getLugar_evento()%></td>
            <td><%= even.getId_usuario()%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<div class="container">
    <h2>Listado de Productos</h2>
    <table>
        <tr>
            <th>Nombre producto</th>
            <th>Descripcion</th>
            <th>Precio</th>
            <th>Stock</th>
            <th>Acciones</th>

        </tr>
        <%
            List<Producto> productos =
                    (List<Producto>) request.getAttribute("ListaProductos");

            if (productos != null) {
                for (Producto product : productos) {
        %>
        <tr>
            <td><%= product.getNombre_producto() %></td>
            <td><%= product.getDescripcion() %></td>
            <td><%= product.getPrecio() %> € </td>
            <td><%= product.getStock() %></td>
            <td>
                <a href="producto?action=edit&id=<%= product.getId_producto() %>">Editar</a>
                <a href="producto?action=delete&id=<%= product.getId_producto() %>"
                   onclick="return confirm('¿Deseas eliminar este producto?');">
                Eliminar
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
    <a href="producto?action=new">Crear nuevo Producto</a>
</p>
</body>
</html>
