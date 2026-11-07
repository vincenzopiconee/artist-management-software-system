package boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUIMusicisti {

	static JFrame frame;
	private final Integer idMusicista;

	public GUIMusicisti(Integer _idMusicista) {
		idMusicista = _idMusicista;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnVisualizzaConvocazioni = new JButton("Visualizza convocazioni");
		btnVisualizzaConvocazioni.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				GUIConvocazioni  conv = new GUIConvocazioni(idMusicista);
				conv.frame.setVisible(true);
				frame.setVisible(false);
			}
		});

		btnVisualizzaConvocazioni.setBounds(86, 41, 262, 23);
		frame.getContentPane().add(btnVisualizzaConvocazioni);
		
		JButton btnInserisciDisponibility = new JButton("Inserisci disponibilità");
		btnInserisciDisponibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIDisponibilita sas = new GUIDisponibilita(idMusicista);
				sas.frame.setVisible(true);
				frame.setVisible(false);
			}
		});

		btnInserisciDisponibility.setBounds(86, 165, 262, 23);
		frame.getContentPane().add(btnInserisciDisponibility);
		JLabel lblIDmusicista = new JLabel("id: "+ idMusicista);
		lblIDmusicista.setBounds(378, 11, 46, 14);
		frame.getContentPane().add(lblIDmusicista);
		
		JButton btnVisualizzaEventi = new JButton("Visualizza eventi");
		btnVisualizzaEventi.setBounds(86, 101, 262, 23);
		frame.getContentPane().add(btnVisualizzaEventi);
	}
}
