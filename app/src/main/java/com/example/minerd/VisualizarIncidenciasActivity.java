package com.example.minerd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VisualizarIncidenciasActivity extends AppCompatActivity {

    private static final String TAG = "VisIncidenciasAct";
    private static final String FILE_NAME = "incidencias.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_incidencias);

        ListView lvIncidencias = findViewById(R.id.lvIncidencias);

        List<Incidencia> incidencias = loadIncidenciasFromFile();

        if (incidencias.isEmpty()) {
            Toast.makeText(this, "No hay incidencias registradas", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<Incidencia> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, incidencias);
            lvIncidencias.setAdapter(adapter);

            lvIncidencias.setOnItemClickListener((parent, view, position, id) -> {
                Incidencia selectedIncidencia = incidencias.get(position);
                Intent intent = new Intent(VisualizarIncidenciasActivity.this, V_IncidenciaActivity.class);
                intent.putExtra("incidencia", selectedIncidencia);
                startActivity(intent);
            });
        }
    }

    private List<Incidencia> loadIncidenciasFromFile() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Incidencia>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e(TAG, "Error al leer las incidencias del archivo JSON", e);
            return new ArrayList<>();
        }
    }
}
