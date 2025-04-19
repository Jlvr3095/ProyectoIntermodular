package com.inventario.empresa_catering.models;
import java.util.List;

public class Evento {
    private int id_evento;
    private String nombre_evento;
    private String fecha_evento;
    private String lugar_evento;
    private int id_usuario;
    private List<Producto> productos;

    public Evento() {
    }

    public Evento(int id_evento, String nombre_evento, String lugar_evento, String fecha_evento, int id_usuario, List<Producto> productos) {
        this.id_evento = id_evento;
        this.nombre_evento = nombre_evento;
        this.lugar_evento = lugar_evento;
        this.fecha_evento = fecha_evento;
        this.id_usuario = id_usuario;
        this.productos = productos;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre_evento() {
        return nombre_evento;
    }

    public void setNombre_evento(String nombre_evento) {
        this.nombre_evento = nombre_evento;
    }

    public String getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(String fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public String getLugar_evento() {
        return lugar_evento;
    }

    public void setLugar_evento(String lugar_evento) {
        this.lugar_evento = lugar_evento;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
