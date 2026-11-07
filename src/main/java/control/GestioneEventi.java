package control;

import database.EventoDAO;
import database.MusicistaDAO;
import entity.Cantante;
import entity.Evento;
import entity.Musicista;
import entity.Strumentista;
import exception.ControllerException;
import exception.DatabaseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe GestioneEventi rappresenta un gestore di eventi musicali.
 * Fornisce metodi per inserire, modificare e rimuovere musicisti, creare e modificare eventi,
 * visualizzare gli eventi e ottenere la lista dei musicisti disponibili per una determinata data.
 * È implementata come un singleton, quindi può essere ottenuta solo tramite il metodo getInstance().
 */
public class GestioneEventi {

    private List<Musicista> musicistiDisponibiliPassati = null;

    private static GestioneEventi gE = null;

    private GestioneEventi() {}

    private enum tipologiaEventi {CONCERTO, ESIBIZIONE, STRUMENTALE};

    /**
     * Restituisce l'unica istanza della classe GestioneEventi.
     * Se l'istanza non è ancora stata creata, ne crea una nuova e la restituisce.
     *
     * @return l'unica istanza della classe GestioneEventi
     */
    public static GestioneEventi getInstance() {
        if (gE == null) {
            gE = new GestioneEventi();
        }
        return gE;
    }

