package com.example.ruleta_recuperacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btt_inicio;
    private TextView txt_email, txt_contra,txt_aqui;
    private FirebaseAuth mAuth;
    Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btt_inicio=(Button) findViewById(R.id.btt_inicio);
        btt_inicio.setOnClickListener(this);

        txt_email=(TextView) findViewById(R.id.txt_emailr);
        txt_contra=(TextView) findViewById(R.id.txt_contrar);
        txt_aqui=(TextView) findViewById(R.id.txt_aqui);
        txt_aqui.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();
        this.window= getWindow();
    }

    //switch case para las acciones de los botones

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.txt_aqui:
                startActivity(new Intent(this, registro.class ));
                break;
            case R.id.btt_inicio:
                userLogin();

                break;

            case R.id.perfil:
                startActivity(new Intent(this, perfil.class));
        }
    }


    //metodo para comprobar si el formato de email y contrsaeña es correcto
    private void userLogin() {
        String email= txt_email.getText().toString().trim();
        String contra= txt_contra.getText().toString().trim();

        if(email.isEmpty()){
            txt_email.setError("Email incorrecto");
            txt_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            txt_email.setError("Introducir email");
            txt_email.requestFocus();
            return;
        }
        if(contra.isEmpty()){
            txt_contra.setError("Contraseña incorrecto");
            txt_contra.requestFocus();
            return;
        }

        if(contra.length()<6){

            txt_contra.setError("Contraseña incorrecta");
            txt_contra.requestFocus();
            return;
        }



        //comprobar credenciales con firestore
         mAuth.signInWithEmailAndPassword(email,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, ruleta.class));
                }else{
                    Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void salirApp(View view){

        finish();
    }

    public boolean onCreateOptionsMenu(Menu mimenu){
        //Menu es un import

        getMenuInflater().inflate(R.menu.menu, mimenu);
    // salir app finish();

        return true;
    }



    //cambiar de color a modo oscuro
    public boolean onOptionsItemSelected(MenuItem opcion_menu){
        int id= opcion_menu.getItemId();
        if(id==R.id.action_settings){
            salirApp(null);
        }
        if (id==R.id.color){

            String primaryDark="#212121";
            String primary="#194a8d";
            String background="#757575";

            cambiarcolor(primaryDark, primary, background);

        }

        if(id==R.id.perfil){
            mensaje();
        }


        return super.onOptionsItemSelected(opcion_menu);
    }


    private void cambiarcolor(String primaryDark, String primary, String background){

        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));

    }

    private void mensaje(){
        Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
    }

    
}