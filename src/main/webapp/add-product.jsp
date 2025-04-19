<%--<%@ page import="com.inventario.empresa_catering.models.Producto" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Añadir Producto al Evento</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Añadir Productos a Evento</h1>--%>

<%--<form method="post" action="add-product">--%>
<%--    &lt;%&ndash;@declare id="id_producto"&ndash;%&gt;<input type="hidden" name="action" value="add" />--%>
<%--    <input type="hidden" name="id_evento" value="${requestScope.id_evento}" />--%>

<%--    <label for="id_producto">Selecciona Producto:</label>--%>
<%--    <select name="id_producto">--%>
<%--        <%--%>
<%--            List<Producto> productos = (List<Producto>) request.getAttribute("productos");--%>
<%--            if (productos != null) {--%>
<%--                for (Producto producto : productos) {--%>
<%--                 --%>
<%--        %>--%>
<%--        <option value="<%= producto.getId_producto() %>"><%= producto.getNombre_producto() %></option>--%>
<%--        <% } } %>--%>
<%--    </select>--%>

<%--    <button type="submit">Añadir Producto</button>--%>
<%--</form>--%>

<%--<a href="evento?action=listCliente">Volver a los Eventos</a>--%>
<%--</body>--%>
<%--</html>--%>
<%@ page import="com.inventario.empresa_catering.models.Producto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Añadir Producto al Evento</title>
    <link rel="stylesheet" href="addProduct.css">
    <script type="text/javascript">
        // Función que se ejecuta al seleccionar un producto
        function mostrarDetallesProducto() {
            // Obtener el producto seleccionado
            var select = document.getElementById("id_producto");
            var productoSeleccionado = select.options[select.selectedIndex];

            // Obtener la descripción y el precio desde los atributos 'data'
            var descripcion = productoSeleccionado.getAttribute("data-descripcion");
            var precio = productoSeleccionado.getAttribute("data-precio");

            // Mostrar la descripción y el precio en los elementos correspondientes
            document.getElementById("descripcion").textContent = descripcion;
            document.getElementById("precio").textContent = precio + " € " ;
        }
    </script>
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