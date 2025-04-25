<%@ page import="com.inventario.empresa_catering.models.Producto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title>Añadir Producto al Evento</title>
    <link rel="stylesheet" href="addProduct.css">
    <script src="addProduct.js"></script>
</head>
<body>
<h1>Añadir Productos a Evento</h1>

<form method="post" action="add-product">
    <input type="hidden" name="action" value="add" />
    <input type="hidden" name="id_evento" value="${requestScope.id_evento}" />

    <label for="id_producto">Selecciona Producto:</label>
    <select name="id_producto" id="id_producto" onchange="mostrarDetallesProducto()">
        <%
            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
            if (productos != null) {
                for (Producto producto : productos) {
        %>
        <option value="<%= producto.getId_producto() %>"
                data-nombre="<%= producto.getNombre_producto() %>"
                data-descripcion="<%= producto.getDescripcion() %>"
                data-precio="<%= producto.getPrecio() %>">
            <%= producto.getNombre_producto() %>
        </option>
        <% } } %>
    </select>

    <p><strong>Descripción:</strong> <span id="descripcion">Selecciona un producto</span></p>
    <p><strong>Precio:</strong> <span id="precio">Selecciona un producto</span></p>

    <button type="submit">Añadir Producto</button>
</form>

<p><a href="evento?action=listCliente">Regresar al listado de eventos</a></p>

</body>
</html>