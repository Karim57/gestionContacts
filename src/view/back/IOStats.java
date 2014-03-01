package view.back;

import java.sql.Date;
import model.business.Manifestation;
import org.jfree.chart.JFreeChart;

public interface IOStats {

    public Date getDate();

    public void gereLesStats();

    public Manifestation getManifestation();

    public void remplitDate();
}
