package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Strumentista
 */
public class Strumentista extends Musicista {

    private List<String> strumento;

    /**
     * Costruttore della classe Strumentista.
     * 
     * @param idMusicista l'ID del musicista
     * @param email l'email del musicista
     * @param nome il nome del musicista
     * @param cognome il cognome del musicista
     * @param password la password del musicista
     * @param telefono il numero di telefono del musicista
     * @param cachet il cachet del musicista
     * @param strumentoIn gli strumenti in cui è specializzato il musicista. Saranno salvati solo i primi 4 strumenti nel caso la lista ne contenga di più
     */
    public Strumentista(Integer idMusicista, String email, String nome, String cognome, String password, String telefono, Float cachet, List<String> strumentoIn) {
        super(idMusicista, email, nome, cognome, password, telefono, cachet);
        this.strumento = new ArrayList<>();
        for (int i =0 ; i< strumentoIn.size() && i<4; i++){
            this.strumento.add(strumentoIn.get(i));
        }
    }

    /**
     * Restituisce lo strumento suonato dal musicista.
     * 
     * @return lo strumento suonato dal musicista
     */
    public List<String> getStrumento() {
        return strumento;
    }

    /**
     * Imposta lo strumento suonato dal musicista.
     * 
     * @param strumento lo strumento suonato dal musicista da impostare
     */
    public void setStrumento(List<String> strumento) {
        this.strumento = strumento;
    }
}