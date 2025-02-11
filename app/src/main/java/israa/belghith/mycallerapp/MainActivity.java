package israa.belghith.mycallerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //déclaration des  composantes
    Button btnval,btnqte;
    EditText ednom,edpsswd;
    CheckBox chkAdmin;
    SharedPreferences sharedPreferences;

    /*
    La méthode onCreate() est appelée lors de la création de l'activité.
     Elle initialise la vue en utilisant le fichier XML (activity_main) et lie les éléments UI aux variables définies précédemment.
    Elle initialise également un objet SharedPreferences pour stocker les informations de session de l'utilisateur.
     */
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

        chkAdmin = findViewById(R.id.chk_admin);

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        /*
        : Ici, la méthode vérifie si l'utilisateur est déjà connecté en utilisant les préférences partagées (SharedPreferences).
         Si l'utilisateur a été identifié comme un admin (isAdmin est true), il est redirigé vers la HomeActivity.
         Si l'utilisateur est déjà connecté, cette activité se termine immédiatement (finish()).
         */
        if (sharedPreferences.getBoolean("isAdmin", false)) {
            startActivity(new Intent(MainActivity.this, Home.class));
            finish();
        }

        /*
         Lorsque l'utilisateur clique sur le bouton "btnqte", cette méthode ferme l'activité en cours avec finish(),
          ce qui revient à quitter l'application ou revenir à l'écran précédent.
         */
        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();//close the activity
            }
        });

        /*
        Cette méthode est déclenchée lorsqu'on clique sur le bouton "btnval" pour valider l'authentification.
         Elle vérifie si le nom d'utilisateur et le mot de passe sont corrects (ici, l'utilisateur "isra" et le mot de passe "123").
         Si c'est le cas, l'état de connexion de l'utilisateur est sauvegardé dans les SharedPreferences,
          et l'utilisateur est redirigé vers la HomeActivity.
         Si les informations sont incorrectes, un message d'erreur est affiché avec Toast.
         */
        btnval.setOnClickListener(view -> {
            String nom = ednom.getText().toString();
            String psswd = edpsswd.getText().toString();
            boolean isAdmin = chkAdmin.isChecked();

            if (nom.equals("isra") && psswd.equals("123")) {
                // ✅ Save login state persistently
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);  // User is logged in
                editor.putBoolean("isAdmin", isAdmin);
                editor.apply();

                // ✅ Redirect to HomeActivity
                startActivity(new Intent(MainActivity.this, Home.class));
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Erreur de saisie", Toast.LENGTH_SHORT).show();
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

    /*
    Cette méthode gère la réponse à une demande de permission.
    Si la permission est accordée, un message de confirmation est affiché.
    Si elle est refusée, un message informe l'utilisateur de l'activer dans les paramètres.
     Elle est utilisée pour gérer des permissions spécifiques, comme celle d'effectuer un appel.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée ! Essayez d'appeler à nouveau.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission refusée. Activez-la dans les paramètres.", Toast.LENGTH_LONG).show();
            }
        }
    }


}