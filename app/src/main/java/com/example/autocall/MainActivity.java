package com.example.autocall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText etNombre = findViewById(R.id.nom);
        EditText etCorreo = findViewById(R.id.correo);
        EditText etMensaje = findViewById(R.id.etMensaje);
        Button button = findViewById(R.id.button);
        Spinner spinnerPrefijos = findViewById(R.id.spinner);
        CheckBox checkBox = findViewById(R.id.checkBox);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.opciones_spinner,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrefijos.setAdapter(adapter);

        button.setOnClickListener( v ->{
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String mensaje = etMensaje.getText().toString().trim();

            String tipoConsulta = spinnerPrefijos.getSelectedItem().toString();

            if (nombre.isEmpty() || correo.isEmpty() || mensaje.isEmpty()) {
                Toast.makeText(
                        this,
                        "Rellena todos los campos",
                        Toast.LENGTH_SHORT).show();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(
                        this,
                        "Introduce un correo válido",
                        Toast.LENGTH_SHORT).show();

            }
            else {
                String asunto = "Nueva consulta: " + tipoConsulta;

                String cuerpo = "Nombre: " + nombre +
                        "\nCorreo de contacto: " + correo +
                        "\nTipo de consulta: " + tipoConsulta +
                        "\n\nMensaje:\n" + mensaje;

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"destinatario@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                intent.putExtra(Intent.EXTRA_TEXT, cuerpo);

                if (checkBox.isChecked()) {
                    intent.putExtra(Intent.EXTRA_CC,
                            new String[]{correo});
                }
                startActivity(Intent.createChooser(intent, "Enviar correo"));
            }
        });

    }
}