package inofmila.info.Model;

public class Plat {
    private long codi;
    private String nom;
    private float preu;
    private int midaBytesFoto;
    private byte[] streamFoto;
    private int categoria;

    public Plat(long codi, String nom, float preu, int midaBytesFoto, byte[] streamFoto, int categoria) {
        this.codi = codi;
        this.nom = nom;
        this.preu = preu;
        this.midaBytesFoto = midaBytesFoto;
        this.streamFoto = streamFoto;
        this.categoria = categoria;
    }

    public int getCategoria()
    {
        return categoria;
    }

    public void setCategoria(int categoria)
    {
        this.categoria = categoria;
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

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getMidaBytesFoto() {
        return midaBytesFoto;
    }

    public void setMidaBytesFoto(int midaBytesFoto) {
        this.midaBytesFoto = midaBytesFoto;
    }

    public byte[] getStreamFoto() {
        return streamFoto;
    }

    public void setStreamFoto(byte[] streamFoto) {
        this.streamFoto = streamFoto;
    }

    @Override
    public String toString() {
        return "Plat{" + "codi=" + codi + ", nom=" + nom + ", preu=" + preu + ", midaBytesFoto=" + midaBytesFoto + ", streamFoto=" + streamFoto + '}';
    }
}
