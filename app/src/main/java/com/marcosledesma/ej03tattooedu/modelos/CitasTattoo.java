package com.marcosledesma.ej03tattooedu.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CitasTattoo implements Parcelable {
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private Date fechaCita;
    private float adelanto;
    private boolean color;
    private boolean autorizado = false;

    public CitasTattoo(String nombre, String apellido, Date fechaNacimiento, Date fechaCita, float adelanto, boolean color) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCita = fechaCita;
        this.adelanto = adelanto;
        this.color = color;
    }

    public CitasTattoo() {
    }

    protected CitasTattoo(Parcel in) {
        nombre = in.readString();
        apellido = in.readString();
        adelanto = in.readFloat();
        color = in.readByte() != 0;
        autorizado = in.readByte() != 0;
        fechaCita = new Date(in.readLong());
        fechaNacimiento = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeFloat(adelanto);
        dest.writeByte((byte) (color ? 1 : 0));
        dest.writeByte((byte) (autorizado ? 1 : 0));
        dest.writeLong(fechaCita.getTime());
        dest.writeLong(fechaNacimiento.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CitasTattoo> CREATOR = new Creator<CitasTattoo>() {
        @Override
        public CitasTattoo createFromParcel(Parcel in) {
            return new CitasTattoo(in);
        }

        @Override
        public CitasTattoo[] newArray(int size) {
            return new CitasTattoo[size];
        }
    };

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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public float getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(float adelanto) {
        this.adelanto = adelanto;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
}
