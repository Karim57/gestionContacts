package model.dao;

import java.util.ArrayList;

public interface DAOInterface<T> {

    public ArrayList<T> readAll();

    public int create(T objetAAjouter);

    public boolean update(T objetAModifier);

    public boolean delete(T objetASupprimer);

    public void deleteList(ArrayList<T> liste);
    
    public T readById(int id);
    
   /* public int createList(ArrayList<T> liste);*/

}
