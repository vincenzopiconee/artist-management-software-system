package boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * Interfaccia per il gestore.
 * Permette di creare un evento, inserire, modificare e rimuovere un musicista e modificare un evento.
 * Solo creaEvento è implementato.
 * La variabile frame è statica e friendly per permettere di ritornare alla schermata precedente.
 */

public class IGestore {

	static JFrame frame;

	public IGestore() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 466, 365);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCreaEvento = new JButton("Crea evento");
		btnCreaEvento.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				GUICreaEvento GCE = new GUICreaEvento();
				GCE.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnCreaEvento.setBounds(139, 47, 177, 23);
		frame.getContentPane().add(btnCreaEvento);
		
		JButton btnInserisciMusicista = new JButton("Inserisci musicista");
		btnInserisciMusicista.setBounds(139, 103, 177, 23);
		frame.getContentPane().add(btnInserisciMusicista);
		
		JButton btnModificaMusicista = new JButton("Modifica musicista");
		btnModificaMusicista.setBounds(139, 157, 177, 23);
		frame.getContentPane().add(btnModificaMusicista);
		
		JButton btnRimuoviMusicista = new JButton("Rimuovi musicista");
		btnRimuoviMusicista.setBounds(139, 214, 177, 23);
		frame.getContentPane().add(btnRimuoviMusicista);
		
		JButton btnModificaEvento = new JButton("Modifica Evento");
		btnModificaEvento.setBounds(139, 272, 177, 23);
		frame.getContentPane().add(btnModificaEvento);
	}

}
