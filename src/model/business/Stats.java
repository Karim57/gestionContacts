package model.business;


import java.awt.Color;
import java.util.List;

public class Stats {
    
    /** titre : Le titre du graphique affiché en haut */
	private String titre;
	/** ordonnee : le nom de l'axe des ordonnées */
	private String ordonnee;
	/** abscisses : le nom de l'axe des abscisses */
	private String abscisse;
	/** valeurs : les valeurs à afficher, elles sont triées par séries et par catégories*/
	private List<Float> valeurs;
	/** series : la liste des séries */
	private List<String> series;
	/** categories : la liste des categories */
	private List<String> categories;
	/** legende : booleen vrai si on affiche la légende */
	private boolean legende;
	/** couleurFond : la couleur du fond */
	private Color couleurFond;
	/** couleurBarres : les couleurs appliquées aux barres */
	private Color[] couleursBarres = {Color.cyan.darker(), 
			Color.red, Color.green, Color.cyan, Color.magenta, 
			Color.yellow, Color.pink, Color.darkGray, Color.orange};
    
}
