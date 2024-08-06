package com.example.minerd;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class V_IncidenciaActivity extends AppCompatActivity {

    private TextView tvTitulo, tvCentroEducativo, tvRegional, tvDistrito, tvFecha, tvDescripcion;
    private ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincidencia);

        tvTitulo = findViewById(R.id.tvTitulo);
        tvCentroEducativo = findViewById(R.id.tvCentroEducativo);
        tvRegional = findViewById(R.id.tvRegional);
        tvDistrito = findViewById(R.id.tvDistrito);
        tvFecha = findViewById(R.id.tvFecha);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        ivFoto = findViewById(R.id.ivFoto);

        Incidencia incidencia = (Incidencia) getIntent().getSerializableExtra("incidencia");
        if (incidencia != null) {
            tvTitulo.setText( "Titulo: " + incidencia.getTitulo());
            tvCentroEducativo.setText( "Centro Educativo: "+incidencia.getCentroEducativo());
            tvRegional.setText("Regional: " +incidencia.getRegional());
            tvDistrito.setText( "Distrito: " +incidencia.getDistrito());
            tvFecha.setText("Fecha: " + incidencia.getFecha());
            tvDescripcion.setText("Descripcion: "+ incidencia.getDescripcion());

            if (incidencia.getFoto() != null && !incidencia.getFoto().isEmpty()) {
                ivFoto.setImageURI(Uri.parse(incidencia.getFoto()));
            }
        }
    }
}
