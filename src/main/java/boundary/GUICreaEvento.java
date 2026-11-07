package boundary;

import control.GestioneEventi;
import exception.ControllerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUICreaEvento {

	JFrame frame;
	private JTextField textLuogo;
	private JTextField txtData;
	private JLabel lblTipologia;
	private JButton btnVisualizzaMusicistiDisponibili;
	private JTextField textError;

	public GUICreaEvento() {
		initialize();
	}

	private void initialize() {
		GestioneEventi GE = GestioneEventi.getInstance();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textLuogo = new JTextField();
		textLuogo.setBounds(240, 20, 182, 20);
		frame.getContentPane().add(textLuogo);
		textLuogo.setColumns(10);
		
		JLabel lblInserisciLuogo = new JLabel("Inserisci Luogo");
		lblInserisciLuogo.setBounds(44, 23, 160, 14);
		frame.getContentPane().add(lblInserisciLuogo);
		
		JLabel lblData = new JLabel("Inserisci Data (yyyy-MM-dd)");
		lblData.setBounds(44, 51, 198, 14);
		frame.getContentPane().add(lblData);
		
		txtData = new JTextField();
		txtData.setBounds(312, 48, 110, 20);
		frame.getContentPane().add(txtData);
		txtData.setColumns(10);
		
		JComboBox<String> boxTipologia = new JComboBox<>();
		boxTipologia.setBounds(312, 77, 110, 22);
		frame.getContentPane().add(boxTipologia);
		boxTipologia.addItem("CONCERTO");
		boxTipologia.addItem("ESIBIZIONE");
		boxTipologia.addItem("STRUMENTALE");
		
		lblTipologia = new JLabel("Tipologia");
		lblTipologia.setBounds(44, 80, 123, 14);
		frame.getContentPane().add(lblTipologia);
		
		btnVisualizzaMusicistiDisponibili = new JButton("Visualizza musicisti disponibili");
		btnVisualizzaMusicistiDisponibili.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				try {
					Map<String, Integer> musDisponibili = GE.getMusicistiDisponibili(txtData.getText());
					GUIMusicistiSelezionati guiMusicistiSelezionati = new GUIMusicistiSelezionati(musDisponibili, txtData.getText(), textLuogo.getText(), boxTipologia.getSelectedItem().toString());
					guiMusicistiSelezionati.frame.setVisible(true);
					frame.setVisible(false);
				
				} catch (ControllerException e1) {
					textError.setText(e1.getMessage());
				}
			}
		});
		btnVisualizzaMusicistiDisponibili.setBounds(108, 142, 251, 23);
		frame.getContentPane().add(btnVisualizzaMusicistiDisponibili);
		
		textError = new JTextField();
		textError.setEditable(false);
		textError.setBounds(20, 191, 413, 53);
		frame.getContentPane().add(textError);
		textError.setColumns(10);
		
		JButton indietro = new JButton("<");
		indietro.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				IGestore.frame.setVisible(true);
			}
		});
		indietro.setBounds(6, 6, 20, 20);
		frame.getContentPane().add(indietro);
		
	}
}
