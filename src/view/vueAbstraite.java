package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public abstract class vueAbstraite extends JFrame {

    protected JButton bSubmit;
    protected JButton bCancel;

    protected JButton bNouveau;
    protected JButton bModifier;
    protected JButton bSupprimer;

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
        jtb.setLayout(new GridBagLayout());
        jtb.setBorder(new EmptyBorder(0, 5, 0, 0));
        jtb.setBackground(new Color(240, 240, 240));
        ClassLoader cl = this.getClass().getClassLoader();

        this.bNouveau = new JButton(new ImageIcon(cl.getResource("view/images/add2.png")));
        this.bNouveau.setActionCommand("Nouveau");
        this.bNouveau.setBorderPainted(false);
        this.bNouveau.setFocusPainted(false);

        this.bModifier = new JButton(new ImageIcon(cl.getResource("view/images/edit.png")));
        this.bModifier.setActionCommand("Modifier");
        this.bModifier.setBorderPainted(false);
        this.bModifier.setFocusPainted(false);

        bSupprimer = new JButton(new ImageIcon(cl.getResource("view/images/trash.png")));
        bSupprimer.setBorderPainted(false);
        bSupprimer.setFocusPainted(false);
        bSupprimer.setActionCommand("Supprimer");

        JButton bsearch = new JButton(new ImageIcon(cl.getResource("view/images/search.png")));
        bsearch.setBorderPainted(false);
        bsearch.setFocusPainted(false);

        JButton xmlEvent = new JButton(new ImageIcon(cl.getResource("view/images/xml.png")));
        xmlEvent.setBorderPainted(false);
        xmlEvent.setFocusPainted(false);

        JSeparator js = new JSeparator(JSeparator.VERTICAL);
        js.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JSeparator js2 = new JSeparator(JSeparator.VERTICAL);
        js2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;

        jtb.add(this.bNouveau, gbc);

        gbc.gridx = 1;
        jtb.add(js, gbc);

        gbc.gridx = 2;
        jtb.add(bModifier, gbc);

        gbc.gridx = 3;
        jtb.add(bSupprimer, gbc);

        gbc.gridx = 4;
        jtb.add(js2, gbc);

        gbc.gridx = 5;
        jtb.add(xmlEvent, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 200, 0, 5);
        gbc.gridx = 6;
        JTextField search = new JTextField(100);
        jtb.add(search, gbc);

        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 7;
        jtb.add(bsearch, gbc);

        jtb.setFloatable(false);

        jtb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return jtb;

    }

}
