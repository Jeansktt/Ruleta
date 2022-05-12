package com.example.ruleta_recuperacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class perfil extends AppCompatActivity {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();

        TextView email_perfil= (TextView) findViewById(R.id.txt_email1);
        TextView nombre_perfil= (TextView) findViewById(R.id.txt_name1);
        TextView edad_perfil= (TextView) findViewById(R.id.txt_age1);
        TextView bienvenido_perfil= (TextView) findViewById(R.id.txt_bienvenido);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario userperfil= snapshot.getValue(usuario.class);
                if(userperfil != null){
                    String fullname= userperfil.nombre;
                    String correito= userperfil.email;
                    String age= userperfil.edad;

                     bienvenido_perfil.setText("Bienvenido, "+ fullname+ "!");
                     nombre_perfil.setText(fullname);
                     edad_perfil.setText(age);
                     email_perfil.setText(correito);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(perfil.this, "Error, algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}