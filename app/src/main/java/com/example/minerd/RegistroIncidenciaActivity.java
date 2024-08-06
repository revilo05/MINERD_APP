package com.example.minerd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroIncidenciaActivity extends AppCompatActivity {

    private EditText etTitulo, etCentroEducativo, etRegional, etDistrito, etFecha, etDescripcion;
    private Button btnSeleccionarImagen;
    private String photoPath;
    private static final int PICK_IMAGE_REQUEST = 5;
    private static final String TAG = "RegIncidenciaAct";
    private static final String FILE_NAME = "incidencias.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_incidencia);

        etTitulo = findViewById(R.id.etTitulo);
        etCentroEducativo = findViewById(R.id.etCentroEducativo);
        etRegional = findViewById(R.id.etRegional);
        etDistrito = findViewById(R.id.etDistrito);
        etFecha = findViewById(R.id.etFecha);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);

        btnSeleccionarImagen.setOnClickListener(v -> openGallery());
        findViewById(R.id.btnGuardarIncidencia).setOnClickListener(v -> registrarIncidencia());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            photoPath = imageUri.toString();
            Toast.makeText(this, "Imagen seleccionada: " + photoPath, Toast.LENGTH_LONG).show();
            Log.d(TAG, "Imagen seleccionada: " + photoPath);
        } else {
            Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrarIncidencia() {
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo(etTitulo.getText().toString());
        incidencia.setCentroEducativo(etCentroEducativo.getText().toString());
        incidencia.setRegional(etRegional.getText().toString());
        incidencia.setDistrito(etDistrito.getText().toString());
        incidencia.setFecha(etFecha.getText().toString());
        incidencia.setDescripcion(etDescripcion.getText().toString());
        incidencia.setFoto(photoPath);

        Log.d(TAG, "Datos de incidencia: " + incidencia.toString());

        // Guardar incidencia en archivo JSON
        saveIncidenciaToFile(incidencia);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.registrarIncidencia(incidencia);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroIncidenciaActivity.this, "Incidencia registrada con éxito", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Respuesta del servidor: " + response.message());
                } else {
                    Toast.makeText(RegistroIncidenciaActivity.this, "Error al registrar la incidencia", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegistroIncidenciaActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error de red: " + t.getMessage(), t);
            }
        });
    }

    private void saveIncidenciaToFile(Incidencia incidencia) {
        List<Incidencia> incidencias = loadIncidenciasFromFile();
        incidencias.add(incidencia);

        Gson gson = new Gson();
        String jsonString = gson.toJson(incidencias);

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar la incidencia en el archivo JSON", e);
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
