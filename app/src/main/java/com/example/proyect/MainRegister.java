package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainRegister extends AppCompatActivity {

    EditText task,dt;
    Button add;

    TextView back;

    DatabaseReference tareasRef;
    FirebaseAuth auth;
    FirebaseUser user;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        tareasRef = FirebaseDatabase.getInstance().getReference().child("tareas").child(user.getUid());
        task=findViewById(R.id.named);
        dt=findViewById(R.id.detaild);
        add=findViewById(R.id.edit);
        progressBar=findViewById(R.id.load);
        progressBar.setVisibility(View.GONE);
        back=findViewById(R.id.backpd);

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Maintareas.class);
                startActivity(intent);
                finish();

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addTarea();
                Toast.makeText(MainRegister.this, "Task added.",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(),Maintareas.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void addTarea() {
        String nombre,detalle;
        String currentDate = date.getCurrentDate();
        nombre=task.getText().toString();
        detalle=dt.getText().toString();
        Tarea tarea = new Tarea();
        tarea.setName(nombre);
        tarea.setDetalles(detalle);
        tarea.setDate(currentDate);
        tarea.setEstado(false);

        // Utiliza la referencia de la base de datos para agregar la tarea
        tareasRef.push().setValue(tarea);
    }


}

