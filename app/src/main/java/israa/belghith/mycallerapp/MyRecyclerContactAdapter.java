package israa.belghith.mycallerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.Manifest;


public class MyRecyclerContactAdapter extends RecyclerView.Adapter<MyRecyclerContactAdapter.MyViewHolder> {
   Context con;
   ArrayList<Contact> data;
    private ArrayList<Contact> contactList;  // This is your contactList


    public MyRecyclerContactAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
        this.contactList = new ArrayList<>(data);   // Initialize the contact list
    }

    @NonNull
    @Override
    public MyRecyclerContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creation d'un veiw
        //convertir un fichier xml =>parse xml
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, null);//v=>view holder

        //recupration des textview
        //v est nommé viewHolder

        return new MyViewHolder(v);

    }


    /*
    Méthode qui lie les données d'un contact à un ViewHolder.
    Elle récupère les informations du contact (nom, prénom, numéro) et les affiche dans les vues correspondantes (TextView).
     */
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerContactAdapter.MyViewHolder holder, int position) {
        Contact c = data.get(position);
        holder.tvnom.setText(c.nom != null ? c.nom : "N/A");
        holder.tvprenom.setText(c.prenom != null ? c.prenom : "N/A");
        holder.tvnum.setText(c.num != null ? c.num : "N/A");
    }
   /* @Override
    public void onBindViewHolder(@NonNull MyRecyclerContactAdapter.MyViewHolder holder, int position) {
        //affectation des holders
        Contact c = data.get(position);
        holder.tvnom.setText(c.nom);
        holder.tvprenom.setText(c.prenom);
        holder.tvnum.setText(c.num);
    }*/

    @Override
    public int getItemCount() {
        return data.size();
    }



/*
 Constructeur du ViewHolder. Il récupère les références aux vues nécessaires (les TextView et ImageView) de l'élément de la liste.
 Cela permet de manipuler et de mettre à jour les vues plus tard.
 */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnom,tvprenom,tvnum;
        ImageView imgDelete,imgEdit,imgCall;

        public MyViewHolder(@NonNull View v) {
            super(v);
            //recupertion des Holders
             tvnom = v.findViewById(R.id.tvnom_contact);
             tvprenom = v.findViewById(R.id.tvprenom_contact);
             tvnum = v.findViewById(R.id.tvnum_contact);

            //recuperation des btn
             imgDelete = v.findViewById(R.id.imageViewDelete_contact);
             imgEdit = v.findViewById(R.id.imageViewEdit_contact);
             imgCall = v.findViewById(R.id.imageViewCall_contact);


            //Evenement


/*
Lorsque l'utilisateur clique sur l'icône d'appel, cette méthode vérifie d'abord si l'application a la permission d'effectuer des appels.
 Si la permission est accordée, un appel est lancé.
 Sinon, elle demande la permission à l'utilisateur.
 */
            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indice = getAdapterPosition();
                    Contact c = data.get(indice);

                    if (ContextCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:" + c.num));
                        con.startActivity(i);
                    } else {
                        if (con instanceof Activity) {
                            ActivityCompat.requestPermissions((Activity) con, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            Toast.makeText(con, "Impossible de demander la permission", Toast.LENGTH_SHORT).show();
                        }                    }
                }
            });

