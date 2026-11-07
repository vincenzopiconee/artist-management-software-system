package entity;

/**
 * Musicista
 */
public abstract class Musicista {
    
    private Integer idMusicista;
    private String email;
    private String nome;
    private String cognome;
    private String password;
    private String telefono;
    private Float cachet;

    /**
     * Costruttore della classe Musicista.
     * 
     * @param idMusicista l'ID del musicista
     * @param email l'email del musicista
     * @param nome il nome del musicista
     * @param cognome il cognome del musicista
     * @param password la password del musicista
     * @param telefono il numero di telefono del musicista
     * @param cachet il cachet del musicista
     */
    public Musicista(Integer idMusicista, String email, String nome, String cognome, String password, String telefono, Float cachet) {
        this.idMusicista = idMusicista;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.telefono = telefono;
        this.cachet = cachet;
    }

    /**
     * Restituisce l'ID del musicista.
     * 
     * @return l'ID del musicista
     */
    public Integer getIdMusicista() {
        return idMusicista;
    }

    /**
     * Imposta l'ID del musicista.
     * 
     * @param idMusicista l'ID del musicista da impostare
     */
    public void setIdMusicista(Integer idMusicista) {
        this.idMusicista = idMusicista;
    }

    /**
     * Restituisce l'email del musicista.
     * 
     * @return l'email del musicista
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email del musicista.
     * 
     * @param email l'email del musicista da impostare
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il nome del musicista.
     * 
     * @return il nome del musicista
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del musicista.
     * 
     * @param nome il nome del musicista da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome del musicista.
     * 
     * @return il cognome del musicista
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome del musicista.
     * 
     * @param cognome il cognome del musicista da impostare
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce la password del musicista.
     * 
     * @return la password del musicista
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password del musicista.
     * 
     * @param password la password del musicista da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce il numero di telefono del musicista.
     * 
     * @return il numero di telefono del musicista
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il numero di telefono del musicista.
     * 
     * @param telefono il numero di telefono del musicista da impostare
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Restituisce il cachet del musicista.
     * 
     * @return il cachet del musicista
     */
    public Float getCachet() {
        return cachet;
    }

    /**
     * Imposta il cachet del musicista.
     * 
     * @param cachet il cachet del musicista da impostare
     */
    public void setCachet(Float cachet) {
        this.cachet = cachet;
    }
}