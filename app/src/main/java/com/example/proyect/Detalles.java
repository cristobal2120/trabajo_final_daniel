package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Detalles extends AppCompatActivity {

    EditText name, detail;

    TextView back;

    Button edit,delete;

    DatabaseReference tareasRef;

    FirebaseAuth auth;
    FirebaseUser user;

    Tarea tarea; // Variable para almacenar la tarea actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        edit = findViewById(R.id.edit);
        delete=findViewById(R.id.delete);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("tarea")) {
            tarea = (Tarea) intent.getSerializableExtra("tarea");

            String key = intent.getStringExtra("key");


            name = findViewById(R.id.named);
            detail = findViewById(R.id.detaild);

            name.setText(tarea.getName());
            detail.setText(tarea.getDetalles());

            back = findViewById(R.id.backpd);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), Maintareas.class);
                    startActivity(intent);
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarTarea();
                    Intent intent = new Intent(getApplicationContext(), Maintareas.class);
                    startActivity(intent);
                    finish();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actualizarDatosEnFirebase();
                    Intent intent = new Intent(getApplicationContext(), Maintareas.class);
                    startActivity(intent);
                    finish();
                }
            });


        }
    }

    private void actualizarDatosEnFirebase() {
        // Obtiene los nuevos datos de los EditText
        String nuevoNombre = name.getText().toString().trim();
        String nuevoDetalle = detail.getText().toString().trim();

        // Verifica si los datos han cambiado
        if (!nuevoNombre.equals(tarea.getName()) || !nuevoDetalle.equals(tarea.getDetalles())) {
            // Actualiza los datos en Firebase solo si hay cambios
            tarea.setName(nuevoNombre);
            tarea.setDetalles(nuevoDetalle);
            tarea.setDate(date.getCurrentDate());

            tareasRef = FirebaseDatabase.getInstance().getReference()
                    .child("tareas")
                    .child(user.getUid())
                    .child(tarea.getKey());

            tareasRef.setValue(tarea).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Detalles.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Detalles.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

        private void eliminarTarea () {
            // Asegúrate de tener la tarea actual
            if (tarea != null && tarea.getKey() != null) {
                DatabaseReference tareaRef = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("tareas")
                        .child(user.getUid())
                        .child(tarea.getKey());

                tareaRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Detalles.this, "Tarea eliminada correctamente", Toast.LENGTH_SHORT).show();
                            // Puedes cerrar la actividad u realizar otras acciones necesarias aquí
                            finish();
                        } else {
                            Toast.makeText(Detalles.this, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Log.e("Detalles", "La tarea o la clave (key) son nulas");
            }
        }
    }




