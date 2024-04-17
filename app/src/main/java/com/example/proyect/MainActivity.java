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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText Email, Pass;
    Button Login;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    TextView registro;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            DatabaseReference rolesRef = FirebaseDatabase.getInstance().getReference().child("roles").child(currentUser.getUid());
            rolesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String rol = dataSnapshot.getValue(String.class);
                    if (rol != null) {
                        if (rol.equals("usuario")) {
                            Intent intent = new Intent(getApplicationContext(), Maintareas.class);
                            startActivity(intent);
                            finish();
                        } else if (rol.equals("admin")) {
                            // Aquí debes decidir a qué actividad quieres redirigir a los administradores
                            // Por ahora, redireccionaremos a null
                            Intent intent = new Intent(getApplicationContext(), null);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Si el rol es null, puede ser útil mostrar un mensaje de error o redirigir a la actividad de inicio de sesión
                        // Por ahora, redireccionaremos a la actividad de inicio de sesión
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        Email=findViewById(R.id.Email);
        Pass=findViewById(R.id.Pass);
        Login=findViewById(R.id.Login);
        registro=findViewById(R.id.Registro);
        progressBar=findViewById(R.id.carga);
        progressBar.setVisibility(View.GONE);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mainlogin.class);
                startActivity(intent);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = Email.getText().toString();
                password = Pass.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Ingresa un Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Ingresa una Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(MainActivity.this, "Ingreso concedido.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Maintareas.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(MainActivity.this, "Error en los datos.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
    }
}