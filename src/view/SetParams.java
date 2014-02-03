package view;

import controller.ControllerSetParams;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.*;

public class SetParams extends JDialog implements Observable {

    private JButton bSubmit;
    private JButton bCancel;
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

        super(parent, "Paramètres de l'application");

        this.construitInterface();

        this.controller = new ControllerSetParams(this);

        this.setLocation(600, 100);
        this.setSize(350, 400);

        this.setResizable(false);

        this.setVisible(true);
       
    }

    private void construitInterface() {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JPanel pSQL = this.construitPanelSaisieSQL();
        JPanel pFTP = this.construitPanelSaisieFTP();
        JPanel pButtons = this.construitPanelButtons();

        p.add(pSQL);
        p.add(pFTP);
        p.add(pButtons);

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
        JLabel lSQLPassword = new JLabel("Mot de passe : ");

        this.tSQLHost = new JTextField(80);
        this.tSQLPort = new JTextField(100);
        this.tSQLUser = new JTextField(100);
        this.tSQLPassword = new JPasswordField(100);

        panelSQL.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelSQL.add(lSQLHost, gbc);

        gbc.gridy = 1;
        panelSQL.add(lSQLPort, gbc);

        gbc.gridy = 2;
        panelSQL.add(lSQLUser, gbc);

        gbc.gridy = 3;
        panelSQL.add(lSQLPassword, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelSQL.add(this.tSQLHost, gbc);

        gbc.gridy = 1;
        panelSQL.add(this.tSQLPort, gbc);

        gbc.gridy = 2;
        panelSQL.add(this.tSQLUser, gbc);

        gbc.gridy = 3;
        panelSQL.add(this.tSQLPassword, gbc);

        return panelSQL;

    }

    private JPanel construitPanelSaisieFTP() {

        JPanel panelFTP = new JPanel();
        panelFTP.setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createEmptyBorder(5, 5, 5, 5), BorderFactory
                .createTitledBorder("Paramètres FTP (Optionnels)")));

        JLabel lFTPHost = new JLabel("Serveur FTP : ");
        JLabel lFTPPort = new JLabel("Port : ");
        JLabel lFTPUser = new JLabel("Utilisateur : ");
        JLabel lFTPPassword = new JLabel("Mot de passe : ");

        this.tFTPHost = new JTextField();
        this.tFTPPort = new JTextField();
        this.tFTPUser = new JTextField();
        this.tFTPPassword = new JPasswordField();

        panelFTP.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelFTP.add(lFTPHost, gbc);

        gbc.gridy = 1;
        panelFTP.add(lFTPPort, gbc);

        gbc.gridy = 2;
        panelFTP.add(lFTPUser, gbc);

        gbc.gridy = 3;
        panelFTP.add(lFTPPassword, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFTP.add(this.tFTPHost, gbc);

        gbc.gridy = 1;
        panelFTP.add(this.tFTPPort, gbc);

        gbc.gridy = 2;
        panelFTP.add(this.tFTPUser, gbc);

        gbc.gridy = 3;
        panelFTP.add(this.tFTPPassword, gbc);

        return panelFTP;

    }

    private JPanel construitPanelButtons() {

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
        this.bSubmit = new JButton("Valider");
        panelButton.add(this.bSubmit, gbc);

        return panelButton;

    }

}
