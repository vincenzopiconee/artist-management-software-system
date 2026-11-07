package entity;

/**
 * Cantante
 */
public class Cantante extends Musicista {
    
    public static enum GenereMusicale{
        CLASSICA, LEGGERA, POP, JAZZ
    }
    private GenereMusicale genereMusicale;
    private String registroVocale = null;

    /**
     * Costruttore della classe Cantante.
     * 
     * @param idMusicista l'ID del musicista
     * @param email l'email del musicista
     * @param nome il nome del musicista
     * @param cognome il cognome del musicista
     * @param password la password del musicista
     * @param telefono il numero di telefono del musicista
     * @param cachet il cachet del musicista
     */
    public Cantante(Integer idMusicista, String email, String nome, String cognome, String password, String telefono, Float cachet) {
        super(idMusicista, email, nome, cognome, password, telefono, cachet);
    }
    /**
     * Imposta il genere musicale in cui è specializzato il cantante.
     * Il cantante di musica classica necessita anche di un registro vocale.
     * 
     * @param registroVocale il registro vocale del cantante
     */
    public void setCantanteClassico(String registroVocale){
        this.genereMusicale = GenereMusicale.CLASSICA;
        this.registroVocale = registroVocale;
    }
    
    /**
     * Imposta il genere musicale in cui è specializzato il cantante.
     */
    public void setCantanteLeggero(){
        this.genereMusicale = GenereMusicale.LEGGERA;
    }

    /**
     * 
     * Imposta il genere musicale in cui è specializzato il cantante.
     */
    public void setCantantePop(){
        this.genereMusicale = GenereMusicale.POP;
    }

    /**
     * 
     * Imposta il genere musicale in cui è specializzato il cantante.
     */
    public void setCantanteJazz(){
        this.genereMusicale = GenereMusicale.JAZZ;
    }

    /**
     * Restituisce il registro vocale del cantante.
     * Nel caso il cantante non sia specializzato in musica classica, restituisce null.
     * 
     * @return il registro vocale del cantante
     */
    public String getRegistroVocale(){
        if (this.genereMusicale == GenereMusicale.CLASSICA) {
            return registroVocale;
        } else {
            return null;
        }
    }
    
    /**
     * Restituisce il genere musicale in cui è specializzato il cantante.
     * 
     * @return il genere musicale in cui è specializzato il cantante
     */
    public GenereMusicale getGenereMusicale() {
        return genereMusicale;

    }
}
