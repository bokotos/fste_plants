package model;

/**
 * Classe Model représentant un Utilisateur (Admin) dans le système.
 * Correspond à la structure de votre table SQL (id, nom, email, password).
 */
public class User {
    private int id;
    private String nom;
    private String email;
    private String password;

    // Constructeur par défaut (vide)
    public User() {
    }

    // Constructeur complet
    public User(int id, String nom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    // --- GETTERS ET SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Méthode pour faciliter l'affichage ou le débogage
    @Override
    public String toString() {
        return "User [ID=" + id + ", Nom=" + nom + ", Email=" + email + "]";
    }
}