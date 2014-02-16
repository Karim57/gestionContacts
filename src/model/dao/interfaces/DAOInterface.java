package model.dao.interfaces;

import java.util.ArrayList;

public interface DAOInterface<T extends Comparable<? super T>> {

    public ArrayList<T> readAll();

    public int create(T objetAAjouter);

    public boolean update(T objetAModifier);

    public boolean delete(T objetASupprimer);

    public void deleteList(ArrayList<T> liste);

}
