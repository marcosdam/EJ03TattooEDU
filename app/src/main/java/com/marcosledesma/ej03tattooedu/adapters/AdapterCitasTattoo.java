package com.marcosledesma.ej03tattooedu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.marcosledesma.ej03tattooedu.R;
import com.marcosledesma.ej03tattooedu.configuraciones.Configuraciones;
import com.marcosledesma.ej03tattooedu.modelos.CitasTattoo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterCitasTattoo extends ArrayAdapter<CitasTattoo> {
    // Creo las variables de los 3 parámetros
    private Context context;
    private int resource;
    private ArrayList<CitasTattoo> objects;
    private SimpleDateFormat simpleDateFormat;

    public AdapterCitasTattoo(@NonNull Context context, int resource, @NonNull ArrayList<CitasTattoo> objects) {
        super(context, resource, objects);
        // Asigno las variables
        this.context = context;
        this.resource = resource;
        this.objects = objects;

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    // Añadir método getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Eliminar return por defecto
        // Creamos view a partir del xml (LayoutInflater que obtengo del contexto, lo saco de la actividad que me llame)
        View fila = LayoutInflater.from(context).inflate(resource, null);
        CitasTattoo citasTattoo = objects.get(position);

        TextView txtNombre = fila.findViewById(R.id.txtNombreFila);
        TextView txtFechaCita = fila.findViewById(R.id.txtFechaFila);

        txtNombre.setText(citasTattoo.getNombre());
        // Cogemos simpleDateFormat de la clase Configuraciones
        txtFechaCita.setText(Configuraciones.simpleDateFormat.format(citasTattoo.getFechaCita()));

        return fila;
    }
}
