package entity;

import java.util.List;

/**
 * Rappresenta un concerto, che è un tipo di evento.
 */
public class Concerto extends Evento{

    private List<Strumentista> strumentisti;
    private List<Cantante> cantanti;

    /**
     * Costruttore della classe Concerto.
     * 
     * @param id l'ID del concerto
     * @param data la data del concerto nel formato "yyyy-MM-dd"
     * @param luogo il luogo del concerto
     * @param costoTotale il costo totale del concerto
     * @param musicistiIncerti il numero di musicisti incerti del concerto
     * @param strumentisti la lista degli strumentisti del concerto
     * @param cantanti la lista dei cantanti del concerto
     */
    public Concerto(int id, String data, String luogo, float costoTotale, int musicistiIncerti,List<Strumentista> strumentisti, List<Cantante> cantanti) {
        super(id, data, luogo, costoTotale, musicistiIncerti);
        this.strumentisti = strumentisti;
        this.cantanti = cantanti;
    }

    /**
     * Restituisce la tipologia del concerto.
     * 
     * @return la tipologia del concerto
     */
    @Override
    public String getTipologia() {
        return "Concerto";
    }

    /**
     * Restituisce la lista degli strumentisti del concerto.
     * 
     * @return la lista degli strumentisti del concerto
     */
    public List<Strumentista> getStrumentisti() {
        return strumentisti;
    }

    /**
     * Imposta la lista degli strumentisti del concerto.
     * 
     * @param strumentisti la lista degli strumentisti del concerto
     */
    public void setStrumentisti(List<Strumentista> strumentisti) {
        this.strumentisti = strumentisti;
    }

    /**
     * Restituisce la lista dei cantanti del concerto.
     * 
     * @return la lista dei cantanti del concerto
     */
    public List<Cantante> getCantanti() {
        return cantanti;
    }

    /**
     * Imposta la lista dei cantanti del concerto.
     * 
     * @param cantanti la lista dei cantanti del concerto
     */
    public void setCantanti(List<Cantante> cantanti) {
        this.cantanti = cantanti;
    }
    
}