/*
Lorsque l'utilisateur clique sur l'icône de suppression, cette méthode affiche une boîte de dialogue de confirmation.
 Si l'utilisateur confirme, le contact est supprimé de la base de données et de la liste affichée dans le RecyclerView.
 */
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //indice  de elt séléctionnée
                    int indice=getAdapterPosition();
                    Contact c=data.get(indice);
                    //affichage boit de dialog
                    AlertDialog.Builder alert=new AlertDialog.Builder(con);
                    alert.setTitle("Suppression");
                    alert.setMessage("Confirmer la suppression?");
                    alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String numToDelete = c.num;  // Numéro du contact sélectionné

                            // Partie base : suppression du contact
                            ContactManager manager = new ContactManager(con);

                            // Ouvrir la base de données
                            manager.ouvrir();

                            // Vérification si le contact existe dans la base de données
                            ArrayList<Contact> contacts = manager.getAllContacts();
                            boolean contactExists = false;
                            for (Contact contact : contacts) {
                                if (contact.num.equals(numToDelete)) {
                                    contactExists = true;
                                    break;
                                }
                            }

                            if (contactExists) {
                                // Suppression du contact de la base
                                int deletedRows = manager.supprimer(numToDelete);

                                if (deletedRows > 0) {
                                    // Mise à jour de l'interface utilisateur
                                    Toast.makeText(con, "Contact supprimé avec succès", Toast.LENGTH_SHORT).show();

                                    // Suppression de l'élément dans la liste et mise à jour de l'interface
                                    data.remove(indice);
                                    notifyDataSetChanged();  // Rafraîchir l'affichage de la liste
                                } else {
                                    Toast.makeText(con, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(con, "Le contact n'existe pas dans la base", Toast.LENGTH_SHORT).show();
                            }
//manager.fermer();
                            // Fermeture de la base de données
                            //db.close();
                        }
                    });
                    alert.setNegativeButton("Annuler", null);
                    // alert.setNeutralButton("Exit",null);
                    alert.show();

                }
            });

/*
Cette méthode affiche une boîte de dialogue permettant à l'utilisateur de modifier les informations du contact.
 Si l'utilisateur confirme, les informations sont mises à jour dans la base de données et dans la liste affichée.
 */
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //indice  de elt séléctionnée
                    int indice=getAdapterPosition();
                    Contact c=data.get(indice);
                    AlertDialog.Builder alert=new AlertDialog.Builder(con);
                    alert.setTitle("Edition");
                    alert.setMessage("modifier les informations du contact");
                    //inflate convert java
                    LayoutInflater inf =LayoutInflater.from(con);
                    View d=inf.inflate(R.layout.view_dialog,null);
                    alert.setView(d);

                    // Initialiser les champs d'édition dans le dialogue
                    EditText edNom = d.findViewById(R.id.ednom_edit);
                    EditText edPrenom = d.findViewById(R.id.edprenom_edit);
                    EditText edNum = d.findViewById(R.id.ednum_edit);

                    // Remplir les champs avec les données existantes du contact
                    edNom.setText(c.nom);
                    edPrenom.setText(c.prenom);
                    edNum.setText(c.num);

                    // Ajouter le bouton de modification
                    alert.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Récupérer les nouvelles données
                            String newNom = edNom.getText().toString();
                            String newPrenom = edPrenom.getText().toString();
                            String newNum = edNum.getText().toString();

                            // Mettre à jour la base de données
                            ContactManager manager = new ContactManager(con);
                            manager.ouvrir();

                            // Mise à jour du contact dans la base
                            ContentValues values = new ContentValues();
                            values.put(ContactHelper.col_nom, newNom);
                            values.put(ContactHelper.col_prenom, newPrenom);
                            values.put(ContactHelper.col_num, newNum);

                            int updatedRows = manager.db.update(ContactHelper.table_contact, values, ContactHelper.col_num + "=?", new String[]{c.num});

                            if (updatedRows > 0) {
                                // Mise à jour réussie
                                Toast.makeText(con, "Contact modifié avec succès", Toast.LENGTH_SHORT).show();

                                // Mettre à jour l'interface
                                c.nom = newNom;
                                c.prenom = newPrenom;
                                c.num = newNum;

                                // Rafraîchir l'affichage
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(con, "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
                            }

                            //  manager.fermer();
                        }
                    });
                    alert.setNegativeButton("Annuler", null);
                    alert.show();
                    //recupere data et l'inserer dans edtext et faire l'enregistrement dans interface + data
                }
            });

//recherche
            //check admin
            //permission
            //sharedpreference(mémoie cashe)->variable conected=false or true(home page directement)



        }

    }

    /*
    Cette méthode met à jour la liste des contacts et notifie
    le RecyclerView de la mise à jour pour que l'interface utilisateur soit rafraîchie.
     */

    public void updateList(ArrayList<Contact> newContacts) {
        this.data = newContacts;
        notifyDataSetChanged(); // Rafraîchir le RecyclerView avec la liste filtrée
    }


}
