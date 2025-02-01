package israa.belghith.mycallerapp;

public class Contact {
    public String nom,prenom,num;

    public Contact(String nom, String prenom, String num) {
        this.nom = nom;
        this.prenom = prenom;
        this.num = num;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
