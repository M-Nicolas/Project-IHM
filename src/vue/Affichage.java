package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import controle.Controller;

import modele.Film;

public class Affichage extends Fenetre{
    private JFrame frame = new JFrame();
    private SpringLayout layout = new SpringLayout();
    private JPanel paneglob = new JPanel();
    private JPanel panemiddle = new JPanel();
    
    private JLabel affiche;
    private JLabel titre;
    private JLabel reali;
    private JList<String> act;
    private JList<String> gen;
    private JLabel dur;
    private JTextArea resume;
    
    
    /**
     * Constructeur de la classe Affichage qui crée la frame demandé.
     */
    public Affichage(Film film) {
        paneglob.setLayout(new BorderLayout());
        panemiddle.setLayout(layout);
        
        Font gras = new Font("Ubuntu", Font.BOLD, 12);
        Font plain = new Font("Ubuntu", Font.PLAIN, 12);
        JLabel realisateur = new JLabel("Réalisateur :");
        JLabel acteurs = new JLabel("Avec :");
        JLabel genre = new JLabel("Genre :");
        JLabel duree = new JLabel("Durée :");
        
        initialiseFilm(film);
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
        paneglob.add(resume, BorderLayout.PAGE_END);
    }
    
    private void generateFrame() {
        frame.setContentPane(paneglob);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void initialiseFilm(Film film) {
        
        affiche = new JLabel(new ImageIcon(Controller.getURL(film)));
        
        titre = new JLabel(Controller.getName(film));
        
        reali = new JLabel(Controller.getDirector(film));
        
        act = new JList<String>(Controller.getActorList(film));
        
        gen = new JList<String>(Controller.getGenreList(film));
        
        
        dur = new JLabel(Controller.getFilmLength(film));
        
        resume = new JTextArea(Controller.getSynopsis(film));
    }
}
