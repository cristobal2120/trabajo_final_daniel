package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maintareas extends AppCompatActivity {

    DatabaseReference tareasRef;

    TextView salir;

    Button add;
    FirebaseAuth auth;
    FirebaseUser user;

    RecyclerView recyclerView;
    Tadapter tareaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintareas);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        salir=findViewById(R.id.Salir);
        add=findViewById(R.id.Add);

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            DatabaseReference rolesRef = FirebaseDatabase.getInstance().getReference().child("roles").child(user.getUid());
            rolesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String rol = dataSnapshot.getValue(String.class);
                    if (rol == null || !rol.equals("usuario")) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores de lectura de la base de datos
                }
            });
        }

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainRegister.class);
                startActivity(intent);
                finish();
            }
        });

        tareasRef = FirebaseDatabase.getInstance().getReference().child("tareas").child(user.getUid());

        recyclerView = findViewById(R.id.recyclerView);

        tareaAdapter = new Tadapter(this, new ArrayList<>());
        recyclerView.setAdapter(tareaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        cargarTareas();

        tareaAdapter.setOnItemClickListener(new Tadapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tarea tarea) {
                // Agrega un log para verificar que el clic en la CardView funciona
                Log.d("Maintareas", "Clic en CardView: " + tarea.getName());

                // Agrega el código para abrir la actividad DetallesTareaActivity
                Intent intent = new Intent(getApplicationContext(), Detalles.class);
                intent.putExtra("tarea", tarea);
                intent.putExtra("key", tarea.getKey());
                startActivity(intent);
            }
        });
    }

    private void cargarTareas() {
        tareasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Tarea> tareas = new ArrayList<>();
                for (DataSnapshot tareaSnapshot : dataSnapshot.getChildren()) {
                    Tarea tarea = tareaSnapshot.getValue(Tarea.class);
                    tarea.setKey(tareaSnapshot.getKey());
                    tareas.add(tarea);
                }
                Collections.reverse(tareas);
                tareaAdapter.setTareas(tareas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}