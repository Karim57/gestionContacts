package model.business;

public class Formation {

    private int idFormation;
    private String libelleFormation;
    private Departement departement;

    public Formation(String libelleFormation) {
        this.libelleFormation = libelleFormation;
    }

    public Formation(int idFormation, String libelleFormation, Departement departement) {
        this.idFormation = idFormation;
        this.libelleFormation = libelleFormation;
        this.departement = departement;
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

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
    
    
}
