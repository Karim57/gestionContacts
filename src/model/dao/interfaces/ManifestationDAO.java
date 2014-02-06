package model.dao.interfaces;

import java.util.Vector;
import model.business.Manifestation;
import model.dao.sql.SQLManifestationDAO;

public interface ManifestationDAO {

    public Vector<Manifestation> readAll();

    public int create(Manifestation manifestation);

    public void update(Manifestation manifestation);

    public void delete(Manifestation manifestation);

}
