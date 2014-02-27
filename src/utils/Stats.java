package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public class Stats extends JPanel {

	private static final long serialVersionUID = 1L;

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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getOrdonnee() {
        return ordonnee;
    }

    public void setOrdonnee(String ordonnee) {
        this.ordonnee = ordonnee;
    }

    public String getAbscisse() {
        return abscisse;
    }

    public void setAbscisse(String abscisse) {
        this.abscisse = abscisse;
    }

    public List<Float> getValeurs() {
        return valeurs;
    }

    public void setValeurs(List<Float> valeurs) {
        this.valeurs = valeurs;
    }

    public List<String> getSeries() {
        return series;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public boolean isLegende() {
        return legende;
    }

    public void setLegende(boolean legende) {
        this.legende = legende;
    }

    public Color getCouleurFond() {
        return couleurFond;
    }

    public void setCouleurFond(Color couleurFond) {
        this.couleurFond = couleurFond;
    }

    public Color[] getCouleursBarres() {
        return couleursBarres;
    }

    public void setCouleursBarres(Color[] couleursBarres) {
        this.couleursBarres = couleursBarres;
    }

    public Stats(String titre, String ordonnee, String abscisse, List<Float> valeurs, List<String> series, List<String> categories, boolean legende, Color couleurFond) {
        super(new GridLayout(1,0));
        this.titre = titre;
        this.ordonnee = ordonnee;
        this.abscisse = abscisse;
        this.valeurs = valeurs;
        this.series = series;
        this.categories = categories;
        this.legende = legende;
        this.couleurFond = couleurFond;
        initialiser();
    }

    /**
	 * Initialise le graphique
	 */
	private void initialiser(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int k = 0;
		for ( int j=0; j<categories.size(); j++){
			for (int i=0; i<series.size(); i++){
				dataset.addValue(valeurs.get(k), series.get(i), categories.get(j));
				k++;
			}

		}
    
}




}