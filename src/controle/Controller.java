package controle;

import java.util.ArrayList;
import java.util.Observer; // Ne pas enlever!

import javax.swing.JOptionPane;

import modele.Actor;
import modele.Director;
import modele.Film;
import modele.Genre;
import modele.Manager;
import vue.Affichage;
import vue.Creation;
import vue.Edition;
import vue.Fenetre;

public class Controller {
    ArrayList<Fenetre> list;
    Manager manager;
    
    /**
     * Constructeur du Controlleur
     * @param list
     */
    public Controller(ArrayList<Fenetre> list) {
        this.list = list;
        manager = new Manager();
        manager.addObserver((Observer) list.get(0));
    }
    
    /**
     * Constructeur de Controller sans liste en entrée
     */
    public Controller() {
    	manager = new Manager();
    }
    
    /**
     * Méthode recherche permettant de chercher un film par titre
     * @param text
     * @return
     */
    public String[] search(String text) {
        ArrayList<Film> liste = manager.searchByTitle(text);
        String[] list = new String[liste.size()];
        for (int i = 0; i < liste.size(); i++) {
            list[i] = liste.get(i).getTitle();
        }
        return list;
    }
    
    /**
     * Méthode faisant apparaitre la vue Affichage
     * @param title
     */
    public void resultOfSearch(String title) {
    	Film film = getFilm(title);
    	manager.addObserver(new Affichage(film.getId()));
    }
    
    /**
     * Retourne un tableau contenant tout les noms de tout les acteurs.
     * @return tableau d'acteur
     */
    public String[] getAllActor(){
    	ArrayList<Actor> lstAct = manager.getAllActors();
    	String[] lstRetour=new String[lstAct.size()];
    	for (int i=0;i<lstAct.size();i++) {
			lstRetour[i] = lstAct.get(i).getFirstname()+
			        " "+lstAct.get(i).getLastname();
		}
    	return lstRetour;
    }
    
    public String[] getAllRealisator(){
    	ArrayList<Director> lstReal = manager.getAllDirectors();
    	String[] lstRetour=new String[lstReal.size()];
    	for (int i=0;i<lstReal.size();i++) {
			lstRetour[i] = lstReal.get(i).getFirstname()+
			        " "+lstReal.get(i).getLastname();
		}
    	return lstRetour;
    }
    
    /**
     * Retourne une ArrayList contenant tout les ID de tout les Acteurs.
     * @return ArrayList d'ID
     */
    public ArrayList<String> getAllActorID() {
        ArrayList<Actor> listAct = manager.getAllActors();
        ArrayList<String> listRetour = new ArrayList<String>();
        for (int i=0;i<listAct.size();i++) {
            listRetour.add(listAct.get(i).getId());
        }
        return listRetour;
    }
    
    /**
     * Ouvre une nouvelle fenetre Creation.
     */
    public void create() {
        manager.addObserver(new Creation());
    }
    
    public void editer(String id){
    	manager.addObserver(new Edition(id));
    }
    
    public void deleteMovie(String title){
    	Film film = getFilm(title);
    	manager.deleteFilm(searchFilmByID(film.getId()));
    }
    
    /** 
     * Méthode retournant le film recherché
     * Prérequis : le nom doit être le nom complet du film recherché.
     * @param name
     * @return
     */
    private Film getFilm(String name) {
        ArrayList<Film> liste = manager.searchByTitle(name);
        return liste.get(0);
    }
    
    /**
     * Retourne un tableau contenant la liste des genre
     * @param film
     * @return Array with the Genres
     */
    public String[] getGenreList(Film film) {
        ArrayList<Genre> genreList = film.getGenres();
        String[] list = new String[genreList.size()];
        for (int i = 0; i<genreList.size(); i++) {
            list[i] = genreList.get(i).getLabelFr();
        }
        return list;
    }
    
