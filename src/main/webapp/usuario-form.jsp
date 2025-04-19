<%@ page import="com.inventario.empresa_catering.models.Usuario" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
</head>
<body>
<h2>Editar Usuario</h2>

<!-- Formulario para editar usuario -->
<form action="usuario" method="post">
    <input type="hidden" name="id_usuario" value="<%= request.getAttribute("usuario") != null ? ((Usuario) request.getAttribute("usuario")).getId_usuario() : "" %>" />

    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre"
           value="<%= request.getAttribute("usuario") != null ? ((Usuario) request.getAttribute("usuario")).getNombre() : "" %>" required /><br>

    <label for="apellidos">Apellidos:</label>
    <input type="text" id="apellidos" name="apellidos"
           value="<%= request.getAttribute("usuario") != null ? ((Usuario) request.getAttribute("usuario")).getApellidos() : "" %>" required /><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email"
           value="<%= request.getAttribute("usuario") != null ? ((Usuario) request.getAttribute("usuario")).getEmail() : "" %>" required /><br>

    <label for="password">Contraseña (deja en blanco si no deseas cambiarla):</label>
    <input type="password" id="password" name="password" /><br>

    <label for="rol">Rol:</label>
    <select name="rol" id="rol" required>
        <option value="administrador"
                <%= request.getAttribute("usuario") != null && "administrador".equals(((Usuario) request.getAttribute("usuario")).getRol()) ? "selected" : "" %>>Administrador</option>
        <option value="cliente"
                <%= request.getAttribute("usuario") != null && "cliente".equals(((Usuario) request.getAttribute("usuario")).getRol()) ? "selected" : "" %>>Cliente</option>
    </select><br>

    <button type="submit" name="action" value="save">Guardar Cambios</button>
</form>

<a href="usuario?action=list">Volver a la lista</a>
</body>
</html>