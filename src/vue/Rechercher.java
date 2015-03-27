package vue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;

import controle.Controller;

public class Rechercher extends Fenetre implements Observer{
    private JFrame frame = new JFrame();
    private JPanel paneGlob = new JPanel();
    private JButton search = new JButton("Rechercher");
    private JList<String> list = new JList<String>();
    private JTextField searchArea = new JTextField("Film à chercher");
    private JButton validate = new JButton("Valider");
    private JButton supprimer = new JButton("Supprimer");
    private Controller controller;
    private JButton create = new JButton("Créer un nouveau Film");
    private JScrollPane scrollPane = new JScrollPane();
    private JButton quitter = new JButton("Quitter");
    
    /**
     * Créer une instance de Rechercher.
     */
    public Rechercher() {
        SpringLayout layout = new SpringLayout();
        paneGlob.setLayout(layout);
        paneGlob.add(searchArea);
        searchArea.setPreferredSize(new Dimension(150, 25));
        paneGlob.add(search);
        scrollPane.add(list);
        paneGlob.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(150, 100));
        paneGlob.add(validate);
        paneGlob.add(supprimer);
        paneGlob.add(create);
        paneGlob.add(quitter);
        
        ActionListener listener = new SearchListener();
        ArrayList<Component> liste = new ArrayList<Component>();
        fillList(liste);
        for (int i = 1; i < liste.size(); i++) {
            if (i == 1)
                layout.putConstraint(SpringLayout.WEST, liste.get(i),5,
                        SpringLayout.EAST, searchArea);
            else if (i == 2) {
                layout.putConstraint(SpringLayout.NORTH, liste.get(i),
                        5,SpringLayout.SOUTH, liste.get(i-1));
            } else {
                layout.putConstraint(SpringLayout.NORTH, liste.get(i),
                        5,SpringLayout.SOUTH, liste.get(i-2));
                layout.putConstraint(SpringLayout.WEST, liste.get(i),
                        5,SpringLayout.EAST, searchArea);
            }
        }
        
        
        search.addActionListener(listener);
        validate.addActionListener(listener);
        supprimer.addActionListener(listener);
        create.addActionListener(listener);
        quitter.addActionListener(listener);
        searchArea.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	if(searchArea.getText().equals("Film à chercher")){
            		searchArea.setText("");
            	}
            }
        });
        scrollPane.setViewportView(list);
        
        
        frame.setContentPane(paneGlob);
        frame.setMinimumSize(new Dimension(400, 225));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    /**
     * Méthode retournant la String 
     * @return
     */
    public String selectedMovie() {
        ListModel<String> model = list.getModel();
        int selectedIndex = list.getSelectedIndex();
        return (String) model.getElementAt(selectedIndex);
    }
    
    /**
     * Listener de la classe searchListener
     */
    private class SearchListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == search) {
                list.setListData(controller.search(searchArea.getText()));
            } else if (e.getSource() == validate) {
                controller.resultOfSearch(selectedMovie());
            } else if (e.getSource() == create) {
                controller.create();
            } else if(e.getSource() == supprimer ){
            	controller.deleteMovie(selectedMovie());
            } else if(e.getSource() == quitter) {
                //TODO : Faire la méthode qui quitte l'application.
            }
            
        }
    }
    
    /**
     * Remplie la liste avec les éléments du panel.
     * @param liste
     */
    private void fillList(ArrayList<Component> liste) {
        liste.add(searchArea);
        liste.add(search);
        liste.add(scrollPane);
        liste.add(validate);
        liste.add(create);
        
        liste.add(supprimer);
        liste.add(quitter);
    }

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("OBSERVABLE DECLENCHE");
		list.setListData(controller.search(""));//recharge la vue
	}
}
