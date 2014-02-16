package model.business;

public class Formation {

    private int idFormation;
    private String libelleFormation;
    private int idDepartement;

    public Formation(String libelleFormation) {
        this.libelleFormation = libelleFormation;
    }

    public Formation(int idFormation, String libelleFormation, int idDepartement) {
        this.idFormation = idFormation;
        this.libelleFormation = libelleFormation;
        this.idDepartement = idDepartement;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getLibelleFormation() {
        return libelleFormation;
    }

    public void setLibelleFormation(String libelleFormation) {
        this.libelleFormation = libelleFormation;
    }

    public int getIdDepartement() {
        return this.idDepartement;
    }

    public void setDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

}