    public void inserisciMusicista() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    public void modificaMusicista() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    public void rimuoviMusicista() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    public void modificaEvento() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    public void visualizzaEventi() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Non supportato.");
    }
    
    /**
     * Restituisce una mappa contenente i musicisti disponibili per una determinata data.
     * 
     * @param data La data in formato yyyy-MM-dd per cui si desidera ottenere i musicisti disponibili.
     * @return Una mappa contenente il nome e cognome dei musicisti come chiave e l'id del musicista come valore.
     * @throws ControllerException Se la data fornita è precedente o uguale alla data odierna, o se la data non è valida.
     */
    public Map<String, Integer> getMusicistiDisponibili(String data) throws ControllerException {
        try {
            LocalDate dataConvertita = LocalDate.parse(data);
            if (dataConvertita.isBefore(LocalDate.now()) || dataConvertita.isEqual(LocalDate.now())) {
                throw new ControllerException("La data non può essere precedente alla data odierna.");
            }
        } catch (DateTimeParseException e1) {
        	throw new ControllerException("Data non valida");
        }
        //Per controllare se i muisicisti selezionati sono tra i musicisti passati (che questa funzione ritorna)
        //salvo in una variabile privata i musicisti passati dal database
        musicistiDisponibiliPassati = ricercaMusicisti(data);
        Map<String, Integer> musicistiDisponibili = new HashMap<>();
        //Per ogni musicista disponibile, inserisco il nome e cognome (da mostrare nella boundary) come chiave e l'id (per indicare chi è stato selezionato) come valore
        for (Musicista musicista : musicistiDisponibiliPassati) {
            musicistiDisponibili.put(musicista.getNome() + " " + musicista.getCognome(), musicista.getIdMusicista());
        }
        return musicistiDisponibili;
    }

    /**
     * Crea un nuovo evento con i parametri specificati e lo salva nel database.
     * La funzione puo essere chiamata solo dopo aver ottenuto i musicisti disponibili per una determinata data.
     * I musicisti selezionati devono essere inclusi tra i musicisti disponibili ottenuti con la funzione getMusicistiDisponibili().
     * 
     * @param data La data dell'evento nel formato "yyyy-MM-dd".
     * @param luogo Il luogo dell'evento.
     * @param tipologia La tipologia dell'evento ("concerto", "esibizione", "strumentale").
     * @param musicistiSelezionati La lista degli ID dei musicisti selezionati per l'evento.
     * @return Il costo totale dell'evento.
     * @throws ControllerException Se si verificano errori durante la creazione dell'evento.
     */
    public float creaEvento(String data, String luogo, String tipologia, List<Integer> musicistiSelezionati) throws ControllerException {
        List<Musicista> listMusicistiSelezionati = new ArrayList<>();
        int cantantiSelezionati = 0;
        int strumentistiSelezionati = 0;
        float costoTot = 0;
        int musicistiIncerti;
        LocalDate dataConvertita = null;

        //Controllare che i parametri siano validi
        try {
            dataConvertita = LocalDate.parse(data);
        } catch (DateTimeParseException e) {
            throw new ControllerException("Data non valida");
        }
        if (dataConvertita.isBefore(LocalDate.now()) || dataConvertita.isEqual(LocalDate.now())) {
            throw new ControllerException("La data non può essere precedente alla data odierna.");
        }
        if (luogo.length()>120) {
            throw new ControllerException("Luogo non valido");
        }
        try {
            tipologiaEventi.valueOf(tipologia.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ControllerException("Tipologia non valida");
        }
        //Controllare che la lista dei musicisti passati non sia vuota
        if(musicistiSelezionati.isEmpty()) {
            throw new ControllerException("Nessun musicista selezionato");
        }
        //Controllare che siano stati precedentemente passati dei musicisti disponibili
        if (musicistiDisponibiliPassati == null) {
            throw new ControllerException("Nessun musicista precedentemente passato");
        }
        List<Integer> musicistiPassati = new ArrayList<>();
        for (Musicista m : musicistiDisponibiliPassati) {
            musicistiPassati.add(m.getIdMusicista());
        }
        //Se i musicisti selezionati non sono inclusi tra i musicisti precedentemente passati allora lancio un'eccezione
        for (Integer idSelezionato : musicistiSelezionati) {
            if (!musicistiPassati.contains(idSelezionato)) {
                throw new ControllerException("Selezione musicisti non valida");
            }
        }
        //Calcolo costo totale e conto dei strumentisti e cantanti selezionati per controllo vincoli
        for (Integer idMusicista: musicistiSelezionati){
            if (Cantante.class.isInstance(getMusicistaFromMusicistiPassatiById(idMusicista))){
                cantantiSelezionati++;
                costoTot += getMusicistaFromMusicistiPassatiById(idMusicista).getCachet();
                listMusicistiSelezionati.add(getMusicistaFromMusicistiPassatiById(idMusicista));
            } else if (Strumentista.class.isInstance(getMusicistaFromMusicistiPassatiById(idMusicista))){
                strumentistiSelezionati++;
                costoTot += getMusicistaFromMusicistiPassatiById(idMusicista).getCachet();
                listMusicistiSelezionati.add(getMusicistaFromMusicistiPassatiById(idMusicista));
            }
        }
        //Controllare vincoli evento in base alla tipologia 
        switch (tipologia.toLowerCase()) {
            case "concerto":
                if (!(cantantiSelezionati > 0 && strumentistiSelezionati > 1)) {
                    throw new ControllerException("Vincoli non rispettati: almeno un cantante e due strumentisti per un concerto.");
                }
                break;
            case "esibizione":
                if (!(cantantiSelezionati == 1 && strumentistiSelezionati > 0)) {
                    throw new ControllerException("Vincoli non rispettati: un solo cantante e almeno uno strumentista per un'esibizione.");
                }
                break;
            case "strumentale":
                if (!(cantantiSelezionati == 0 && strumentistiSelezionati > 0)) {
                    throw new ControllerException("Vincoli non rispettati: nessun cantante e almeno uno strumentista per un evento strumentale.");
                }
                break;
            default:
                break;
        }
        //I musicisti incerti sono pari al numero di musicisti selezionati alla craziione dell'evento
        musicistiIncerti = musicistiSelezionati.size();
        try {
            //Salvo l'evento nel database
            EventoDAO.createEvento(dataConvertita.toString(), luogo, tipologia, musicistiSelezionati, costoTot, musicistiIncerti);
            inviaConvocazione(listMusicistiSelezionati, dataConvertita.toString(), luogo, tipologia);
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante la creazione dell'evento: " + e.getMessage());
        }
        //Resetto la variabile privata contenente i musicisti passati
        musicistiDisponibiliPassati = null;
        return costoTot;
    }

    /**
        * Inserisce un periodo di disponibilità per un musicista.
        * 
        * @param idMusicista l'ID del musicista
        * @param dataInizio la data di inizio della disponibilità (formato: yyyy-mm-dd), inclusa, non può essere precedente alla data odierna
        * @param dataFine la data di fine della disponibilità (formato: yyyy-mm-dd), inclusa, non può essere precedente alla data di inizio
        * @throws ControllerException se si verifica un errore durante l'inserimento delle disponibilità
        */
    public void inserisciDisponibilita(int idMusicista, String dataInizio, String dataFine) throws ControllerException {
        //Controlli sui parametri
        if (idMusicista <= 0) {
            throw new ControllerException("IdMusicista inserito non valido");
        }
        try {
            LocalDate dataInizioConvertita = LocalDate.parse(dataInizio);
            LocalDate dataFineConvertita = LocalDate.parse(dataFine);
            if (dataInizioConvertita.isBefore(LocalDate.now())) {
                throw new ControllerException("La data di inizio non può essere precedente alla data odierna.");
            }
            if (dataFineConvertita.isBefore(dataInizioConvertita)) {
                throw new ControllerException("La data di fine non può essere precedente alla data di inizio.");
            }
            try {
                //Viene inserito singolarmente ogni giorno tra la data di inizio e la data di fine (inclusa)
                while (dataInizioConvertita.isBefore(dataFineConvertita) || dataInizioConvertita.isEqual(dataFineConvertita)) {
                    MusicistaDAO.inserisciDisponibilita(idMusicista, dataInizioConvertita.toString());
                    dataInizioConvertita = dataInizioConvertita.plusDays(1);
                }
            } catch (DatabaseException e) {
                throw new ControllerException("Errore durante l'inserimento delle disponibilità: " + e.getMessage());
            }
        } catch (DateTimeParseException e) {
            throw new ControllerException("Data non formattata correttamente (yyyy-mm-dd)");
        }
    }

    /**
     * Gestisce la convocazione di un musicista per un determinato evento.
     * 
     * @param idMusicista l'ID del musicista da convocare (se negativo lancia una eccezione)
     * @param idEvento l'ID dell'evento per cui si effettua la convocazione (se negativo lancia una eccezione)
     * @param esito l'esito della convocazione (true se accettato, false se rifiutato)
     * @throws ControllerException se si verificano errori durante la gestione della convocazione
     */
    public void gestisciConvocazione(int idMusicista, int idEvento, boolean esito) throws ControllerException {
        //Controlli sui parametri
        if (idMusicista <= 0) {
            throw new ControllerException("IdMusicista inserito non valido");
        }
        if (idEvento <= 0) {
            throw new ControllerException("IdEventoConvocazione inserito non valido");
        }
        //Viene inviato l'esito selezionato dal musicista, eventualmente confermando la convocazione per l'evento
        this.inviaEsito(idMusicista, idEvento, esito);
        try {
            //Salvo l'esito della convocazione nel database
            MusicistaDAO.updateEsitoConvocazione(esito, idEvento, idMusicista);
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante la conferma della convocazione: " + e.getMessage());
        }        
    }

    /**
     * Invia un report contenente gli eventiconfermati e i compensi del mese e anno specificati.
     * 
     * @param mese il numero del mese (1-12).
     * @param anno l'anno.
     * @throws ControllerException se si verifica un errore durante l'invio del report
     */
    public void inviaReport(int mese, int anno) throws ControllerException{
        try {
            List<Evento> eventiMesePrecedente = EventoDAO.readEventiMensili(mese, anno);
            //Syso che simula la che simula la chiamata al metodo di invio mail report eventi
            System.out.println("Email inviata!");
            for (Evento evento : eventiMesePrecedente) {
                System.out.println("Evento in data: " + evento.getData() + " presso: " + evento.getLuogo() + " di tipologia: " + evento.getTipologia() + "costo totale: " + evento.getCostoTotale());
            }
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante l'invio del report eventi: " + e.getMessage());
        }
        try {
            HashMap<String, Float> compensiMesePrecedente = MusicistaDAO.readCompensiMensili(mese, anno);
            //Syso che simula la che simula la chiamata al metodo di invio mail report Musicisti
            System.out.println("Email inviata!");
            for (String musicista : compensiMesePrecedente.keySet()) {
                System.out.println("Musicista: " + musicista + " compenso: " + compensiMesePrecedente.get(musicista));
            } 
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante l'invio del report compensi: " + e.getMessage());
        }
    }

    /**
     * Restituisce un HashMap contenente le convocazioni di un musicista identificato dall'id specificato.
     * 
     * @param idMusicista l'id del musicista di cui si vogliono ottenere le convocazioni
     * @return un HashMap contenente le convocazioni del musicista, dove la chiave è l'id dell'evento e il valore è un array di stringhe contenente le informazioni dell'evento
     * @throws ControllerException se l'id del musicista è negativo o se si verifica un errore durante la lettura delle convocazioni dal database
     */
    public HashMap<Integer, String[]> getConvocazioni(Integer idMusicista) throws ControllerException {
        if (idMusicista <= 0) {
            throw new ControllerException("IdMusicista inserito non valido");
        }
        try {
            return MusicistaDAO.getConvocazioni(idMusicista);
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante la lettura delle convocazioni: " + e.getMessage());
        }
    }

    /**
     * Effettua una ricerca dei musicisti disponibili in base alla data specificata.
     * I parametri sono supposti validi, non genera eccezioni.
     * 
     * @param data la data per cui effettuare la ricerca dei musicisti disponibili
     * @return una lista di oggetti Musicista rappresentanti i musicisti disponibili
     * @throws ControllerException se si verifica un errore durante la ricerca dei musicisti
     */
    private List<Musicista> ricercaMusicisti(String data) throws ControllerException{
        try {
            return MusicistaDAO.readMusicistiDisponibili(data);
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante la ricerca dei musicisti: " + e.getMessage());
        }
    }


    /**
     * Invia una convocazione ai musicisti selezionati per un evento.
     * I parametri sono supposti validi, non genera eccezioni.
     * 
     * @param musicistiSelezionati la lista dei musicisti selezionati
     * @param data la data dell'evento
     * @param luogo il luogo dell'evento
     * @param tipologia la tipologia dell'evento
     */
    private void inviaConvocazione(List<Musicista> musicistiSelezionati, String data, String luogo, String tipologia) {
        //Syso che simula la chiamata al metodo di invio mail convocazione
        for (Musicista musicista : musicistiSelezionati) {
            System.out.println("Email inviata! Musicista: " + musicista.getNome() + " " + musicista.getCognome() + " convocato per l'evento in data: " + data + " presso: " + luogo + " di tipologia: " + tipologia);
        }
    }

    /**
     * Invia l'esito della convocazione di un musicista a un evento.
     * I parametri sono supposti validi, non genera eccezioni.
     * 
     * @param idMusicista l'ID del musicista a cui inviare l'esito.
     * @param idEvento l'ID dell'evento di cui inviare l'esito.
     * @param esito l'esito dell'evento (true se accettato, false se rifiutato).
     * @throws ControllerException se si verifica un errore durante l'invio dell'esito.
     */
    private void inviaEsito(int idMusicista, int idEvento, boolean esito) throws ControllerException {
        Evento evento;
        try {
            //Prelevo l'evento e il nome del musicista della convocazione
            evento = EventoDAO.readEvento(idEvento);
            String musicista = MusicistaDAO.readNomeCognome(idMusicista);
            //Syso che simula la chiamata al metodo di invio mail esito
            System.out.println("Email inviata! Musicista: " + musicista + " ha dato l'esito: " + esito + " per l'evento in data: " +  evento.getData() + " presso: " + evento.getLuogo() + " di tipologia: " + evento.getTipologia());
            if (esito) {
                //Se l'esito è positivo, confermo la convocazione decrementando il numero di musicisti incerti
                evento.confermaConvocazione();
            }
        } catch (DatabaseException e) {
            throw new ControllerException("Errore durante la lettura dell'evento o del musicista :" + e.getMessage());
        }
    }

    /**
     * Metodo di utilità.
     * Restituisce un oggetto Musicista corrispondente all'ID specificato tra quelli in musicistiDisponibiliPassati.
     * Se l'ID non corrisponde a nessun musicista, lancia un'eccezione.
     * 
     * @param id l'ID del musicista da cercare.
     * @return l'oggetto Musicista corrispondente all'ID specificato.
     * @throws ControllerException se la selezione dei musicisti non è valida.
     */
    private Musicista getMusicistaFromMusicistiPassatiById(Integer id) throws ControllerException {
        for (Musicista m : musicistiDisponibiliPassati) {
            if(m.getIdMusicista().equals(id)) {
                return m;
            }
        }
        throw new ControllerException("Selezione musicisti non valida");
    }
}