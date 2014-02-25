package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import model.business.Departement;

public abstract class vueAbstraite extends JFrame {

    protected JButton bSubmit;
    protected JButton bCancel;

    protected JButton bNouveau;
    protected JButton bModifier;
    protected JButton bSupprimer;
    protected JButton bManifDefaut;

    protected JComboBox<Departement> cListeDpt;

    protected JTextField tSearch;

    public vueAbstraite(String titre) {
        super(titre);
    }

    protected JPanel creerPanelTable(JTableDonnees table) {

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        table.getTableHeader().setBackground(new Color(245, 246, 247));
        table.getTableHeader().setFont(new Font("Arial", 1, 12));
        jp.add(table.getTableHeader(), gbc2);

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        jp.add(table, gbc2);

        JScrollPane pane = new JScrollPane(table);

        jp.add(pane, gbc2);

        return jp;

    }

    protected JPanel construitPanelButtons() {

        JPanel panelButton = new JPanel();

        panelButton.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 2, 10);
        this.bCancel = new JButton("Annuler");
        panelButton.add(this.bCancel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 2, 10);
        this.bSubmit = new JButton("Ajouter");
        this.bSubmit.setActionCommand("submitAjout");
        this.bSubmit.setEnabled(false);
        panelButton.add(this.bSubmit, gbc);

        return panelButton;
    }

    protected JToolBar creerBarreOutils() {

        JToolBar jtb = new JToolBar();
        jtb.setLayout(new BorderLayout());
        jtb.setBackground(new Color(240, 240, 240));

        jtb.add(this.construitPanelOutilsGauche(), BorderLayout.WEST);

        jtb.add(this.construitPanelOutilsDroite(), BorderLayout.EAST);

        jtb.setFloatable(false);

        jtb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return jtb;

    }

    private JPanel construitPanelOutilsDroite() {

        JPanel p = new JPanel(new FlowLayout());

        this.cListeDpt = new JComboBox<Departement>();
        p.add(this.cListeDpt);

        ClassLoader cl = this.getClass().getClassLoader();
        JLabel lSearch = new JLabel("Recherche : ");
        p.add(lSearch);

        this.tSearch = new JTextField();
        this.tSearch.setActionCommand("recherche");
        this.tSearch.getDocument().putProperty("id", "tSearch");
        this.tSearch.setPreferredSize(new Dimension(230, 23));
        p.add(this.tSearch);

        return p;
    }

    private JPanel construitPanelOutilsGauche() {

        ClassLoader cl = this.getClass().getClassLoader();

        Dimension dimButtons = new Dimension(40, 30);
        Color bgColor = new Color(240, 240, 240);

        this.bNouveau = new JButton(new ImageIcon(cl.getResource("view/images/add2.png")));
        this.bNouveau.setActionCommand("Nouveau");
        this.bNouveau.setBorderPainted(false);
        this.bNouveau.setFocusPainted(false);
        this.bNouveau.setBackground(bgColor);
        this.bNouveau.setPreferredSize(dimButtons);

        this.bModifier = new JButton(new ImageIcon(cl.getResource("view/images/edit.png")));
        this.bModifier.setActionCommand("Modifier");
        this.bModifier.setBorderPainted(false);
        this.bModifier.setFocusPainted(false);
        this.bModifier.setBackground(bgColor);
        this.bModifier.setPreferredSize(dimButtons);

        this.bSupprimer = new JButton(new ImageIcon(cl.getResource("view/images/trash.png")));
        this.bSupprimer.setBorderPainted(false);
        this.bSupprimer.setFocusPainted(false);
        this.bSupprimer.setActionCommand("Supprimer");
        this.bSupprimer.setToolTipText("Supprimer");
        this.bSupprimer.setBackground(bgColor);
        this.bSupprimer.setPreferredSize(dimButtons);

        this.bManifDefaut = new JButton(new ImageIcon(cl.getResource("view/images/xml.png")));
        this.bManifDefaut.setBorderPainted(false);
        this.bManifDefaut.setFocusPainted(false);
        this.bManifDefaut.setActionCommand("ManifDefaut");
        this.bManifDefaut.setBackground(bgColor);
        this.bManifDefaut.setPreferredSize(dimButtons);

        JSeparator js = new JSeparator(JSeparator.VERTICAL);
        js.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JSeparator js2 = new JSeparator(JSeparator.VERTICAL);
        js2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JPanel p = new JPanel(new GridBagLayout());

        p.setBackground(bgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;

        p.add(this.bNouveau, gbc);

        gbc.gridx = 1;
        p.add(js, gbc);

        gbc.gridx = 2;
        p.add(bModifier, gbc);

        gbc.gridx = 3;
        p.add(bSupprimer, gbc);

        gbc.gridx = 4;
        p.add(js2, gbc);

        gbc.gridx = 5;
        p.add(this.bManifDefaut, gbc);

        return p;
    }

}
