package model.business;

public class Manifestation {

    private int idManif;
    private String libelleManif;

    public Manifestation(String libelleManif) {
        this.setLibelleManif(libelleManif);
    }

    public Manifestation(int idManif, String libelleManif) {
        this(libelleManif);
        this.setIdManif(idManif);
    }

    public int getIdManif() {
        return idManif;
    }

    private void setIdManif(int idManif) {
        this.idManif = idManif;
    }

    public String getLibelleManif() {
        return libelleManif;
    }

    public void setLibelleManif(String libelleManif) {
        this.libelleManif = libelleManif;
    }

}
