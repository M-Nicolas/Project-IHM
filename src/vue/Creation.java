package vue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.plaf.ActionMapUIResource;

import controle.Controller;
import controle.Launch;

public class Creation extends Fenetre{
	Controller controller;
    JFrame frame = new JFrame();
    JScrollPane scroll = new JScrollPane();
    JPanel paneglob = new JPanel();
    SpringLayout layout = new SpringLayout();
    Font gras = new Font("Ubuntu", Font.BOLD, 12);
    Font plain = new Font("Ubuntu", Font.PLAIN, 12);
    JScrollPane scrollPane = new JScrollPane();
    JScrollPane scrollPane2 = new JScrollPane();
    
    JTextField aff = new JTextField("image/edge.jpg", 15);
    JTextField tit = new JTextField("Titre", 15);
    JTextField reali = new JTextField("réalisateur",15);
    JList<String> act = new JList<String>();
    ArrayList<String> actID = new ArrayList<String>();
    JList<String> act2 = new JList<String>();
    JButton add = new JButton("ajouter");
    JTextField gen = new JTextField("Genre",15);
    JTextField dur = new JTextField("durée", 15);
    JTextArea res = new JTextArea("Résumé");
    JButton creer = new JButton("Créer");
    
    /**
     * Constructeur de la classe Edition.
     */
    public Creation() {
    	
    	controller = Launch.getController();
    	
        JLabel affiche = new JLabel("affiche");
        JLabel titre = new JLabel("Titre :");
        JLabel realisateur = new JLabel("Réalisateur :");
        JLabel acteurs = new JLabel("Avec :");
        JLabel genre = new JLabel("Genre :");
        JLabel duree = new JLabel("Durée :");
        JLabel resume = new JLabel("Résumé :");
        
        paneglob.setLayout(layout);
        scroll.add(res);
        
        String[] actorsNames = controller.getAllActor();
        act.setListData(actorsNames);
        actID = controller.getAllActorID();
        
        MouseAdapter mouseAdapt = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getSource() == aff && aff.getText().equals("image/edge.jpg")){
                    aff.setText("");
                }
                if(e.getSource() == tit && tit.getText().equals("Titre")){
                    tit.setText("");
                }
                if(e.getSource() == reali && reali.getText().equals("réalisateur")){
                    reali.setText("");
                }
                if(e.getSource() == gen && gen.getText().equals("Genre")){
                    gen.setText("");
                }
                if (e.getSource() == res && res.getText().equals("Résumé")){
                    res.setText("");
                }
                if(e.getSource() == dur && dur.getText().equals("durée")){
                    dur.setText("");
                }
            }
        };

        aff.addMouseListener(mouseAdapt);
        tit.addMouseListener(mouseAdapt);
        reali.addMouseListener(mouseAdapt);
        gen.addMouseListener(mouseAdapt);
        res.addMouseListener(mouseAdapt);
        dur.addMouseListener(mouseAdapt);
        
        scrollPane.add(act);
        scrollPane2.add(act2);
        scrollPane.setPreferredSize(new Dimension(150, 100));
        scrollPane2.setPreferredSize(new Dimension(150,100));
        
        creer.addActionListener(new CreateListener());
        
        initiate(titre, realisateur, acteurs, genre, duree, resume);
        addToPane(affiche, titre, realisateur, acteurs, genre, duree, resume);
        constraining(affiche, titre, realisateur, acteurs, genre, duree, resume);
        
        scroll.setViewportView(res);
        scrollPane.setViewportView(act);
        scrollPane2.setViewportView(act2);
        
        generateFrame();
    }
    
    /**
     * Met en forme les textes de la fenetre.
     * @param titre
     * @param realisateur
     * @param acteurs
     * @param genre
     * @param duree
     * @param resume
     */
    private void initiate(JLabel titre, JLabel realisateur, JLabel acteurs,
            JLabel genre, JLabel duree, JLabel resume) {
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        realisateur.setFont(gras);
        reali.setFont(plain);
        titre.setFont(gras);
        act.setOpaque(false);
        act.setFont(plain);
        genre.setFont(gras);
        gen.setFont(plain);
        duree.setFont(gras);
        dur.setFont(plain);
        resume.setFont(gras);
        res.setSize(500, 300);
        res.setLineWrap(true);
        res.setWrapStyleWord(true);
    }
    
    /**
     * Ajoute au panel les composants de la fenetre
     * @param affiche
     * @param titre
     * @param realisateur
     * @param acteurs
     * @param genre
     * @param duree
     * @param resume
     */
    private void addToPane(JLabel affiche, JLabel titre, JLabel realisateur,
            JLabel acteurs, JLabel genre, JLabel duree, JLabel resume) {
        paneglob.add(titre);
        paneglob.add(tit);
        paneglob.add(affiche);
        paneglob.add(aff);
        paneglob.add(realisateur);
        paneglob.add(reali);
        paneglob.add(acteurs);
        paneglob.add(scrollPane);
        paneglob.add(scrollPane2);
        paneglob.add(add);
        paneglob.add(genre);
        paneglob.add(gen);
        paneglob.add(duree);
        paneglob.add(dur);
        paneglob.add(resume);
        res.setPreferredSize(new Dimension(150, 150));
        scroll.add(res);
        paneglob.add(scroll);
        paneglob.add(creer);
    }
    
    private class CreateListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	/*
        	JLabel affiche, JLabel titre, JLabel realisateur,
            JLabel acteurs, JLabel genre, JLabel duree, JLabel resume*/
        	String titre = tit.getText();
        	System.out.println(tit.getText());
        	String affiche = aff.getText();
        	System.out.println(aff.getText());
        	String realisateur =reali.getText();
        	System.out.println(reali.getText());
        	String test = act.getSelectedValue();
        	System.out.println(test);
        	//for(int i = 0 ; i<;i++){
        	//System.out.println();act
        	System.out.println(gen.getText());
        	System.out.println(dur.getText());
        	int duree = 120;//Integer.parseInt(dur.getText());
        	System.out.println(res.getText());
        	ArrayList<String> acts = new ArrayList<String>();
        	acts.add("test");
        	controller.CreerFilm(titre, affiche, realisateur, acts, gen.getText(), duree, res.getText());
        	//Controller.
        }
    }
    
    /**
     * Contraint les component de la fenetre.
     * @param affiche
     * @param titre
     * @param realisateur
     * @param acteurs
     * @param genre
     * @param duree
     * @param resume
     */
    private void constraining(JLabel affiche, JLabel titre, JLabel realisateur,
            JLabel acteurs, JLabel genre, JLabel duree, JLabel resume) {
        layout.putConstraint(SpringLayout.WEST, tit,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, realisateur,5,SpringLayout.SOUTH,tit);
        layout.putConstraint(SpringLayout.WEST, reali,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, reali,5,SpringLayout.SOUTH,tit);
        layout.putConstraint(SpringLayout.NORTH, acteurs,5,SpringLayout.SOUTH,reali);
        layout.putConstraint(SpringLayout.WEST, scrollPane,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, scrollPane,5,SpringLayout.SOUTH,reali);
        layout.putConstraint(SpringLayout.WEST, add,5,SpringLayout.EAST,scrollPane);
        layout.putConstraint(SpringLayout.NORTH, add,5,SpringLayout.SOUTH,realisateur);
        layout.putConstraint(SpringLayout.WEST, scrollPane2, 5, SpringLayout.EAST, add);
        layout.putConstraint(SpringLayout.NORTH, scrollPane2,5,SpringLayout.SOUTH,realisateur);
        layout.putConstraint(SpringLayout.NORTH, genre,5,SpringLayout.SOUTH,scrollPane);
        layout.putConstraint(SpringLayout.WEST, gen,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, gen,5,SpringLayout.SOUTH,scrollPane);
        layout.putConstraint(SpringLayout.NORTH, duree,5,SpringLayout.SOUTH,gen);
        layout.putConstraint(SpringLayout.WEST, dur,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, dur,5,SpringLayout.SOUTH,gen);
        layout.putConstraint(SpringLayout.NORTH, resume,5,SpringLayout.SOUTH,dur);
        layout.putConstraint(SpringLayout.WEST, scroll,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, scroll,5,SpringLayout.SOUTH,dur);
        layout.putConstraint(SpringLayout.NORTH, affiche,5,SpringLayout.SOUTH, scroll);
        layout.putConstraint(SpringLayout.WEST, aff,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, aff,5,SpringLayout.SOUTH,scroll);
        layout.putConstraint(SpringLayout.NORTH, creer,5,SpringLayout.SOUTH,affiche);
    }
    /**
     * Crée la frame.
     */
    private void generateFrame() {
        frame.setContentPane(paneglob);
        frame.setMinimumSize(new Dimension(400, 500));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
