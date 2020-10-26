package com.marcosledesma.ej03tattooedu;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.marcosledesma.ej03tattooedu.adapters.AdapterCitasTattoo;
import com.marcosledesma.ej03tattooedu.modelos.CitasTattoo;
import com.marcosledesma.ej03tattooedu.modelos.EditCitaActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int EDIT_CITA = 100;
    private final int ADD_CITA = 77;
    // Modelo de datos
    private ArrayList<CitasTattoo> listaCitas;
    // Contenedor - Adapter asociado
    private ListView contenedorCitas;
    private AdapterCitasTattoo adapterCitasTattoo;
    // Fila o elemento que se repite
    private int filaCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Inicializar elementos
        listaCitas = new ArrayList<>();
        contenedorCitas = findViewById(R.id.contenedorCitas);
        filaCita = R.layout.fila_cita_tattoo;
        adapterCitasTattoo = new AdapterCitasTattoo(this, filaCita, listaCitas);
        contenedorCitas.setAdapter(adapterCitasTattoo);

        contenedorCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("POS", position);
                CitasTattoo citasTattoo = listaCitas.get(position);
                bundle.putParcelable("CITA", citasTattoo);
                Intent intent = new Intent(MainActivity.this, EditCitaActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_CITA);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewCitaActivity.class), ADD_CITA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CITA && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                CitasTattoo citasTattoo = data.getExtras().getParcelable("CITA");
                listaCitas.add(citasTattoo);
                adapterCitasTattoo.notifyDataSetChanged();
            }
        }
        if (requestCode == EDIT_CITA && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                int posicion = data.getExtras().getInt("POS");
                CitasTattoo citasTattoo = data.getExtras().getParcelable("CITA");
                if (citasTattoo == null){
                    listaCitas.remove(posicion);
                }else{
                    listaCitas.set(posicion, citasTattoo);
                }
                adapterCitasTattoo.notifyDataSetChanged();
            }
        }
    }
}