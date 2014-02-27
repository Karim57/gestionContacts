package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.business.Departement;
import model.dao.sql.SQLStatsDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import view.VueEnseignants;
import view.VueFenetrePrincipale;
import utils.Stats;

public class Principale {

    public static void main(String[] args) {

       // VueFenetrePrincipale vuePrincipale = new VueFenetrePrincipale();

       
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(10, 10, 500, 500);
        JFreeChart s = ChartFactory.createBarChart("Histogramme", "Formations", "Contacts", SQLStatsDAO.getInstance().getNbContactsParDpt());
        ChartPanel p = new ChartPanel(s);;
        f.add(p);
        f.setVisible(true);

    }
}
