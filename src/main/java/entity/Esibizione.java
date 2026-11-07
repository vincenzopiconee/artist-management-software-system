package entity;

import java.util.List;

/**
 * Questa classe rappresenta un'Esibizione, che è un tipo di Evento.
 * Contiene informazioni sull'Esibizione, come il cantante e gli strumentisti coinvolti.
 */
public class Esibizione extends Evento{

    private Cantante cantante;
    private List<Strumentista> strumentisti;
    
    /**
     * Costruttore per la classe Esibizione.
     * 
     * @param id l'ID dell'Esibizione
     * @param data la data dell'Esibizione nel formato "yyyy-MM-dd"
     * @param luogo il luogo dell'Esibizione
     * @param costoTotale il costo totale dell'Esibizione
     * @param musicistiIncerti il numero di musicisti incerti nell'Esibizione
     * @param cantante il cantante dell'Esibizione
     * @param strumentisti la lista degli strumentisti dell'Esibizione
     */
    public Esibizione(int id, String data, String luogo, float costoTotale, int musicistiIncerti, Cantante cantante, List<Strumentista> strumentisti) {
        super(id, data, luogo, costoTotale, musicistiIncerti);
        this.cantante = cantante;
        this.strumentisti = strumentisti;
    }

    /**
     * Restituisce la tipologia dell'Esibizione.
     * 
     * @return la tipologia dell'Esibizione
     */
    @Override
    public String getTipologia() {
        return "Esibizione";
    }

    /**
     * Restituisce il cantante dell'Esibizione.
     * 
     * @return il cantante dell'Esibizione
     */
    public Cantante getCantante() {
        return cantante;
    }

    /**
     * Imposta il cantante dell'Esibizione.
     * 
     * @param cantante il cantante da impostare
     */
    public void setCantante(Cantante cantante) {
        this.cantante = cantante;
    }

    /**
     * Restituisce la lista degli strumentisti dell'Esibizione.
     * 
     * @return la lista degli strumentisti dell'Esibizione
     */
    public List<Strumentista> getStrumentisti() {
        return strumentisti;
    }

    /**
     * Imposta la lista degli strumentisti dell'Esibizione.
     * 
     * @param strumentisti la lista degli strumentisti da impostare
     */
    public void setStrumentisti(List<Strumentista> strumentisti) {
        this.strumentisti = strumentisti;
    }
}
