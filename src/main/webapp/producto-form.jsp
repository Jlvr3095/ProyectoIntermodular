<%@ page import="com.inventario.empresa_catering.models.Producto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Producto producto = (Producto) request.getAttribute("producto");
    boolean isEdit = (producto != null); // Si no es nulo, es edición
%>

<html>
<head>
    <title><%= isEdit ? "Editar Producto" : "Nuevo producto" %></title>
    <link rel="stylesheet" href="addProduct.css">
</head>
<body>
<div class="container">
    <h1><%= isEdit ? "Editar Producto" : "Nuevo producto" %></h1>

    <form action="producto" method="post">
        <input type="hidden" name="action" value="save" />

        <% if (isEdit) { %>
        <input type="hidden" name="id_producto" value="<%= producto.getId_producto() %>" />
        <% } %>
        <p>
            <label>Nombre del producto:</label><br />
            <input type="text" name="nombre_producto"
                   value="<%= isEdit ? producto.getNombre_producto() : "" %>" required />
        </p>
        <p>
            <label>Descripcion:</label><br />
            <input type="text" name="descripcion"
                   value="<%= isEdit ? producto.getDescripcion() : "" %>" required />
        </p>
        <p>
            <label>Precio:</label><br/>
            <input type="number" name="precio" step="0.01" min="0"
                   value="<%= isEdit ? producto.getPrecio() : "" %>" required>
        </p>
        <p>
            <label>Stock:</label><br/>
            <input type="number" name="stock" min="0"
                   value="<%= isEdit ? producto.getStock() :"" %>" required>
        </p>
        <button type="submit">Guardar</button>
    </form>
    <p><a href="evento?action=listProduct">Regresar al listado</a></p>
</div>
</body>
</html>
