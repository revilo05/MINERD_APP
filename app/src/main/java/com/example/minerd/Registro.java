package com.example.minerd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

    private EditText etCedula, etNombre, etApellido, etClave, etCorreo, etTelefono, etFechaNacimiento;
    private Button btnRegisterSubmit;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etCedula = findViewById(R.id.etCedula);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etClave = findViewById(R.id.etClave);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        requestQueue = Volley.newRequestQueue(this);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String cedula = etCedula.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String clave = etClave.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String fechaNacimiento = etFechaNacimiento.getText().toString().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || clave.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaNacimiento.isEmpty()) {
            Toast.makeText(Registro.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://adamix.net/minerd/def/registro.php?cedula=" + cedula + "&nombre=" + nombre + "&apellido=" + apellido + "&clave=" + clave + "&correo=" + correo + "&telefono=" + telefono + "&fecha_nacimiento=" + fechaNacimiento;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean exito = response.getBoolean("exito");
                            if (exito) {
                                saveUserData(cedula, nombre, apellido, clave, correo, telefono, fechaNacimiento);
                                Intent intent = new Intent(Registro.this, menu.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Registro.this, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Registro.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Registro.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void saveUserData(String cedula, String nombre, String apellido, String clave, String correo, String telefono, String fechaNacimiento) {
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("cedula", cedula);
            userJson.put("nombre", nombre);
            userJson.put("apellido", apellido);
            userJson.put("clave", clave);
            userJson.put("correo", correo);
            userJson.put("telefono", telefono);
            userJson.put("fecha_nacimiento", fechaNacimiento);

            FileUtils.saveToFile(this, "user_data.json", userJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
