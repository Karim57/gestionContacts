package model.business;

public class Departement implements Comparable<Departement> {

    private int idDepartement;
    private String libelleDepartement;

    public Departement(String libelleDepartement) {
        this.setLibelleDepartement(libelleDepartement);
    }

    public Departement(int idDeprtement, String libelleDepartement) {
        this(libelleDepartement);
        this.setIdDepartement(idDepartement);
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getLibelleDepartement() {
        return libelleDepartement;
    }

    public void setLibelleDepartement(String libelleDepartement) {
        this.libelleDepartement = libelleDepartement;
    }

    @Override
    public int compareTo(Departement d) {
        return this.libelleDepartement.compareTo(d.libelleDepartement);
    }

}
