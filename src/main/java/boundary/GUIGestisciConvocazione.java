package boundary;

import control.GestioneEventi;
import exception.ControllerException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIGestisciConvocazione {

	JFrame frame;
	private final Integer idEvento;
	private final Integer idMusicista;
	private JTextField textError;


	public GUIGestisciConvocazione(Integer _idEvento, Integer _idMusicista) {
		idMusicista = _idMusicista;
		idEvento = _idEvento;
		initialize();
	}

	private void initialize() {
		GestioneEventi ge = GestioneEventi.getInstance();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblConvocazione = new JLabel("CONVOCAZIONE PER EVENTO: " + idEvento);
		lblConvocazione.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblConvocazione.setBounds(63, 11, 339, 14);
		frame.getContentPane().add(lblConvocazione);
		
		JButton btnAccetta = new JButton("ACCETTA");
		btnAccetta.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				try {
					ge.gestisciConvocazione(idMusicista, idEvento, true);
					// chiudi il frame e riapri la finestra precedente 
					GUIConvocazioni conv = new GUIConvocazioni(idMusicista);
					PopUpEsitoGestisciConvocazione popUp = new PopUpEsitoGestisciConvocazione("Convocazione accettata con successo!");
					popUp.frame.setVisible(true);
					conv.frame.setVisible(true);
					frame.setVisible(false);
				} catch (ControllerException e1) {
					textError.setText(e1.getMessage());
				}

			}
		});
		btnAccetta.setBounds(85, 99, 89, 23);
		frame.getContentPane().add(btnAccetta);
		
		JButton btnRifiuta = new JButton("RIFIUTA");
		btnRifiuta.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				try {
					ge.gestisciConvocazione(idMusicista, idEvento, false);
					GUIConvocazioni conv = new GUIConvocazioni(idMusicista);
					PopUpEsitoGestisciConvocazione popUp = new PopUpEsitoGestisciConvocazione("Convocazione rifiutata con successo!");
					popUp.frame.setVisible(true);
					conv.frame.setVisible(true);
					frame.setVisible(false);
				} catch (ControllerException e1) {
					textError.setText(e1.getMessage());
				}
			}
		});
		btnRifiuta.setBounds(244, 99, 89, 23);
		frame.getContentPane().add(btnRifiuta);
		
		textError = new JTextField();
		textError.setEditable(false);
		textError.setBounds(85, 164, 278, 65);
		frame.getContentPane().add(textError);
		textError.setColumns(10);
	}
}
