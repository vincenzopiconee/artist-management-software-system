package entity;

import database.EventoDAO;
import exception.DatabaseException;

/**
 * Questa classe rappresenta un evento generico.
 */
public abstract class Evento {

    /**
     * L'identificatore dell'evento.
     */
    private int id;

    /**
     * La data dell'evento nel formato "yyyy-MM-dd".
     */
    private String data;

    /**
     * Il luogo dell'evento.
     */
    private String luogo;

    /**
     * Il costo totale dell'evento.
     */
    private float costoTotale;

    /**
     * Il numero di musicisti incerti per l'evento, ovvero dei musicisti che non hanno ancora accettato.
     */
    private int musicistiIncerti;

    /**
     * Lo stato dell'evento.
     */
    private enum StatoEnum {CONFERMATO, IN_ATTESA};
    private StatoEnum stato;

    /**
     * Costruttore della classe Evento.
     * 
     * @param id L'identificatore dell'evento
     * @param data La data dell'evento nel formato "yyyy-MM-dd"
     * @param luogo Il luogo dell'evento
     * @param costoTotale Il costo totale dell'evento
     * @param musicistiIncerti Il numero di musicisti incerti per l'evento
     */
    public Evento(int id, String data, String luogo, float costoTotale,int musicistiIncerti) {
        this.id = id;
        this.data = data;
        this.luogo = luogo;
        this.costoTotale = costoTotale;
        this.musicistiIncerti = musicistiIncerti;
        this.stato = StatoEnum.IN_ATTESA;
    }

    /**
     * Restituisce la data dell'evento.
     * 
     * @return La data dell'evento
     */
    public String getData() {
        return data;
    }

    /**
     * Imposta la data dell'evento.
     * 
     * @param data La data dell'evento nel formato "yyyy-MM-dd"
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Restituisce il luogo dell'evento.
     * 
     * @return Il luogo dell'evento
     */
    public String getLuogo() {
        return luogo;
    }

    /**
     * Imposta il luogo dell'evento.
     * 
     * @param luogo Il luogo dell'evento
     */
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    /**
     * Restituisce il numero di musicisti incerti per l'evento.
     * 
     * @return Il numero di musicisti incerti per l'evento
     */
    public int getMusicistiIncerti() {
        return musicistiIncerti;
    }

    /**
     * Imposta il numero di musicisti incerti per l'evento.
     * 
     * @param musicistiIncerti Il numero di musicisti incerti per l'evento
     */
    public void setMusicistiIncerti(int musicistiIncerti) {
        this.musicistiIncerti = musicistiIncerti;
    }

    /**
     * Restituisce il costo totale dell'evento.
     * 
     * @return Il costo totale dell'evento
     */
    public float getCostoTotale() {
        return costoTotale;
    }

    /**
     * Imposta il costo totale dell'evento.
     * 
     * @param costoTotale Il costo totale dell'evento
     */
    public void setCostoTotale(float costoTotale) {
        this.costoTotale = costoTotale;
    }

    /**
     * Restituisce l'identificatore dell'evento.
     * 
     * @return L'identificatore dell'evento
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificatore dell'evento.
     * 
     * @param id L'identificatore dell'evento
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce lo stato dell'evento.
     * 
     * @return Lo stato dell'evento
     */
    public String getStato() {
        if (this.stato == StatoEnum.CONFERMATO) {
            return "Confermato";
        } else {
            return "In attesa";
        }
    }

    /**
     * Restituisce la tipologia dell'evento.
     * 
     * @return La tipologia dell'evento
     */
    public abstract String getTipologia();

    /**
     * Conferma la convocazione dell'evento.
     * 
     * @throws DatabaseException Se si verifica un errore durante l'aggiornamento del database
     */
    public void confermaConvocazione() throws DatabaseException {
        this.musicistiIncerti--;
        EventoDAO.updateMusicistiIncerti(id, musicistiIncerti);
        if (musicistiIncerti == 0) {
            this.stato = StatoEnum.CONFERMATO;
            EventoDAO.confermaStato(this.id);
        }
    }
}
