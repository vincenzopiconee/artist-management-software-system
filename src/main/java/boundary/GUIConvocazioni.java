package boundary;

import control.GestioneEventi;
import exception.ControllerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class GUIConvocazioni {

	JFrame frame;
	private final Integer idMusicista;

	public GUIConvocazioni(Integer _idmusicista) {
		idMusicista = _idmusicista;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 63, 366, 145);
		frame.getContentPane().add(scrollPane);
		GestioneEventi ge = GestioneEventi.getInstance();
		HashMap<Integer, String[]> eventi = null;
		ArrayList<String> infoEventi = null;
		try {
			eventi = ge.getConvocazioni(idMusicista);
			infoEventi = new ArrayList<>();
			for (Integer id : eventi.keySet()) {
				String[] info = eventi.get(id);
				infoEventi.add(id + " - " + info[0] + " - " + info[1]);
			}
			
		} catch (ControllerException e) {
			System.out.println(e.getMessage());
		}
		JList list = new JList(infoEventi.toArray());
		scrollPane.setColumnHeaderView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Retrieve degli eventi in cui è stato convocato il musicista e che non hanno ancora un esito
		
		
		JButton btnGestisci = new JButton("Gestisci");
		if (eventi.isEmpty()) {
			btnGestisci.setEnabled(false);
		}
		btnGestisci.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				// apertura finestra gestione evento con id = evento selezionato
				Integer idEvento = Integer.parseInt(((String) list.getSelectedValue()).split(" - ")[0]);
				GUIGestisciConvocazione gest = new GUIGestisciConvocazione(idEvento, idMusicista);
				gest.frame.setVisible(true);
				frame.setVisible(false);

			}
		});
		btnGestisci.setBounds(171, 220, 89, 23);
		frame.getContentPane().add(btnGestisci);
		
		JLabel lblNewLabel = new JLabel("Id, data e luogo di eventi a cui sei stato convocato");
		lblNewLabel.setBounds(6, 35, 396, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("<");
		btnNewButton.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				GUIMusicisti.frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(6, 6, 23, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
