package com.example.minerd;

public class UserDatos {

    DatosClass datos;

    public DatosClass getDatos() {
        return datos;
    }

    public void setDatos(DatosClass datos) {
        this.datos = datos;
    }

    class DatosClass{
        String nombre, apellido, correo;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }
    }
}
