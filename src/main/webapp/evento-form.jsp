<%@ page import="com.inventario.empresa_catering.models.Evento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Evento evento = (Evento) request.getAttribute("evento");
    boolean isEdit = (evento != null); // Si no es nulo, es edición
%>

<html>
<head>
    <title><%= isEdit ? "Editar evento" : "Nuevo evento" %></title>
    <link rel="stylesheet" href="NewEditEvento.css">
</head>
<body>
<div class="container">
    <h1><%= isEdit ? "Editar evento" : "Nuevo evento" %></h1>

    <form action="evento" method="post">
        <input type="hidden" name="action" value="save" />

        <% if (isEdit) { %>
        <input type="hidden" name="id_evento" value="<%= evento.getId_evento() %>" />
        <% } %>

        <p>
            <label>Nombre del evento</label><br />
            <input type="text" name="nombre_evento"
                   value="<%= isEdit ? evento.getNombre_evento() : "" %>" required />
        </p>
        <p>
            <label>Fecha del evento</label><br />
            <input type="date" name="fecha_evento"
                value=" <%= isEdit ? evento.getFecha_evento() : "" %>" required />
        </p>
        <p>
            <label>Lugar del evento</label><br/>
            <select name="lugar_evento" required>
                <option value="Finca Los Aromas"
                        <%= (isEdit && "Finca Los Aromas".equals(evento.getLugar_evento())) ? "selected" : "" %>>
                    Finca Los Aromas
                </option>
                <option value="Finca Los Pardos"
                        <%= (isEdit && "Finca Los Pardos".equals(evento.getLugar_evento())) ? "selected" : "" %>>
                    Finca Los Pardos
                </option>
            </select>
        </p>
        <input type="hidden" name="id_usuario" value="${evento.id_usuario}" />

        <button type="submit">Guardar</button>
    </form>
    <p><a href="evento?action=listCliente">Regresar a tus eventos</a></p>
</div>
</body>
</html>
