package com.example.ruleta_recuperacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ruleta extends AppCompatActivity {

    Button btt_girar, btt_verNum;
    TextView resultadoTv;
    ImageView rule;
    Window window;


    Random r;

   FirebaseFirestore db= FirebaseFirestore.getInstance();

    int degree_old = 0, degree = 0;
    //hay 37 sectores en la rule (de 9,72 grados cada una)
    private static final float FACTOR = 4.86f;
    //4.86 es 9.72/2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);
        btt_girar = (Button) findViewById(R.id.btt_girar);
        resultadoTv = (TextView) findViewById(R.id.resultadoTv);
        rule = (ImageView) findViewById(R.id.rule);
        btt_verNum=(Button) findViewById(R.id.btt_verNum);

        //firebase
         db = FirebaseFirestore.getInstance();

        r = new Random();

        this.window= getWindow();

        btt_verNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ruleta.this, tiradas.class);
                startActivity(intent);
            }
        });

        btt_girar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                degree_old = degree % 360;
                degree = r.nextInt(360) + 360;
                RotateAnimation rotate = new RotateAnimation(degree_old, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3000);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        resultadoTv.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        resultadoTv.setText(currentNumber(360-(degree % 360)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                rule.startAnimation(rotate);

                String resultado = resultadoTv.getText().toString().trim();
                // llamar cuando este firebase
                UploadData(resultado);


            }
        });
    }

    //llamar cuando este firebase, datos a guardar en la bbdd
   private void UploadData(String resultado) {
        String id="numeros_ruleta";

        Map<String, Object> doc= new HashMap<>();
        doc.put("id", id); //id of data
        doc.put("resultado", resultado);

       db.collection("numeros1").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ruleta.this,"Guardando número...",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ruleta.this, "Error al subir info",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private String currentNumber (int degrees){

        String text = "";
        //hacer esto por cada numero
        if(degrees >=(FACTOR * 1) && degrees < (FACTOR * 3)){
            text= "32 rojo";
        }
        if(degrees >=(FACTOR * 3) && degrees < (FACTOR * 5)){
            text= "15 negro";
        }
        if(degrees >=(FACTOR * 5) && degrees < (FACTOR * 7)){
            text= "19 rojo";
        }
        if(degrees >=(FACTOR * 7) && degrees < (FACTOR * 9)){
            text= "4 negro";
        }
        if(degrees >=(FACTOR * 9) && degrees < (FACTOR * 11)){
            text= "21 rojo";
        }
        if(degrees >=(FACTOR * 11) && degrees < (FACTOR * 13)){
            text= "2 negro";
        }
        if(degrees >=(FACTOR * 13) && degrees < (FACTOR * 15)){
            text= "25 rojo";
        }
        if(degrees >=(FACTOR * 15) && degrees < (FACTOR * 17)){
            text= "17 negro";
        }
        if(degrees >=(FACTOR * 17) && degrees < (FACTOR * 19)){
            text= "34 rojo";
        }
        if(degrees >=(FACTOR * 19) && degrees < (FACTOR * 21)){
            text= "6 negro";
        }
        if(degrees >=(FACTOR * 21) && degrees < (FACTOR * 23)){
            text= "27 rojo";
        }
        if(degrees >=(FACTOR * 23) && degrees < (FACTOR * 25)){
            text= "13 negro";
        }
        if(degrees >=(FACTOR * 25) && degrees < (FACTOR * 27)){
            text= "36 rojo";
        }
        if(degrees >=(FACTOR * 27) && degrees < (FACTOR * 29)){
            text= "11 negro";
        }
        if(degrees >=(FACTOR * 29) && degrees < (FACTOR * 31)){
            text= "30 rojo";
        }
        if(degrees >=(FACTOR * 31) && degrees < (FACTOR * 33)){
            text= "8 negro";
        }
        if(degrees >=(FACTOR * 33) && degrees < (FACTOR * 35)){
            text= "23 rojo";
        }
        if(degrees >=(FACTOR * 35) && degrees < (FACTOR * 37)){
            text= "10 negro";
        }
        if(degrees >=(FACTOR * 37) && degrees < (FACTOR * 39)){
            text= "5 rojo";
        }
        if(degrees >=(FACTOR * 39) && degrees < (FACTOR * 41)){
            text= "24 negro";
        }
        if(degrees >=(FACTOR * 41) && degrees < (FACTOR * 43)){
            text= "16 rojo";
        }
        if(degrees >=(FACTOR * 43) && degrees < (FACTOR * 45)){
            text= "33 negro";
        }
        if(degrees >=(FACTOR * 45) && degrees < (FACTOR * 47)){
            text= "1 rojo";
        }
        if(degrees >=(FACTOR * 47) && degrees < (FACTOR * 49)){
            text= "20 negro";
        }
        if(degrees >=(FACTOR * 49) && degrees < (FACTOR * 51)){
            text= "14 rojo";
        }
        if(degrees >=(FACTOR * 51) && degrees < (FACTOR * 53)){
            text= "31 negro";
        }
        if(degrees >=(FACTOR * 53) && degrees < (FACTOR * 55)){
            text= "9 rojo";
        }
        if(degrees >=(FACTOR * 55) && degrees < (FACTOR * 57)){
            text= "22 negro";
        }
        if(degrees >=(FACTOR * 57) && degrees < (FACTOR * 59)){
            text= "18 rojo";
        }
        if(degrees >=(FACTOR * 59) && degrees < (FACTOR * 61)){
            text= "29 negro";
        }
        if(degrees >=(FACTOR * 61) && degrees < (FACTOR * 63)){
            text= "7 rojo";
        }
        if(degrees >=(FACTOR * 63) && degrees < (FACTOR * 65)){
            text= "28 negro";
        }
        if(degrees >=(FACTOR * 65) && degrees < (FACTOR * 67)){
            text= "12 rojo";
        }
        if(degrees >=(FACTOR * 67) && degrees < (FACTOR * 69)){
            text= "35 negro";
        }
        if(degrees >=(FACTOR * 69) && degrees < (FACTOR * 71)){
            text= "3 rojo";
        }
        if(degrees >=(FACTOR * 71) && degrees < (FACTOR * 73)){
            text= "26 negro";
        }
        if((degrees >=(FACTOR * 73) && degrees < 360) || (degrees >= 0 && degrees < (FACTOR * 1))){
            text= "0";
        }


        return  text;

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

        if(id==R.id.perfil){
            perfil_next(null);

        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    private void cambiarcolor(String primaryDark, String primary, String background){

        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));

    }
    public void perfil_next(View view){
        Intent perfil_nex= new Intent(this, perfil.class);
        startActivity(perfil_nex);
    }
}