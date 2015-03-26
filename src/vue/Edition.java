package vue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.plaf.ActionMapUIResource;

import controle.Controller;
import controle.Launch;

public class Edition extends Fenetre{
    private Controller controller;
    private JFrame frame = new JFrame();
    private JScrollPane scroll = new JScrollPane();
    private JPanel paneglob = new JPanel();
    private SpringLayout layout = new SpringLayout();
    private Font gras = new Font("Ubuntu", Font.BOLD, 12);
    private Font plain = new Font("Ubuntu", Font.PLAIN, 12);
    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane scrollPane2 = new JScrollPane();
    private JScrollPane scrollPane3 = new JScrollPane();
    
    private JTextField tit;
    private JTextField reali;
    private JList<String> act = new JList<String>();
    private ArrayList<String> actID = new ArrayList<String>();
    private JList<String> act2 = new JList<String>();
    private ArrayList<String> actID2 = new ArrayList<String>();
    private JButton add = new JButton("ajouter");
    private JButton rm = new JButton("enlever");
    private JPanel gen = new JPanel();
    private JTextField dur = new JTextField("durée", 15);
    private JTextArea res;
    private JButton creer = new JButton("Créer");
    
    /**
     * Constructeur de la classe Edition.
     */
    public Edition(String id) {
        controller = Launch.getController();
        tit = new JTextField(controller.getTitre(id));
        reali = new JTextField(controller.getRealisateur(id));
        res = new JTextArea(controller.getResume(id));
        JLabel titre = new JLabel("Titre :");
        JLabel realisateur = new JLabel("Réalisateur :");
        JLabel acteurs = new JLabel("Avec :");
        JLabel genre = new JLabel("Genre :");
        JLabel duree = new JLabel("Durée :");
        JLabel resume = new JLabel("Résumé :");
        
        paneglob.setLayout(layout);
        
        String[] actorsNames = controller.getAllActor();
        act.setListData(actorsNames);
        actID = controller.getAllActorID();

        scroll.add(res);
        scrollPane.add(act);
        scrollPane2.add(act2);
        scrollPane3.add(paneglob);
        scroll.setPreferredSize(new Dimension(300, 150));
        scrollPane.setPreferredSize(new Dimension(150, 100));
        scrollPane2.setPreferredSize(new Dimension(150,100));
        scrollPane3.setPreferredSize(new Dimension(600,500));
        
        CreateListener listener = new CreateListener();
        creer.addActionListener(listener);
        add.addActionListener(listener);
        rm.addActionListener(listener);
        createGenrePanel(id);
        
        initiate(titre, realisateur, acteurs, genre, duree, resume);
        addToPane(titre, realisateur, acteurs, genre, duree, resume);
        constraining(titre, realisateur, acteurs, genre, duree, resume);
        
        scroll.setViewportView(res);
        scrollPane.setViewportView(act);
        scrollPane2.setViewportView(act2);
        scrollPane3.setViewportView(paneglob);
        
        generateFrame();
    }
    
    /**
     * Création du panel de genres.
     */
    private void createGenrePanel(String id) {
        ArrayList<String> genres = controller.getAllGenre();
        for (int i = 0; i<genres.size(); i++) {
            JCheckBox temporary = new JCheckBox(genres.get(i));
            if (controller.checkGenre(genres.get(i), id))
                temporary.setSelected(true);
            temporary.setFocusable(true);
            gen.add(temporary);
            gen.setLayout(new GridLayout(5,4));
        }
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
        act2.setFont(plain);
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
    private void addToPane(JLabel titre, JLabel realisateur,
            JLabel acteurs, JLabel genre, JLabel duree, JLabel resume) {
        paneglob.add(titre);
        paneglob.add(tit);
        paneglob.add(realisateur);
        paneglob.add(reali);
        paneglob.add(acteurs);
        paneglob.add(scrollPane);
        paneglob.add(scrollPane2);
        paneglob.add(add);
        paneglob.add(rm);
        paneglob.add(genre);
        paneglob.add(gen);
        paneglob.add(duree);
        paneglob.add(dur);
        paneglob.add(resume);
        paneglob.add(scroll);
        paneglob.add(creer);
    }
    
    private class CreateListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == creer) {
                String titre = tit.getText();
                System.out.println(tit.getText());
                String realisateur =reali.getText();
                System.out.println(reali.getText());
                String test = act.getSelectedValue();
                System.out.println(test);
                //for(int i = 0 ; i<;i++){
                //System.out.println();act2
                //System.out.println(gen.getText());
                System.out.println(dur.getText());
                int duree = 120;
                System.out.println(res.getText());
                //controller.CreerFilm(titre, affiche, realisateur,
                        //actID2, gen.getText(), duree, res.getText());
            }
            
