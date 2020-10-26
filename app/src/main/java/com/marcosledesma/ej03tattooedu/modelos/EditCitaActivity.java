package com.marcosledesma.ej03tattooedu.modelos;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marcosledesma.ej03tattooedu.NewCitaActivity;
import com.marcosledesma.ej03tattooedu.R;
import com.marcosledesma.ej03tattooedu.configuraciones.Configuraciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditCitaActivity extends AppCompatActivity {
    private EditText txtNombre, txtApellidos, txtFechaNacimiento, txtFechaCita, txtAdelanto;
    private Switch swColor, swAutorizado;
    private Button btnEliminar, btnGuardar;
    private CitasTattoo citasTattoo;
    private int posicion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_gestion_cita);

        citasTattoo = getIntent().getExtras().getParcelable("CITA");
        posicion = getIntent().getExtras().getInt("POS");

        inicializaInterfaz();
        rellenaInformacion();

        final LocalDate hoy = LocalDate.now();

        swAutorizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnGuardar.setEnabled(isChecked);
            }
        });

        txtFechaNacimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Date fecha = Configuraciones.simpleDateFormat.parse(s.toString());
                    LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (hoy.getYear() - fechaNacimiento.getYear() < 18) {
                        swAutorizado.setVisibility(View.VISIBLE);
                        btnGuardar.setEnabled(false);
                    } // else (comprobar mes etc)
                    else{
                        swAutorizado.setVisibility(View.GONE);
                        swAutorizado.setChecked(false);
                        btnGuardar.setEnabled(true);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

                    try {
                        citasTattoo.setFechaNacimiento(Configuraciones.simpleDateFormat.parse(txtFechaNacimiento.getText().toString()));
                        citasTattoo.setFechaCita(Configuraciones.simpleDateFormat.parse(txtFechaCita.getText().toString()));
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
                        bundle.putInt("POS", posicion);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();

                    } catch (ParseException e) {
                        Toast.makeText(EditCitaActivity.this, "Fecha incorrecta", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("POS", posicion);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void rellenaInformacion() {
        txtNombre.setText(citasTattoo.getNombre());
        txtApellidos.setText(citasTattoo.getApellido());
        txtFechaNacimiento.setText(Configuraciones.simpleDateFormat.format(citasTattoo.getFechaNacimiento()));
        txtFechaCita.setText(Configuraciones.simpleDateFormat.format(citasTattoo.getFechaCita()));
        txtAdelanto.setText(String.valueOf(citasTattoo.getAdelanto()));
        if (citasTattoo.isAutorizado()){
            swAutorizado.setVisibility(View.VISIBLE);
            swAutorizado.setChecked(citasTattoo.isAutorizado());
        }
        swColor.setChecked(citasTattoo.isColor());
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
        if (citasTattoo.isAutorizado()){
            swAutorizado.setVisibility(View.VISIBLE);
            swAutorizado.setSelected(citasTattoo.isAutorizado());
        }else {
            swAutorizado.setVisibility(View.GONE);
            swAutorizado.setSelected(false);
        }
    }
}
