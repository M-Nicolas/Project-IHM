package vue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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

public class Edition extends Fenetre implements Observer{
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
    
    private JFilePicker aff = new JFilePicker("","browse");
    private JTextField tit = new JTextField("Titre", 15);
    private JComboBox<String> reali = new JComboBox<String>();
    private JList<String> act = new JList<String>();
    private ArrayList<String> actID = new ArrayList<String>();
    private JList<String> act2 = new JList<String>();
    private ArrayList<String> actID2 = new ArrayList<String>();
    private JButton add = new JButton("ajouter");
    private JButton rm = new JButton("enlever");
    private JPanel gen = new JPanel();
    private JTextField dur = new JTextField("durée", 15);
    private JTextArea res = new JTextArea("Résumé");
    private JButton creer = new JButton("Editer");
    private JButton retour = new JButton("Retour");
    
    private String AncienID;
    
    /**
     * Constructeur de la classe Edition.
     */
    public Edition(String id) {
    	this.AncienID=id;
    	JLabel affiche = new JLabel("Affiche");
        controller = Launch.getController();
        tit = new JTextField(controller.getTitre(id));
        String[] realisatorName = controller.getAllRealisator();
        int index = 0;
        for (int i = 0; i< realisatorName.length; i++) {
            if (realisatorName[i] == controller.getDirector(id))
                index = i;
        }
        reali = new JComboBox<String>(realisatorName);
        reali.setSelectedIndex(index);
        res = new JTextArea(controller.getSynopsis(id));
        dur = new JTextField(controller.getFilmLengthMin(id));
        JLabel titre = new JLabel("Titre :");
        JLabel realisateur = new JLabel("Réalisateur :");
        JLabel acteurs = new JLabel("Avec :");
        JLabel genre = new JLabel("Genre :");
        JLabel duree = new JLabel("Durée :");
        JLabel resume = new JLabel("Résumé :");
        
        paneglob.setLayout(layout);
        
        String[] actorsName = controller.getActorNotInMovieList(id);
        String[] actorsNamesInMovie = controller.getActorInMovieList(id);
        act.setListData(actorsName);
        act2.setListData(actorsNamesInMovie);
        actID = controller.getActorNotInMovieID(id);
        actID2 = controller.getActorInMovieID(id);
        
        aff.setMode(JFilePicker.MODE_OPEN);
        aff.addFileTypeFilter(".jpg", "JPEG Images");
        aff.getFileChooser().setCurrentDirectory(new File("C:/"));

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
        retour.addActionListener(listener);
        createGenrePanel(id);
        
        initiate(affiche, titre, realisateur, acteurs, genre, duree, resume);
        addToPane(affiche, titre, realisateur, acteurs, genre, duree, resume);
        constraining(affiche, titre, realisateur, acteurs, genre, duree, resume);
        
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
            if (controller.checkGenre(genres.get(i), id)) {
                temporary.setSelected(true);
            }
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
    private void initiate(JLabel affiche, JLabel titre, JLabel realisateur,
            JLabel acteurs, JLabel genre, JLabel duree, JLabel resume) {
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
    private void addToPane(JLabel affiche, JLabel titre, JLabel realisateur,
            JLabel acteurs, JLabel genre, JLabel duree, JLabel resume) {
        paneglob.add(affiche);
        paneglob.add(aff);
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
        paneglob.add(retour);
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
        layout.putConstraint(SpringLayout.NORTH, affiche,5,SpringLayout.SOUTH,scroll);
        layout.putConstraint(SpringLayout.WEST, aff,5,SpringLayout.EAST,realisateur);
        layout.putConstraint(SpringLayout.NORTH, aff,5,SpringLayout.SOUTH,scroll);
        layout.putConstraint(SpringLayout.NORTH, creer,5,SpringLayout.SOUTH,aff);
        layout.putConstraint(SpringLayout.WEST, retour,5,SpringLayout.EAST,creer);
        layout.putConstraint(SpringLayout.NORTH, retour,5,SpringLayout.SOUTH,aff);
    }
    
    /**
     * Crée la frame.
     */
    private void generateFrame() {
        frame.setContentPane(paneglob);
        frame.setTitle("Page Edition");
        frame.setMinimumSize(new Dimension(850, 600));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        frame.setVisible(false);
        frame.dispose();
    }
    private class CreateListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
            if(e.getSource() == creer) {
                String titre = tit.getText();

                String realisateur =(String) reali.getSelectedItem();

                int duree=0;
                try{
                    duree = Integer.parseInt(dur.getText());
                }
                catch(NumberFormatException numberE){
                    JOptionPane.showMessageDialog(null, "Veuillez rentrer le nombre de minutes dans le champs duree");
                    return;//Arrete la methode
                }
                
                ArrayList<String> genres = new ArrayList<String>(); 
                for (Component jb : gen.getComponents()) {
                        JCheckBox tmp =(JCheckBox)jb;
                        if(tmp.isSelected()){
                            genres.add(tmp.getText());
                        }
                }
                controller.deleteMovie(controller.getTitre(AncienID));
                controller.CreerFilm(titre, aff.getSelectedFilePath(), realisateur, actID2, genres, duree, res.getText(), "E");
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
            if (e.getSource() == retour) {
            	frame.setVisible(false);
                frame.dispose();
            }
        }
    }
}