package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.SplittableRandom;

public class Mainlogin extends AppCompatActivity {

    EditText Email, Pass;
    Button Registrar;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    TextView volver;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),Maintareas.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);
        mAuth=FirebaseAuth.getInstance();
        Email=findViewById(R.id.EmailR);
        Pass=findViewById(R.id.PassR);
        Registrar=findViewById(R.id.Register);
        volver=findViewById(R.id.back);
        progressBar=findViewById(R.id.carga);
        progressBar.setVisibility(View.GONE);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email=Email.getText().toString();
                password=Pass.getText().toString();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Mainlogin.this, "Ingresa un Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Mainlogin.this, "Ingresa una Contraseña",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Mainlogin.this, "Cuenta Creada.",
                                            Toast.LENGTH_SHORT).show();
                                    String userID = mAuth.getCurrentUser().getUid(); // Obtiene el UID del usuario registrado
                                    asignarRolUsuario(userID);
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Mainlogin.this, "Error.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void asignarRolUsuario(String userID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("roles").child(userID);
        databaseReference.setValue("usuario").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // El rol se asignó correctamente
                } else {
                    // Error al asignar el rol
                }
            }
        });
    }
}