package israa.belghith.mycallerapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ContactManager {
    SQLiteDatabase db = null;  // Objet pour gérer la base de données SQLite
    Context con;  // Contexte de l'application (utilisé pour accéder aux ressources)

    ContactManager(Context con){
       this.con=con;
    }
    public void ouvrir()
    {
        ContactHelper helper=new ContactHelper(con,"contactbase.db",null,2);
        db=helper.getWritableDatabase();
    }
    public long ajout(String num,String nom,String prenom)
    {
//retourne le nbr des elts insérer
        long a=0;
        ContentValues values=new ContentValues();
        values.put(ContactHelper.col_num,num);
        values.put(ContactHelper.col_nom,nom);
        values.put(ContactHelper.col_prenom,prenom);
a=db.insert(ContactHelper.table_contact,null,values);
return a;
    }
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> l = new ArrayList<Contact>();
        Cursor cr = db.query(ContactHelper.table_contact,
                new String[]{ContactHelper.col_num,
                        ContactHelper.col_nom,
                        ContactHelper.col_prenom}, null, null, null, null, null);


        cr.moveToFirst();  // Déplace le curseur à la première ligne
        while (!cr.isAfterLast()) {  // Parcours des résultats
            String i1 = cr.getString(0);  // Récupère le numéro
            String i2 = cr.getString(1);  // Récupère le nom
            String i3 = cr.getString(2);  // Récupère le prénom
            l.add(new Contact(i1, i2, i3));  // Ajoute le contact à la liste
            cr.moveToNext();  // Déplace le curseur vers la ligne suivante
        }
        return l;  // Retourne la liste de tous les contacts
    }
    public int supprimer(String num) {
       // SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(ContactHelper.table_contact, ContactHelper.col_num+"= ?", new String[]{num});
        db.close();
        return deletedRows;
    }

    public void fermer() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public ArrayList<Contact> searchContactsByPhoneNumber(String phoneNumber) {
        ArrayList<Contact> l = new ArrayList<Contact>();
        // Query to search for contacts where phone number matches
        String query = "SELECT * FROM " + ContactHelper.table_contact + " WHERE " + ContactHelper.col_num + " LIKE ?";
        Cursor cr = db.rawQuery(query, new String[]{"%" + phoneNumber + "%"});

        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            String i1 = cr.getString(0);  // Phone number
            String i2 = cr.getString(1);  // Name
            String i3 = cr.getString(2);  // Surname
            l.add(new Contact(i1, i2, i3));  // Add contact to the list
            cr.moveToNext();
        }
        cr.close();
        return l;
    }

}
