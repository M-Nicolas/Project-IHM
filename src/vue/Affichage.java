package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import controle.Controller;
import controle.Launch;

public class Affichage extends Fenetre implements Observer{
    private String id;
    private JFrame frame = new JFrame();
    private SpringLayout layout = new SpringLayout();
    private JPanel paneglob = new JPanel();
    private JPanel panemiddle = new JPanel();
    private JPanel panesouth = new JPanel();
    
    private JLabel affiche;
    private JLabel titre;
    private JLabel reali;
    private JList<String> act;
    private JList<String> gen;
    private JLabel dur;
    private JTextArea resume;
    private Controller controller;
    private JButton editer = new JButton("Edition");
    private JButton retour = new JButton("Retour");
    
    /**
     * Constructeur de la classe Affichage qui crée la frame demandé.
     */
    public Affichage(String idFilm) {
    	
    	id = idFilm;
    	
    	controller = Launch.getController();
    	
        paneglob.setLayout(new BorderLayout());
        panemiddle.setLayout(layout);
        panesouth.setLayout(new GridLayout(2,1));
        
        Font gras = new Font("Ubuntu", Font.BOLD, 12);
        Font plain = new Font("Ubuntu", Font.PLAIN, 12);
        JLabel realisateur = new JLabel("Réalisateur :");
        JLabel acteurs = new JLabel("Avec :");
        JLabel genre = new JLabel("Genre :");
        JLabel duree = new JLabel("Durée :");
        
        AffichageListener listener = new AffichageListener();
        editer.addActionListener(listener);
        retour.addActionListener(listener);
        initialiseFilm(idFilm);
        gererTitre();
        gererAct();
        gererResume();
        setFont(gras, plain);

        genre.setFont(gras);
        realisateur.setFont(gras);
        duree.setFont(gras);
        
        paneglob.add(titre, BorderLayout.PAGE_START);
        ArrayList<Component> liste = new ArrayList<Component>();
        liste.add(affiche);
        liste.add(realisateur);
        liste.add(reali);
        liste.add(acteurs);
        liste.add(act);
        liste.add(genre);
        liste.add(gen);
        liste.add(duree);
        liste.add(dur);
        
        for(int i=1; i <= liste.size() - 1; i++) {
            panemiddle.add((Component) liste.get(i));
            if (i == 1)
                layout.putConstraint(SpringLayout.WEST, liste.get(i),5,SpringLayout.EAST, affiche);
            else {
                layout.putConstraint(SpringLayout.NORTH, liste.get(i),5,SpringLayout.SOUTH, liste.get(i-1));
                layout.putConstraint(SpringLayout.WEST, liste.get(i),5,SpringLayout.EAST, affiche);
            }
        }
        
        addPane();
        
        generateFrame();
    }
    
    private void gererTitre() {
        titre.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void gererAct() {
        act.setOpaque(false);
        act.setFocusable(false);
        act.setOpaque(false);
    }
    
    private void gererResume() {
        resume.setMargin(new Insets(5,5,5,5));
        resume.setLineWrap(true);
        resume.setWrapStyleWord(true);
        resume.setOpaque(false);
        resume.setEditable(false);
    }
    
    private void setFont(Font gras, Font plain) {
        gen.setFont(plain);
        reali.setFont(plain);
        titre.setFont(gras);
        act.setFont(plain);
        dur.setFont(plain);
    }
    
    private void addPane() {
        panemiddle.add(affiche);
        paneglob.add(panemiddle,BorderLayout.CENTER);
        paneglob.add(resume,BorderLayout.SOUTH);
        panesouth.add(editer);
        panesouth.add(retour);
        paneglob.add(panesouth, BorderLayout.EAST);
    }
    
    private void generateFrame() {
        frame.setContentPane(paneglob);
        frame.setTitle("Page Du Film");
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void initialiseFilm(String idFilm) {
    	ImageIcon tmpIcon = new ImageIcon(controller.getAffiche(idFilm));
    	if(tmpIcon.getIconHeight()==-1 || tmpIcon.getIconWidth()==-1){
    		tmpIcon = new ImageIcon("resources/posters/unknownPoster.jpg");
    	}
    	
        affiche = new JLabel(tmpIcon);
        
        titre = new JLabel(controller.getTitre(idFilm));
        
        reali = new JLabel(controller.getDirector(idFilm));
        
        act = new JList<String>(controller.getActorList(idFilm));
        
        gen = new JList<String>(controller.getGenreList(idFilm));
        
        
        dur = new JLabel(controller.getFilmLength(idFilm));
        
        resume = new JTextArea(controller.getSynopsis(idFilm));
    }
    
    private class AffichageListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == editer) {
                controller.editer(id);
            }
            if (event.getSource() == retour) {
            	frame.setVisible(false);
                frame.dispose();
            }
        }
        
    }

	@Override
	public void update(Observable o, Object arg) {
        frame.setVisible(false);
        frame.dispose();
	}
}
