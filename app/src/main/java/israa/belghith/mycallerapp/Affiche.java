package israa.belghith.mycallerapp;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Affiche extends AppCompatActivity {
    ListView lv;
    EditText edsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_affiche);

        lv=findViewById(R.id.lv_aff);
        edsearch=findViewById(R.id.edrech_aff);
        MyContactAdapter ad=new MyContactAdapter(Affiche.this,Home.data);
       /* ArrayAdapter ad =new ArrayAdapter(//adapter c pour la creation des view
                Affiche.this,//context
                android.R.layout.simple_list_item_1,//textview exist already : simple_list_item_1
                Home.data);*/
        lv.setAdapter(ad);

    }
}