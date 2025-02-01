package israa.belghith.mycallerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //déclaration des  composantes
    Button btnval,btnqte;
    EditText ednom,edpsswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mettre une interface xml sur l'écran R=ressources.layout.main
        MainActivity.this.setContentView(R.layout.activity_main);

//recuperation des composantes
        ednom=MainActivity.this.findViewById(R.id.ednom_auth);
        edpsswd=findViewById(R.id.edpsswd_auth);
        btnqte=findViewById(R.id.btnqte_auth);
        btnval=findViewById(R.id.btnval_auth);
        //Evenement

        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();//close the activity
            }
        });
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=ednom.getText().toString();
                String psswd=edpsswd.getText().toString();
                if(nom.equals("a") &&psswd.equals(""))
                {
                 //passage vers home activity
                    Intent i =new Intent(MainActivity.this,Home.class);
                    startActivity(i);
                    finish();//appel ->onstop ->ondestroy
                }
                else {
                    //err msg
                    Toast.makeText(MainActivity.this, "err de saisie", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}