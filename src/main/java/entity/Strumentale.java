package entity;

import java.util.List;

/**
 * Questa classe rappresenta un evento strumentale.
 * Estende la classe Evento e aggiunge la lista dei convocati.
 */
public class Strumentale extends Evento{

    private List<Strumentista> convocati;

    /**
     * Costruttore della classe Strumentale.
     * 
     * @param id l'ID dell'evento
     * @param data la data dell'evento nel formato "yyyy-MM-dd"
     * @param luogo il luogo dell'evento
     * @param costoTotale il costo totale dell'evento
     * @param musicistiIncerti il numero di musicisti incerti
     * @param convocati la lista dei convocati per l'evento
     */
    public Strumentale(int id, String data, String luogo, float costoTotale, int musicistiIncerti, List<Strumentista> convocati) {
        super(id, data, luogo, costoTotale, musicistiIncerti);
        this.convocati = convocati;
    }

    /**
     * Restituisce la tipologia dell'evento.
     * 
     * @return la tipologia dell'evento
     */
    @Override
    public String getTipologia() {
        return "Strumentale";
    }

    /**
     * Restituisce la lista dei convocati per l'evento.
     * 
     * @return la lista dei convocati per l'evento
     */
    public List<Strumentista> getConvocati() {
        return convocati;
    }

    /**
     * Imposta la lista dei convocati per l'evento.
     * 
     * @param convocati la lista dei convocati per l'evento
     */
    public void setConvocati(List<Strumentista> convocati) {
        this.convocati = convocati;
    }

}
