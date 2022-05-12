package com.example.ruleta_recuperacion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tiradas extends AppCompatActivity {

    TextView txt_numeros_hot, txt_resultado;
    FirebaseFirestore mFirestore;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiradas);
        txt_numeros_hot= findViewById(R.id.txt_numeros_hot);
        txt_resultado= findViewById(R.id.txt_resultado);
        mFirestore= FirebaseFirestore.getInstance();
        this.window= getWindow();
        obtenerresultado();
        // llamara cuando este el firestore ehcho
    }

    //metodo para recuperar info de firestore

    private void obtenerresultado(){
        String id= "numeros_ruleta";

        mFirestore.collection("numeros1").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){

                    String resultado= documentSnapshot.getString("resultado");
                    txt_resultado.setText(resultado);
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
        return super.onOptionsItemSelected(opcion_menu);
    }

    private void cambiarcolor(String primaryDark, String primary, String background){

        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));

    }
}