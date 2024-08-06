package com.example.minerd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class AcercaDeActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private TextView tvHoroscope;

    TextView textView, textView2, textView3, texto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        textView = findViewById(R.id.textV1);
        textView2 = findViewById(R.id.textV2);
        textView3 = findViewById(R.id.textV3);
        texto = findViewById(R.id.texto);

        datePicker = findViewById(R.id.datePicker);
        tvHoroscope = findViewById(R.id.tvHoroscope);
        Button btnShowHoroscope = findViewById(R.id.btnShowHoroscope);

        btnShowHoroscope.setOnClickListener(view -> showHoroscope());

        loadUserData();
    }

    private void loadUserData() {
        JSONObject userData = FileUtils.readFromFile(this, "user_data.json");
        if (userData != null) {
            try {
                String nombre = userData.getString("nombre");
                String apellido = userData.getString("apellido");
                String correo = userData.getString("correo");
                String fechaNacimiento = userData.getString("fecha_nacimiento");

                textView.setText("Nombre: " + nombre);
                textView2.setText("Apellido: " + apellido);
                textView3.setText("Correo: " + correo);

                String[] fechaParts = fechaNacimiento.split("-");
                int year = Integer.parseInt(fechaParts[0]);
                int month = Integer.parseInt(fechaParts[1]) - 1; // Month is 0-based
                int day = Integer.parseInt(fechaParts[2]);

                datePicker.updateDate(year, month, day);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showHoroscope() {
        int month = datePicker.getMonth() + 1;

        String horoscope;
        if (month >= 1 && month <= 4) {
            horoscope = "Hoy es un buen día para ti!";
        } else if (month >= 5 && month <= 8) {
            horoscope = "Hoy será un día regular.";
        } else {
            horoscope = "Hoy es un buen día para ti!";
        }

        tvHoroscope.setText(horoscope);
    }
}
