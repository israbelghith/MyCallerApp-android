package israa.belghith.mycallerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    Button btnAjout,btnAffiche,btnLogout;
    public static ArrayList<Contact> data=new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        btnAjout=findViewById(R.id.btnajout_home);
        btnAffiche=findViewById(R.id.btnaff_home);
        btnLogout = findViewById(R.id.btn_logout);

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //passage vers home activity
                    Intent i =new Intent(Home.this,Ajout.class);
                    startActivity(i);
                   // finish();//appel ->onstop ->ondestroy


            }
        });
        btnAffiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passage vers home activity
                Intent i =new Intent(Home.this,Affiche.class);
                startActivity(i);
                // finish();//appel ->onstop ->ondestroy


            }
        });
        btnLogout.setOnClickListener(view -> {
            // ✅ Clear login state
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();  // Removes all stored values (log out user)
            editor.apply();

            // ✅ Redirect back to login screen
            startActivity(new Intent(Home.this, MainActivity.class));
            finish();
        });

    }
}