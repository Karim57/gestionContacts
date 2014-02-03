package view.manifestation;

import controller.manifestation.ManifestationControlleurPrincipale;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class ManifestationFenetrePrincipale extends JFrame {

    public ManifestationFenetrePrincipale() {

        super("Exemple d'appli MVC-DAO");

        this.setJMenuBar(this.creerBarreMenu());
        this.add(creerBarreOutils(), BorderLayout.NORTH);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(400, 200);

        this.setVisible(true);
    }

    private JMenuBar creerBarreMenu() {

        JMenuBar mb = new JMenuBar();
        mb.setBackground(new Color(245, 246, 247));
        JMenu file = new JMenu("Fichier");

        ClassLoader cl = this.getClass().getClassLoader();

        JMenuItem newEvent = new JMenuItem("Nouveau", KeyEvent.VK_N);
        newEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        ImageIcon iconAdd = new ImageIcon(cl.getResource("view/images/add.png"));

        newEvent.setIcon(iconAdd);

        JMenuItem editEvent = new JMenuItem("Editer");
        JMenuItem deleteEvent = new JMenuItem("Supprimer");
        JMenuItem generateXMLEvent = new JMenuItem("Générer XML");

        file.add(newEvent);
        file.add(editEvent);
        file.add(deleteEvent);
        file.addSeparator();
        file.add(generateXMLEvent);

        JMenu view = new JMenu("Fenêtre");
        JMenuItem manifestation = new JMenuItem("Manifestations");
        JMenuItem enseignant = new JMenuItem("Enseignants");
        JMenuItem formation = new JMenuItem("Formations");

        view.add(manifestation);
        view.add(enseignant);
        view.add(formation);

        JMenu help = new JMenu("?");

        JMenu settings = new JMenu("Paramètres");

        mb.add(file);
        mb.add(view);
        mb.add(settings);
        mb.add(help);

        newEvent.addActionListener(new ManifestationControlleurPrincipale(this));

        return mb;

    }

    private JToolBar creerBarreOutils() {
        JToolBar jtb = new JToolBar();
        jtb.setLayout(new GridBagLayout());
        jtb.setBorder(new EmptyBorder(0, 5, 0, 0));
        jtb.setBackground(new Color(200, 240, 240));
        ClassLoader cl = this.getClass().getClassLoader();

        JButton newEvent = new JButton(new ImageIcon(cl.getResource("view/images/add2.png")));
        newEvent.setBorderPainted(false);
        newEvent.setFocusPainted(false);

        JButton editEvent = new JButton(new ImageIcon(cl.getResource("view/images/edit.png")));
        editEvent.setBorderPainted(false);
        editEvent.setFocusPainted(false);

        JButton deleteEvent = new JButton(new ImageIcon(cl.getResource("view/images/trash.png")));
        deleteEvent.setBorderPainted(false);
        deleteEvent.setFocusPainted(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;

        jtb.add(newEvent, gbc);

        gbc.gridx = 1;
        jtb.add(new JSeparator(JSeparator.VERTICAL), gbc);

        gbc.gridx = 2;
        jtb.add(editEvent, gbc);

        gbc.gridx = 3;
        jtb.add(deleteEvent, gbc);

        jtb.setFloatable(false);

        return jtb;

    }
}
