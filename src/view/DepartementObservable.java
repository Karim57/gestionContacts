/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import model.tables.ModeleDepartement;

/**
 *
 * @author Karim
 */
public interface DepartementObservable {
    
    public void remplitTable(ModeleDepartement modele);

    public String getLibelle();

    public void close();

    public void activeButton();

    public void construitAjout();
}