    /**
     * retourne une ArrayList de String décrivant les genres.
     * @return
     */
    public ArrayList<String> getAllGenre() {
        ArrayList<Genre> genres = manager.getAllGenres();
        ArrayList<String> genres2 = new ArrayList<String>();
        for (int i = 0; i<genres.size(); i++) {
            genres2.add(genres.get(i).getLabelFr());
        }
        return genres2;
    }
    
    /**
     * Création du film dans la base de données.
     * @param titre
     * @param affiche
     * @param realisateur
     * @param acteurs
     * @param genre
     * @param duree
     * @param resume
     */
    public void CreerFilm(String titre,String affiche,String realisateur,ArrayList<String> acteurs,ArrayList<String> genres,int duree,String resume, String mode){
    	ArrayList<Director> d = manager.getAllDirectors();
    	Director dir = null;
    	for (Director director : d) {
    	    if(realisateur.equals(director.getFirstname()+" "+director.getLastname())){
    	        dir=director;
    	    }
    	}
    	
    	ArrayList<Actor> actorNewFilm = new ArrayList<Actor>();
    	ArrayList<Actor> tmpAct = manager.getAllActors();
    	for (Actor actor : tmpAct) {
			if(acteurs.contains(actor.getId())){
				actorNewFilm.add(actor);
			}
		}
    	
    	ArrayList<Genre> genreNewFilm =  new ArrayList<Genre>();
    	
    	for (Genre g : manager.getAllGenres()) {
			if(genres.contains(g.getLabelFr())){
				genreNewFilm.add(g);
			}
		}
    	
    	Film newfilm = new Film("", titre, dir, actorNewFilm, genreNewFilm, duree,affiche, resume);
    	if(manager.addFilm(newfilm)){
    		if(mode=="C"){
    			JOptionPane.showMessageDialog(null, "L'ajout du film c'est bien passé");
    		}
    		else{
    			JOptionPane.showMessageDialog(null, "La mise a jour du film c'est bien passé");
    		}
    	}
    	else{
    		if(mode=="C"){
    			JOptionPane.showMessageDialog(null, "Probleme avec l'ajout du film veuillez reessayer.");
    		}
    		else{
    			JOptionPane.showMessageDialog(null, "Probleme avec la mise a jour du film veuillez reessayer.");
    		}
    	}
    }
    
