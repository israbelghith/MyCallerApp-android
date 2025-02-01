package israa.belghith.mycallerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyContactAdapter extends BaseAdapter {

    Context con;
    ArrayList<Contact> data;
    MyContactAdapter(Context con, ArrayList<Contact> data)
    {
        this.con=con;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();//return nbr de view à créer
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //creation d'un veiw
        //convertir un fichier xml =>parse xml
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, null);

        //recupration des textview
        //v est nommé viewHolder
        //recupertion des Holders
        TextView tvnom = v.findViewById(R.id.tvnom_contact);
        TextView tvprenom = v.findViewById(R.id.tvprenom_contact);
        TextView tvnum = v.findViewById(R.id.tvnum_contact);

        //recuperation des btn
        ImageView imgDelete = v.findViewById(R.id.imageViewDelete_contact);
        ImageView imgEdit = v.findViewById(R.id.imageViewEdit_contact);
        ImageView imgCall = v.findViewById(R.id.imageViewCall_contact);

        //affectation des holders
        Contact c = data.get(position);
        tvnom.setText(c.nom);
        tvprenom.setText(c.prenom);
        tvnum.setText(c.num);

        //Evenement
imgCall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:"+c.num));
        con.startActivity(i);
    }
});
imgDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //affichage boit de dialog
        AlertDialog.Builder alert=new AlertDialog.Builder(con);
        alert.setTitle("Suppression");
        alert.setMessage("Confirmer la suppression?");
        alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*
                  supprimer depuis la base et l'interface selon numero(id)
                 */
                data.remove(position);
                notifyDataSetChanged();//refresh
            }
        });
        alert.setNegativeButton("Annuler", null);
       // alert.setNeutralButton("Exit",null);
        alert.show();

    }
});

imgEdit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder alert=new AlertDialog.Builder(con);
        alert.setTitle("Edition");
        alert.setMessage("modifier les informations du contact");
        //inflate convert java
        LayoutInflater inf =LayoutInflater.from(con);
        View d=inf.inflate(R.layout.view_dialog,null);
        alert.setView(d);
        alert.show();
        //recupere data et l'inserer dans edtext et faire l'enregistrement dans interface + data
    }
});

        return v;

       /* Button btn=new Button(this.con);
        Contact c=data.get(position);
        btn.setText(c.num);

        return btn;//return 1 view à afficher
    }*/
    }
}
