
<%@ page import="com.inventario.empresa_catering.models.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
  Usuario usuario = (Usuario) request.getAttribute("usuario");
  boolean isEdit = (usuario != null);
%>

<html>
<head>
  <title><%= isEdit ? "Editar Usuario" : "Nuevo Usuario" %></title>
  <link rel="stylesheet" href="registro.css">
</head>
<body>
<div class="container">
  <h1><%= isEdit ? "Editar Usuario" : "Nuevo Usuario" %></h1>

  <form action="usuario" method="post">
    <input type="hidden" name="action" value="save" />

    <% if (isEdit) { %>
    <input type="hidden" name="id_usuario" value="<%= usuario.getId_usuario() %>" />
    <% } %>

    <p>
      <label>Nombre</label><br />
      <input type="text" name="nombre"
             value="<%= isEdit ? usuario.getNombre(): "" %>" required />
    </p>

    <p>
      <label>Apellidos</label><br />
      <input type="text" name="apellidos"
                value="<%= isEdit ? usuario.getApellidos(): "" %>" required />
    </p>
    <p>
      <label>Correo electronico</label><br />
      <input type="email" name="email"
                value="<%= isEdit ? usuario.getEmail(): "" %>" required />
    </p>
    <p>
      <label>Contraseña</label><br />
      <input type="password" name="password" minlength="7"
                value="<%= isEdit ? usuario.getPassword(): "" %>" required />
    </p>
    <button type="submit">Guardar</button>
  </form>
</div>
</body>
</html>