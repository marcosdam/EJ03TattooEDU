package com.marcosledesma.ej03tattooedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.marcosledesma.ej03tattooedu.modelos.CitasTattoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewCitaActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtFechaNacimiento, txtFechaCita, txtAdelanto;
    private Switch swColor, swAutorizado;
    private Button btnEliminar, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cita);

        inicializaInterfaz();

        txtFechaNacimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LocalDate hoy = LocalDate.now();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date fecha = simpleDateFormat.parse(s.toString());
                    LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (hoy.getYear() - fechaNacimiento.getYear() < 18) {
                        swAutorizado.setVisibility(View.VISIBLE);
                        swAutorizado.setChecked(true);
                    } // else (comprobar mes etc)
                    else{
                        swAutorizado.setVisibility(View.GONE);
                        swAutorizado.setChecked(false);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        swAutorizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnGuardar.setEnabled(isChecked);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtNombre.getText().toString().isEmpty() &&
                    !txtFechaNacimiento.getText().toString().isEmpty() &&
                    !txtFechaCita.getText().toString().isEmpty()){
                    CitasTattoo citasTattoo = new CitasTattoo();
                    citasTattoo.setNombre(txtNombre.getText().toString());
                    citasTattoo.setApellido(txtApellidos.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        citasTattoo.setFechaNacimiento(sdf.parse(txtFechaNacimiento.getText().toString()));
                        citasTattoo.setFechaCita(sdf.parse(txtFechaCita.getText().toString()));
                        if (txtAdelanto.getText().toString().isEmpty())
                            citasTattoo.setAdelanto(0);
                        else
                            citasTattoo.setAdelanto(Float.parseFloat(txtAdelanto.getText().toString()));
                        citasTattoo.setColor(swColor.isChecked());
                        citasTattoo.setAutorizado(swAutorizado.isChecked());

                        // Ya lo tengo, me lo llevo al Main en el Intent
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("CITA", citasTattoo);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();

                    } catch (ParseException e) {
                        Toast.makeText(NewCitaActivity.this, "Fecha incorrecta", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private void inicializaInterfaz() {
        txtNombre = findViewById(R.id.txtNombreCita);
        txtApellidos = findViewById(R.id.txtApellidosCita);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimientoCita);
        txtFechaCita = findViewById(R.id.txtFechaCitaCita);
        txtAdelanto = findViewById(R.id.txtAdelantoCita);
        swColor = findViewById(R.id.swColorCita);
        swAutorizado = findViewById(R.id.swAutorizadoCita);
        btnEliminar = findViewById(R.id.btnEliminarCita);
        btnGuardar = findViewById(R.id.btnGuardarCita);
        // Ocultamos el btnEliminar y el swAutorizado
        btnEliminar.setVisibility(View.GONE);
        swAutorizado.setVisibility(View.GONE);
    }
}