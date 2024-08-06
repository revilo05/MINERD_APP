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

public class IniSESION extends AppCompatActivity {

    private EditText etCedula, etClave;
    private Button btnLoginSubmit;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ini_sesion);

        etCedula = findViewById(R.id.etCedulaLogin);
        etClave = findViewById(R.id.etClaveLogin);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        requestQueue = Volley.newRequestQueue(this);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String cedula = etCedula.getText().toString().trim();
        String clave = etClave.getText().toString().trim();

        if (cedula.isEmpty() || clave.isEmpty()) {
            Toast.makeText(IniSESION.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://adamix.net/minerd/def/iniciar_sesion.php?cedula=" + cedula + "&clave=" + clave;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean exito = response.getBoolean("exito");
                            if (exito) {
                                Intent intent = new Intent(IniSESION.this, menu.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(IniSESION.this, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IniSESION.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(IniSESION.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}