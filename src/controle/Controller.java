package controle;

import java.util.ArrayList;
import java.util.Observer;

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
        new Affichage(film.getId());
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
    public void CreerFilm(String titre,String affiche,String realisateur,ArrayList<String> acteurs,ArrayList<String> genres,int duree,String resume){
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
    	
    	//ArrayList<Actor> a = manager.getAllActors();
    	//ArrayList<Genre> g = manager.getAllGenres();
    	Film newfilm = new Film("", titre, dir, actorNewFilm, genreNewFilm, duree,affiche, resume);
    	if(manager.addFilm(newfilm)){
    		System.out.println("##AJOUT FILM OK##");
    	}
    	else{
    		System.out.println("##FAIL##");
    	}
    }
   
    /**
     * cherche un film par id. retourne null si l'id est mauvais.
     * @param id
     * @return
     */
    public String getTitre(String id) {
        ArrayList<Film> list = manager.getAllFilms();
        String title = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
                title = list.get(i).getTitle();
            }
        }
        return title;
    }
    
    public String getAffiche(String id) {
        ArrayList<Film> list = manager.getAllFilms();
        String affiche = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
            	affiche = list.get(i).getPoster();
            }
        }
        return affiche;
    }
    
    public String getDirector(String id) {
    	ArrayList<Film> list = manager.getAllFilms();
        String director = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
            	director = list.get(i).getDirector().getFirstname()+" "+list.get(i).getDirector().getLastname();
            }
        }
        return director;
    }
    
    public String[] getActorList(String id) {
    	ArrayList<Film> list = manager.getAllFilms();
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
            	String[] actors = new String[list.get(i).getActors().size()];
            	int j=0;
            	for (Actor actor : list.get(i).getActors()) {
            		actors[j] = actor.getFirstname()+" "+actor.getLastname();
            		j++;
				}
            	return actors;
            }
        }
        return null;
    }
    
    public String[] getGenreList(String id) {
    	ArrayList<Film> list = manager.getAllFilms();
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
            	String[] genres = new String[list.get(i).getGenres().size()];
            	int j=0;
            	for (Genre genre : list.get(i).getGenres()) {
            		genres[j] = genre.getLabelFr();
            		j++;
				}
            	return genres;
            }
        }
        return null;
    }
    
    public String getFilmLength(String id) {
    	ArrayList<Film> list = manager.getAllFilms();
    	String duree = "";
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
            	duree = ""+list.get(i).getRuntime();
            }
        }
        return duree;
    }
    
    public String getSynopsis(String id) {
    	ArrayList<Film> list = manager.getAllFilms();
    	String resume = "";
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
            	resume = list.get(i).getSynopsis();
            }
        }
        return resume;
    }
    
    
    
    /**
     * Cherche le realisateur d'un film donné par id.
     * @param id
     * @return
     */
    public String getRealisateur(String id) {
        ArrayList<Film> list = manager.getAllFilms();
        String reali = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
                reali = list.get(i).getDirector().getLastname()+" "+list.get(i).getDirector().getFirstname();
            }
        }
        return reali;
    }
    
    /**
     * Cherche le résumé d'un film donné par id.
     * @param id
     * @return
     */
    public String getResume(String id) {
        ArrayList<Film> list = manager.getAllFilms();
        String resume = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
                resume = list.get(i).getSynopsis();
            }
        }
        return resume;
    }
    /**
     * Regarde si le genre proposé est bien présent dans le film.
     * @param genre
     * @param id
     * @return
     */
    public boolean checkGenre(String genre, String id) {
        boolean bool = false;
        ArrayList<Film> list = manager.getAllFilms();
        Film film = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == id) {
                film = list.get(i);
            }
        }
        String[] liste = getGenreList(film);
        for (int i = 0; i < liste.length - 1; i++) {
            if (liste[i].equals(genre)) {
                bool = true;
            }
        }
        return bool;
    }
}

