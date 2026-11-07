package boundary;

import control.GestioneEventi;
import exception.ControllerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIDisponibilita {

	JFrame frame;
	private JTextField textFieldDataIn;
	private JTextField textFieldDataFin;
	private JTextField textError;
	private final Integer idMusicista;

	public GUIDisponibilita(Integer _idMusicista) {
		idMusicista = _idMusicista;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Inserisci periodo di disponibilità:");
		lblNewLabel.setBounds(6, 21, 245, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Data iniziale (yyyy-MM-dd): ");
		lblNewLabel_1.setBounds(6, 49, 210, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Data finale (yyyy-MM-dd): ");
		lblNewLabel_2.setBounds(6, 85, 177, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		textFieldDataIn = new JTextField();
		textFieldDataIn.setBounds(254, 44, 190, 26);
		frame.getContentPane().add(textFieldDataIn);
		textFieldDataIn.setColumns(10);
		
		textFieldDataFin = new JTextField();
		textFieldDataFin.setBounds(254, 80, 190, 26);
		frame.getContentPane().add(textFieldDataFin);
		textFieldDataFin.setColumns(10);
		
		textError = new JTextField();
		textError.setEditable(false);
		textError.setBounds(6, 193, 438, 60);
		frame.getContentPane().add(textError);
		textError.setColumns(10);
		
		JButton btnNewButton = new JButton("Inserisci");
		btnNewButton.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				try {
					GestioneEventi.getInstance().inserisciDisponibilita(idMusicista, textFieldDataIn.getText(), textFieldDataFin.getText());
					textError.setText("Disponibilità inserita con successo!");
				} catch (ControllerException e1) {
					textError.setText(e1.getMessage());
				}
			}
		});
		btnNewButton.setBounds(160, 145, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				GUIMusicisti.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton_1.setBounds(6, 6, 25, 16);
		frame.getContentPane().add(btnNewButton_1);
	}
}
