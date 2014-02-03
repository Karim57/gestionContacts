package model.dao.interfaces;

import java.util.ArrayList;
import model.business.Manifestation;

public interface ManifestationDAO {

    public ArrayList<Manifestation> readAll();

    public int create(Manifestation manifestation);

    public void update(Manifestation manifestation);

    public void delete(Manifestation manifestation);

}