            if (e.getSource() == add) {
                //récupére les informations de la première liste :
                ListModel<String> model = act.getModel();
                int[] selectedIndex = act.getSelectedIndices();
                
                //les ajoute dans la seconde :
                for (int i = 0; i<selectedIndex.length; i ++) {
                    actID2.add(actID.get(selectedIndex[i]));
                }
                
                //Création d'un tableau qui remplacera le précédent de la
                //seconde liste:
                ListModel<String> model2 = act2.getModel();
                String[] list = new String[model2.getSize() + selectedIndex.length];
                for (int i = 0; i<model2.getSize(); i++) {
                    list[i] = model2.getElementAt(i);
                }
                //ajout des nouvel élément :
                for (int i = 0; i<selectedIndex.length; i ++) {
                    list[model2.getSize()+i] = model.getElementAt(selectedIndex[i]);
                }
                
                //suppression de la première liste :
                try {
                    actID.remove(selectedIndex);
                    String[] liste = new String[model.getSize()
                                                -selectedIndex.length];
                    int j = 0;
                    for (int i = 0; i<model.getSize(); i++) {
                        boolean passed = true;
                        for (int k = 0; k < selectedIndex.length; k++) {
                            if (i == selectedIndex[k]) {
                                passed = false;
                            }
                        }
                        if (passed == true) {
                            liste[j] = model.getElementAt(i);
                            j++;
                        }
                    }
                    act.setListData(liste);
                } catch (IndexOutOfBoundsException e1) {}
                
                act2.setListData(list);
            }
            
            if (e.getSource() == rm) {
                //recuppération de la liste et de l'indice :
                ListModel<String> model = act2.getModel();
                int[] selectedIndex = act2.getSelectedIndices();
                
                //ajout des éléments dans la première liste :
                for (int i = 0; i<selectedIndex.length; i++)
                    actID.add(actID2.get(selectedIndex[i]));
                
                //Création d'un tableau qui remplacera le précédent de la
                //seconde liste:
                ListModel<String> model2 = act.getModel();
                String[] list = new String[model2.getSize() + selectedIndex.length];
                for (int i = 0; i<model2.getSize(); i++)
                    list[i] = model2.getElementAt(i);
                
                for (int i = 0; i<selectedIndex.length; i++)
                    list[model2.getSize() + i] = model.getElementAt(selectedIndex[i]);
                act.setListData(list);
                
                //Suppréssion des éléments :
                try {
                    actID2.remove(selectedIndex);
                    String[] liste = new String[model.getSize()
                                                -selectedIndex.length];
                    int j = 0;
                    for (int i = 0; i<model.getSize(); i++) {
                        boolean passed = false;
                        for (int k = 0; k < selectedIndex.length; k++) {
                            if (i == selectedIndex[k]) {
                                passed = true;
                            }
                        }
                        if (passed == false) {
                            liste[j] = model.getElementAt(i);
                            j++;
                        }
                    }
                    act2.setListData(liste);
                } catch (IndexOutOfBoundsException e1) {}
            }
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
    private void constraining(JLabel titre, JLabel realisateur,
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
        layout.putConstraint(SpringLayout.WEST, rm,5,SpringLayout.EAST,scrollPane);
        layout.putConstraint(SpringLayout.NORTH, rm,5,SpringLayout.SOUTH,add);
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
        layout.putConstraint(SpringLayout.NORTH, creer,5,SpringLayout.SOUTH,scroll);
    }
    /**
     * Crée la frame.
     */
    private void generateFrame() {
        frame.setContentPane(paneglob);
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}