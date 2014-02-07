/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao.interfaces;

import java.util.Vector;
import model.business.Departement;

/**
 *
 * @author Karim
 */
public interface DepartementDAO {
    
    public Vector<Departement> readAll();

    public int create(Departement departement);

    public void update(Departement departement);

    public void delete(Departement departement);
}
