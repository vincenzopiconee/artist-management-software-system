package boundary;

import control.GestioneEventi;
import exception.ControllerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class GUIMusicistiSelezionati {

	JFrame frame;
	private JTextField textFieldError;

	public GUIMusicistiSelezionati(Map<String, Integer> musicistiDisponibili, String data, String luogo, String tipologia) {
		initialize(musicistiDisponibili, data, luogo, tipologia);
	}

	private void initialize(Map<String, Integer> musicistiDisponibili, String data, String luogo, String tipologia) {
		GestioneEventi ge = GestioneEventi.getInstance();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String[] musicisti = new String[musicistiDisponibili.size()];
		int i = 0;
		for (String nome : musicistiDisponibili.keySet()) {
			musicisti[i] = nome;
			i++;
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 26, 351, 113);
		frame.getContentPane().add(scrollPane);
		JList<String> list = new JList<>(musicisti);
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(15);
		
		JButton btnNewButton = new JButton("Seleziona Musicisti");
		btnNewButton.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				List<String> nomiCognomiSelezionati = list.getSelectedValuesList();
				List<Integer> listaIdSelezionati = new ArrayList<>();
				for (String s : nomiCognomiSelezionati) {
					listaIdSelezionati.add(musicistiDisponibili.get(s));
				} 
				try {
					float costoTotale = ge.creaEvento(data, luogo, tipologia, listaIdSelezionati);
					String[] nomCogSel = new String[nomiCognomiSelezionati.size()];
					int i = 0;
					for (String s : nomiCognomiSelezionati) {
						nomCogSel[i++] = s;
					}
					GUIEventoCreato sos = new GUIEventoCreato(data, luogo, tipologia, costoTotale, nomCogSel);
					sos.frame.setVisible(true);
					frame.setVisible(false);
				} catch (ControllerException e1) {
					textFieldError.setText(e1.getMessage());
				}
			}
		});
		
		btnNewButton.setBounds(150, 162, 170, 29);
		frame.getContentPane().add(btnNewButton);
		
		textFieldError = new JTextField();
		textFieldError.setEditable(false);
		textFieldError.setBounds(50, 203, 351, 45);
		frame.getContentPane().add(textFieldError);
		textFieldError.setColumns(10);
	}
}
