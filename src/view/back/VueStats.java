package view.back;

import controller.back.ControleurStats;
import java.awt.BorderLayout;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.business.Manifestation;
import org.jfree.chart.ChartPanel;

public class VueStats extends JFrame implements IOStats {

    private Manifestation manifestation;
    private ControleurStats monControleur;

    private JComboBox cDate;
    private JComboBox cChoix;
    private JComboBox cMatinAprem;

    private ChartPanel cp;

    public VueStats(Manifestation manifestation) {

        super("Statisitques");

        this.manifestation = manifestation;
        this.monControleur = new ControleurStats(this);

        this.cp = new ChartPanel(null);
        this.add(this.construitPanelHaut(), BorderLayout.NORTH);
        this.add(this.cp, BorderLayout.CENTER);

        this.cChoix.addActionListener(monControleur);
        this.cChoix.setActionCommand("choix");
        this.cDate.addActionListener(monControleur);
        this.cDate.setActionCommand("date");
        this.cMatinAprem.addActionListener(monControleur);
        this.cMatinAprem.setActionCommand("matinAprem");

        this.cChoix.setSelectedIndex(0);

        this.setDefaultLookAndFeelDecorated(true);
        this.pack();
        this.setLocationRelativeTo(null);

        this.setVisible(true);

    }

    private JPanel construitPanelHaut() {
        JPanel p = new JPanel();
        p.setLayout((new BoxLayout(p, BoxLayout.X_AXIS)));

        cChoix = new JComboBox();
        cChoix.addItem("Par département");
        cChoix.addItem("Par formation");
        cChoix.addItem("Par jour");

        cDate = new JComboBox();
        cMatinAprem = new JComboBox();

        cMatinAprem.addItem("Matin");
        cMatinAprem.addItem("Après-midi");

        p.add(cChoix);
        p.add(cDate);
        p.add(cMatinAprem);
        return p;

    }

    @Override
    public Date getDate() {
        Date date = null;
        try {
            String sDate = (String) this.cDate.getSelectedItem();

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsed = df.parse(sDate);
            date = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        return date;
    }

    @Override
    public void gereLesStats() {
        if (this.cChoix.getSelectedIndex() == 0) {
            this.cDate.setVisible(false);
            this.cMatinAprem.setVisible(false);
            this.cp.setChart(this.monControleur.getStatsDpt());
        } else if (this.cChoix.getSelectedIndex() == 1) {
            this.cMatinAprem.setVisible(false);
            this.cDate.setVisible(false);
            this.cp.setChart(this.monControleur.getStatsFormation());
        } else if (this.cChoix.getSelectedIndex() == 2) {

            this.cMatinAprem.setVisible(true);
            this.cDate.setVisible(true);
            if (cMatinAprem.getSelectedIndex() == 0) {
                this.cp.setChart(this.monControleur.getStatsDateMatin());
            } else {
                this.cp.setChart(this.monControleur.getStatsDateAprem());
            }
        }
    }

    @Override
    public void remplitDate() {
        if (this.cChoix.getSelectedIndex() == 2) {
            DefaultComboBoxModel modelDate = new DefaultComboBoxModel<String>(this.monControleur.getListeDate());
            this.cDate.setModel(modelDate);
            if (cDate.getItemAt(0) != null) {
                this.cDate.setSelectedIndex(0);
            }
            this.cMatinAprem.setSelectedIndex(0);
        }
    }

    @Override
    public Manifestation getManifestation() {
        return this.manifestation;
    }

}
