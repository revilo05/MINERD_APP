package com.example.minerd;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class menu extends AppCompatActivity {

    private Button btnRegistrarIncidencia;
    private Button btnVisualizarIncidencia;
    private Button btnAcercaDe;
    private Button btnEliminarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnRegistrarIncidencia = findViewById(R.id.Registrar);
        btnVisualizarIncidencia = findViewById(R.id.Visualizar);
        btnAcercaDe = findViewById(R.id.AcercaDe);
        btnEliminarDatos = findViewById(R.id.btnEliminarDatos);

        btnRegistrarIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, RegistroIncidenciaActivity.class);
                startActivity(intent);
            }
        });

        btnVisualizarIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, VisualizarIncidenciasActivity.class);
                startActivity(intent);
            }
        });

        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, AcercaDeActivity.class);
                startActivity(intent);
            }
        });

        btnEliminarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres eliminar todos los archivos JSON?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteLocalJsonFiles();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteLocalJsonFiles() {
        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (directory != null && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                boolean filesDeleted = false; // Para verificar si al menos un archivo fue eliminado
                for (File file : files) {
                    if (file.getName().endsWith(".json")) {
                        if (file.delete()) {
                            filesDeleted = true; // Al menos un archivo fue eliminado
                        } else {
                            Toast.makeText(this, "Error al eliminar el archivo: " + file.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (filesDeleted) {
                    Toast.makeText(this, "Archivos JSON eliminados exitosamente.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No se encontraron archivos JSON para eliminar.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error al acceder al directorio de documentos.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Directorio de documentos no encontrado.", Toast.LENGTH_SHORT).show();
        }
    }
}
