package model.business;

public class Departement implements Comparable<Departement> {

    private int idDepartement;
    private String libelleDepartement;

    public Departement(String libelleDpt) {
        this.setLibelleDepartement(libelleDpt);
    }

    public Departement(int idDpt, String libelleDpt) {
        this(libelleDpt);
        this.setIdDepartement(idDpt);
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDpt) {
        this.idDepartement = idDpt;
    }

    public String getLibelleDepartement() {
        return libelleDepartement;
    }

    public void setLibelleDepartement(String libelleDpt) {
        this.libelleDepartement = libelleDpt;
    }

    @Override
    public int compareTo(Departement d) {
        return this.libelleDepartement.compareTo(d.libelleDepartement);
    }

}
