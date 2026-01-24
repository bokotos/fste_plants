package model;

/**
 * Classe Model représentant une Plante dans l'application.
 */
public class Plant {
    private int id;
    private String nom;
    private String espece;
    private String etatSante;
    private int besoinEau;
    private String datePlantation;
    private int idCategorie;
    private String nomCategorie; // Utile pour l'affichage dans les tableaux

    // Constructeur vide
    public Plant() {}

    // Constructeur complet
    public Plant(int id, String nom, String espece, String etatSante, int besoinEau, String datePlantation, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.espece = espece;
        this.etatSante = etatSante;
        this.besoinEau = besoinEau;
        this.datePlantation = datePlantation;
        this.idCategorie = idCategorie;
    }

    // --- GETTERS ET SETTERS ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEspece() { return espece; }
    public void setEspece(String espece) { this.espece = espece; }

    public String getEtatSante() { return etatSante; }
    public void setEtatSante(String etatSante) { this.etatSante = etatSante; }

    public int getBesoinEau() { return besoinEau; }
    public void setBesoinEau(int besoinEau) { this.besoinEau = besoinEau; }

    public String getDatePlantation() { return datePlantation; }
    public void setDatePlantation(String datePlantation) { this.datePlantation = datePlantation; }

    public int getIdCategorie() { return idCategorie; }
    public void setIdCategorie(int idCategorie) { this.idCategorie = idCategorie; }

    public String getNomCategorie() { return nomCategorie; }
    public void setNomCategorie(String nomCategorie) { this.nomCategorie = nomCategorie; }

    @Override
    public String toString() {
        return nom + " (" + espece + ")";
    }
}