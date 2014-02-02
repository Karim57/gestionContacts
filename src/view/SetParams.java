package view;

import controller.ControllerSetParams;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class SetParams extends JDialog {

    private JButton bSubmit;
    private ControllerSetParams controller;

    JTextField tSQLHost;
    JTextField tSQLPort;
    JTextField tSQLUser;
    JPasswordField tSQLPassword;

    JTextField tFTPHost;
    JTextField tFTPPort;
    JTextField tFTPUser;
    JPasswordField tFTPPassword;

    public SetParams(JFrame parent) {

        super(parent, "Gestion des étudiants");

        this.construitInterface();

        this.controller = new ControllerSetParams();

        this.setLocation(40, 40);
        this.setSize(730, 400);

        this.setVisible(true);
    }

    private void construitInterface() {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JPanel pSaisie = this.construitPanelSaisieSQL();

        p.add(pSaisie);

        this.add(p);
    }

    private JPanel construitPanelSaisieSQL() {

        JPanel panelSQL = new JPanel();
        panelSQL.setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createEmptyBorder(5, 5, 5, 5), BorderFactory
                .createTitledBorder("Paramètres de la base de données")));

        JLabel lSQLHost = new JLabel("Serveur SQL : ");
        JLabel lSQLPort = new JLabel("Port : ");
        JLabel lSQLUser = new JLabel("Utilisateur : ");
        JLabel lSQLPassword = new JLabel("Mot de passe ");

        JLabel lFTPHost = new JLabel("Serveur FTP : ");
        JLabel lFTPPort = new JLabel("Port : ");
        JLabel lFTPUser = new JLabel("Utilisateur : ");
        JLabel lFTPPassword = new JLabel("Mot de passe ");

        this.tSQLHost = new JTextField();
        this.tSQLPort = new JTextField();
        this.tSQLUser = new JTextField();
        this.tSQLPassword = new JPasswordField();

        panelSQL.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        panelSQL.add(lSQLHost, gbc);
        panelSQL.add(this.tSQLHost, gbc);

        gbc.gridy = 1;
        panelSQL.add(lSQLPort, gbc);
        panelSQL.add(this.tSQLPort, gbc);

        gbc.gridy = 2;
        panelSQL.add(lSQLUser, gbc);
        panelSQL.add(this.tSQLUser, gbc);

        gbc.gridy = 3;
        panelSQL.add(lSQLPassword, gbc);
        panelSQL.add(this.tSQLPassword, gbc);

        return panelSQL;

    }

}
