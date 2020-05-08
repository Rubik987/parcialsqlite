package com.dorellano.parcialsqlite.helpers;

public class person {
    private int id;
    private String nombre;
    private String Cedula;
    private String Salario;
    private String Estrato;
    private String nivelEdu;

    public person(int id, String nombre, String cedula, String salario, String estrato, String nivelEdu) {
        this.id=id;
        this.nombre = nombre;
        Cedula = cedula;
        Salario = salario;
        Estrato = estrato;
        this.nivelEdu = nivelEdu;
    }

    public String getCedula() {
        return Cedula;
    }

    public String getSalario() {
        return Salario;
    }

    public String getEstrato() {
        return Estrato;
    }

    public String getNivelEdu() {
        return nivelEdu;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "person{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", Cedula='" + Cedula + '\'' +
                ", Salario='" + Salario + '\'' +
                ", Estrato='" + Estrato + '\'' +
                ", nivelEdu='" + nivelEdu + '\'' +
                '}';
    }
}
