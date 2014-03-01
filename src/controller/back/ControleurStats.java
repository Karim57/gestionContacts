package controller.back;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.business.Manifestation;
import model.dao.sql.SQLStatsDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import view.back.IOStats;

public class ControleurStats implements ActionListener {

    private IOStats vue;
    Manifestation manifestation;

    public ControleurStats(IOStats vue) {
        this.vue = vue;
        this.manifestation = this.vue.getManifestation();
    }

    public JFreeChart getStatsDpt() {
        return ChartFactory.createBarChart("Histogramme",
                "Départements",
                "Contacts",
                SQLStatsDAO.getInstance().getNbContactsParDpt(this.manifestation));

    }

    public JFreeChart getStatsFormation() {
        return ChartFactory.createBarChart("Histogramme",
                "Formations",
                "Contacts",
                SQLStatsDAO.getInstance().getNbContactsParFormation(manifestation));
    }

    public String[] getListeDate() {
        ArrayList<Date> aList = SQLStatsDAO.getInstance().getJours(manifestation);
        String[] liste = new String[aList.size()];
        for (int i = 0; i < aList.size(); i++) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            liste[i] = df.format(aList.get(i));
        }
        return liste;
    }

    public JFreeChart getStatsDateMatin() {
        return ChartFactory.createBarChart("Histogramme",
                "Départements",
                "Contacts",
                SQLStatsDAO.getInstance().getNbContactsParDateMatin(manifestation, this.vue.getDate()));
    }

    public JFreeChart getStatsDateAprem() {
        return ChartFactory.createBarChart("Histogramme",
                "Départements",
                "Contacts",
                SQLStatsDAO.getInstance().getNbContactsParDateAprem(manifestation, this.vue.getDate()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("choix")) {
            this.vue.remplitDate();
        }
        this.vue.gereLesStats();
    }

}