    public void updateFilm(String titre,String affiche,String realisateur,ArrayList<String> acteurs,ArrayList<String> genres,int duree,String resume){
    	ArrayList<Director> d = manager.getAllDirectors();
    	Director dir = d.get(0);
    	
    	ArrayList<Actor> actorNewFilm = new ArrayList<Actor>();
    	ArrayList<Actor> tmpAct = manager.getAllActors();
    	for (Actor actor : tmpAct) {
			if(acteurs.contains(actor.getId())){
				actorNewFilm.add(actor);
			}
		}
    	
    	ArrayList<Genre> genreNewFilm =  new ArrayList<Genre>();
    	
    	for (Genre g : manager.getAllGenres()) {
			if(genres.contains(g.getLabelFr())){
				genreNewFilm.add(g);
			}
		}
    	
    	Film newfilm = new Film("", titre, dir, actorNewFilm, genreNewFilm, duree,affiche, resume);
    	
    	if(manager.updateFilm(newfilm)){
    		JOptionPane.showMessageDialog(null, "La mise a jour du film c'est bien passé");
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "Probleme avec la mise a jour du film veuillez reessayer.");
    	}
    }
    
   
    /**
     * cherche un film par id. retourne null si l'id est mauvais.
     * @param id
     * @return
     */
    public String getTitre(String id) {
        Film film = searchFilmByID(id);
        return film.getTitle();
    }
    
    public String getAffiche(String id) {
        Film film = searchFilmByID(id);
        return film.getPoster();
    }
    
    public String getDirector(String id) {
        Film film = searchFilmByID(id);
        return film.getDirector().getFirstname()+" "+film.getDirector().getLastname();
    }
    
    public String[] getActorList(String id) {
        Film film = searchFilmByID(id);
        int j = 0;
        String[] actors = new String[film.getActors().size()];
        for (Actor actor : film.getActors()) {
            actors[j] = actor.getFirstname()+" "+actor.getLastname();
            j++;
        }
        return actors;
    }
    
    public String[] getGenreList(String id) {
        Film film = searchFilmByID(id);
        String[] genres = new String[film.getGenres().size()];
        int j=0;
        for (Genre genre : film.getGenres()) {
            genres[j] = genre.getLabelFr();
            j++;
        }
        return genres;
    }
    
    public String getFilmLength(String id) {
        Film film = searchFilmByID(id);
        return (film.getRuntime()/60)+"h"+(film.getRuntime()%60);
    }
    
    public String getFilmLengthMin(String id) {
        Film film = searchFilmByID(id);
        return ""+film.getRuntime();
    }
    
    public String getSynopsis(String id) {
        Film film = searchFilmByID(id);
    	return film.getSynopsis();
    }
    
    /**
     * Regarde si le genre proposé est bien présent dans le film.
     * @param genre
     * @param id
     * @return
     */
    public boolean checkGenre(String genre, String id) {
        boolean bool = false;
        Film film = searchFilmByID(id);
        String[] liste = getGenreList(film);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < liste.length; i++) {
            list.add(liste[i]);
        }
        return list.contains(genre);
    }
    
    /**
     * Renvoie le film d'id donnée.
     * @param id
     * @return
     */
    public Film searchFilmByID(String id) {
        ArrayList<Film> list = manager.getAllFilms();
        Film film = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                film = list.get(i);
            }
        }
        return film;
    }
    
    /**
     * Crée la liste d'acteur présent dans le film.
     * @param id
     * @return
     */
    public String[] getActorInMovieList(String id) {
        Film film = searchFilmByID(id);
        String[] actorList = new String[film.getActors().size()];
        int i = 0;
        for (Actor actor : film.getActors()) {
            actorList[i] = actor.getFirstname()+" "+actor.getLastname();
            i++;
        }
        return actorList;
    }
    
    /**
     * Crée la liste des IDs des acteurs dans le film.
     * @param id
     * @return
     */
    public ArrayList<String> getActorInMovieID(String id) {
        Film film = searchFilmByID(id);
        ArrayList<String> actorIDList = new ArrayList<String>();
        for (Actor actor : film.getActors()) {
            actorIDList.add(actor.getId());
        }
        return actorIDList;
    }
    
    /**
     * Crée un tableau contenant les acteur n'ayant pas participé au film.
     * @param id
     * @return
     */
    public String[] getActorNotInMovieList(String id) {
        String[] actorInMovieList = getActorInMovieList(id);
        ArrayList<String> actorInMovieList2 = new ArrayList<String>();
        for (String actor : actorInMovieList) {
            actorInMovieList2.add(actor);
        }
        String[] allActorList = getAllActor();
        ArrayList<String> actorNotInMovieList = new ArrayList<String>();
        for (String actor : allActorList) {
            if (!actorInMovieList2.contains(actor))
                actorNotInMovieList.add(actor);
        }
        String[] returnList = new String[actorNotInMovieList.size()];
        int i = 0;
        for (String actor : actorNotInMovieList) {
            returnList[i] = actor;
            i++;
        }
        return returnList;
    }
    
    /**
     * Crée une ArrayList<String> contennant les ids des acteur n'ayant pas
     * participé au film.
     * @param id
     * @return
     */
    public ArrayList<String> getActorNotInMovieID(String id) {
        ArrayList<String> actorInMovieList = getActorInMovieID(id);
        ArrayList<String> allActorList = getAllActorID();
        ArrayList<String> actorNotInMovieID = new ArrayList<String>();
        for (String actor : allActorList) {
            if (!actorInMovieList.contains(actor)) {
                actorNotInMovieID.add(actor);
            }
        }
        return actorNotInMovieID;
    }
}

