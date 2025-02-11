package israa.belghith.mycallerapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Affiche extends AppCompatActivity {
    RecyclerView rv;
    EditText edsearch;
    SearchView searchview;
    Context con;
    MyRecyclerContactAdapter adapter;
    ArrayList<Contact> contactList, filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_affiche);

        ContactManager contmanager = new ContactManager(con);
        rv=findViewById(R.id.rv_aff);
        edsearch=findViewById(R.id.edrech_aff);

        //searchview=findViewById(R.id.searchview);
        //searchview.clearFocus();;

        // Load contacts from database (assuming Home.data is populated)
        //recuperation de données  de DB
        ContactManager cm=new ContactManager(Affiche.this);
        cm.ouvrir();
        Home.data=cm.getAllContacts();

        contactList = Home.data != null ? new ArrayList<>(Home.data) : new ArrayList<>();
        filteredList = new ArrayList<>(contactList);



       // MyContactAdapter ad=new MyContactAdapter(Affiche.this,Home.data);
       /* ArrayAdapter ad =new ArrayAdapter(//adapter c pour la creation des view
                Affiche.this,//context
                android.R.layout.simple_list_item_1,//textview exist already : simple_list_item_1
                Home.data);*/

        //model d'affichage de recyclerview
      /*  LinearLayoutManager manager=new LinearLayoutManager(Affiche.this,
                LinearLayoutManager.HORIZONTAL,true);
        rv.setLayoutManager(manager);*/

        GridLayoutManager manager =new GridLayoutManager(Affiche.this,2,GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        MyRecyclerContactAdapter ad=new MyRecyclerContactAdapter(Affiche.this,Home.data);
        rv.setAdapter(ad);

        // Search functionality
        edsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String searchQuery = editable.toString().toLowerCase();
                filterContacts(searchQuery,ad); // Call the filter method to filter and update the RecyclerView
            }
        });
      /*  searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                filterContacts(newText, ad); // Filtrer par numéro et mettre à jour l'adaptateur
                return true;
            }

        });*/

    }
    private void filterContacts(String query, MyRecyclerContactAdapter adapter) {
        filteredList.clear(); // Vider la liste filtrée
        if (query.isEmpty()) {
            filteredList.addAll(contactList); // Si la recherche est vide, afficher tous les contacts
        } else {
            // Ajouter les contacts dont le numéro contient la chaîne de recherche
            for (Contact c : contactList) {
                if ((c.num != null && c.num.toLowerCase().contains(query)) ||
                        (c.nom != null && c.nom.toLowerCase().contains(query)) ||
                        (c.prenom != null && c.prenom.toLowerCase().contains(query))) {
                    filteredList.add(c);
                }

            }
        }
        adapter.updateList(filteredList); // Mettre à jour l'adaptateur avec la liste filtrée
    }


}