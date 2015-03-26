package controle;

import java.util.ArrayList;

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
            System.out.println(liste.get(i).getTitle());
        }
        return list;
    }
    
    /**
     * Méthode faisant apparaitre la vue Affichage
     * @param title
     */
    public void resultOfSearch(String title) {
        Film film = getFilm(title);
        new Affichage(film);
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
        new Creation();
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
     * Retourne le lien vers la pochette du film
     * @param film
     * @return URL
     */
    public String getURL(Film film) {
        String name = film.getTitle();
        char[] affi2 = name.toCharArray();
        String URL = "";
        for (int i = 0; i <affi2.length; i++) {
            if (affi2[i] == ' ') {
            } else {
                URL+=Character.toLowerCase(affi2[i]);
            }
        }
        return "resources/posters/"+ URL + ".jpg";
    }
    
    /**
     * Retourne le titre du film
     * @param film
     * @return Title
     */
    public String getName(Film film) {
        return film.getTitle();
    }
    
    /**
     * Retourne le Directeur du film
     * @param film
     * @return Director Name
     */
    public String getDirector(Film film) {
        Director director = film.getDirector();
        return director.getFirstname() + " " +director.getLastname();
    }
    
    /**
     * Retourne un tableau contenant la liste des acteurs
     * @param film
     * @return Array with the actors names
     */
    public String[] getActorList(Film film) {
        ArrayList<Actor> liste = film.getActors();
        String[] list = new String[liste.size()];
        for (int i = 0; i<liste.size(); i++) {
            list[i] = liste.get(i).getFirstname() + " "
                    + liste.get(i).getLastname();
        }
        return list;
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
     * Retourne la durée du film au format hh mm
     * @param film
     * @return film length
     */
    public String getFilmLength(Film film) {
        int duree = film.getRuntime();
        return (duree/60)+"h "+(duree - (duree/60)*60);
    }
    
    /**
     * Retourne le résumé du film
     * @param film
     * @return film's synopsis
     */
    public String getSynopsis(Film film) {
        return film.getSynopsis();
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
    public void CreerFilm(String titre,String affiche,String realisateur,ArrayList<String> acteurs,ArrayList<String> genre,int duree,String resume){
    	ArrayList<Director> d = manager.getAllDirectors();
    	Director dir = d.get(0);
    	ArrayList<Actor> a = manager.getAllActors();
    	ArrayList<Genre> g = manager.getAllGenres();
    	Film newfilm = new Film("", titre, dir, a, g, duree, affiche, resume);
    	if(manager.addFilm(newfilm)){
    		System.out.println("##AJOUT FILM OK##");
    	}
    	else{
    		System.out.println("##FAIL##");
    	}
    }
}

