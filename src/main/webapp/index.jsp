<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>INICIO DE SESION</title>
    <link rel="stylesheet" type="text/css" href="login.css">
</head>

<body>

<div class="login-container">
    <h1>Catering Sabor & Estilo</h1>
    <%
        String MensajeError = (String) request.getAttribute("MensajeError");
        if (MensajeError != null) {
    %>
        <%= MensajeError %>
    <%
        }
    %>
    <form method="post" action="login">
    <input type="email" placeholder="Correo" name="email" required>
    <input type="password" placeholder="Contraseña" minlength="4" name="password" required>
    <input type="submit" value="Iniciar Sesion">
</form>
<p class="registro"> ¿No tienes cuenta? <a href="RegistroEdicion.jsp">Regístrate aquí</a></p>
</div>
</body>
</html>