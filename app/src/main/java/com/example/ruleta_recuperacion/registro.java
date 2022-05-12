package com.example.ruleta_recuperacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class registro extends AppCompatActivity implements View.OnClickListener {

    private EditText txt_nombre, txt_edad, txt_emailr, txt_contraR, txt_repetir;
    private Button btt_registrarR;
    private TextView txt_volver;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btt_registrarR= (Button) findViewById(R.id.btt_registrar);
        btt_registrarR.setOnClickListener(this);

        txt_nombre= (EditText) findViewById(R.id.txt_nombre);
        txt_edad= (EditText) findViewById(R.id.txt_edad);
        txt_emailr= (EditText) findViewById(R.id.txt_emailr);
        txt_contraR= (EditText) findViewById(R.id.txt_contrar);
        txt_volver= (TextView) findViewById(R.id.txt_volver);
        txt_volver.setOnClickListener(this);
        txt_repetir=(EditText) findViewById(R.id.txt_repetir_contra);
        mAuth= FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btt_registrar:
                registrar_usuario();
                break;
            case R.id.txt_volver:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

    private void registrar_usuario() {

        String email= txt_emailr.getText().toString().trim();
        String contra= txt_contraR.getText().toString().trim();
        String nombre= txt_nombre.getText().toString().trim();
        String edad= txt_edad.getText().toString().trim();
        String repetir= txt_repetir.getText().toString().trim();

        if(nombre.isEmpty()){
            txt_nombre.setError("Campo nombre vacío");
            txt_nombre.requestFocus();
            return;

        }

        if(edad.isEmpty()){
            txt_edad.setError("Campo edad vacío");
            txt_edad.requestFocus();
            return;
        }
        if(email.isEmpty()){
            txt_emailr.setError("Campo email vacío");
            txt_emailr.requestFocus();
            return;
        }
        //comprobar email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txt_emailr.setError("Email invalido");
            txt_emailr.requestFocus();
            return;
        }

        if(contra.isEmpty()){
            txt_contraR.setError("Campo Password vacío");
            txt_contraR.requestFocus();
            return;
        }

        if(contra.length()<6){

            txt_contraR.setError("Minimo debe contener 6 caracteres");
            txt_contraR.requestFocus();
            return;
        }

        if(repetir.isEmpty()){
            txt_repetir.setError("Campo vacío");
            txt_repetir.requestFocus();
            return;
        }

        if(repetir.equals(contra)){

        }else{
            txt_repetir.setError("Intentalo de nuevo");
            txt_repetir.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            usuario user= new usuario(nombre, edad, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(registro.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                                    }else{

                                        Toast.makeText(registro.this, "Registro fallido, Pruebe de nuevo", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });

                        }else{

                            Toast.makeText(registro.this, "Registro fallido, Pruebe de nuevo", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}