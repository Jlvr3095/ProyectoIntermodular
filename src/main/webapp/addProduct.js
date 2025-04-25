function mostrarDetallesProducto() {

    var select = document.getElementById("id_producto");
    var productoSeleccionado = select.options[select.selectedIndex];

    var descripcion = productoSeleccionado.getAttribute("data-descripcion");
    var precio = productoSeleccionado.getAttribute("data-precio");

    document.getElementById("descripcion").textContent = descripcion;
    document.getElementById("precio").textContent = precio + " € " }