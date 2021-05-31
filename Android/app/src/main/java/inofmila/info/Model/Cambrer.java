package inofmila.info.Model;

public class Cambrer {
    private long codi;
    private String nom;
    private String cognom1;
    private String cognom2;
    private String usuari;
    private String passw;

    public Cambrer(long codi, String nom, String cognom1, String cognom2, String ususari, String passw)
    {
        this.codi = codi;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.usuari = ususari;
        this.passw = passw;
    }

    public long getCodi() {
        return codi;
    }

    public void setCodi(long codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    @Override
    public String toString() {
        return "Cambrer{" + "codi=" + codi + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", usuari=" + usuari + ", passw=" + passw + '}';
    }
}
