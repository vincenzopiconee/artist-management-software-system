package boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUIEventoCreato {

	JFrame frame;
	private JTextField textFieldData;
	private JTextField textFieldLuogo;
	private JTextField textFieldTipologia;
	private JTextField textFieldCostoTotale;

	public GUIEventoCreato(String data, String luogo, String tipologia, float costoTotale, String[] musicistiSelezionati) {
		initialize(data, luogo, tipologia, costoTotale, musicistiSelezionati);
	}

	private void initialize(String data, String luogo, String tipologia, float costoTotale, String[] musicistiSelezionati) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data: ");
		lblNewLabel.setBounds(6, 22, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		textFieldData = new JTextField();
		textFieldData.setEditable(false);
		textFieldData.setBounds(101, 17, 130, 26);
		frame.getContentPane().add(textFieldData);
		textFieldData.setColumns(10);
		textFieldData.setText(data);
		
		JLabel lblNewLabel_1 = new JLabel("Luogo: ");
		lblNewLabel_1.setBounds(6, 60, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldLuogo = new JTextField();
		textFieldLuogo.setEditable(false);
		textFieldLuogo.setBounds(101, 55, 130, 26);
		frame.getContentPane().add(textFieldLuogo);
		textFieldLuogo.setColumns(10);
		textFieldLuogo.setText(luogo);
		
		JLabel lblNewLabel_2 = new JLabel("Tipologia:");
		lblNewLabel_2.setBounds(6, 106, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		textFieldTipologia = new JTextField();
		textFieldTipologia.setEditable(false);
		textFieldTipologia.setBounds(101, 99, 130, 26);
		frame.getContentPane().add(textFieldTipologia);
		textFieldTipologia.setColumns(10);
		textFieldTipologia.setText(tipologia);
		
		
		JLabel lblNewLabel_3 = new JLabel("Costo Totale:");
		lblNewLabel_3.setBounds(6, 151, 90, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		textFieldCostoTotale = new JTextField();
		textFieldCostoTotale.setEditable(false);
		textFieldCostoTotale.setBounds(101, 146, 130, 26);
		frame.getContentPane().add(textFieldCostoTotale);
		textFieldCostoTotale.setColumns(10);
		textFieldCostoTotale.setText(String.valueOf(costoTotale));
		
		JLabel lblNewLabel_4 = new JLabel("Musicisti Selezionati:");
		lblNewLabel_4.setBounds(286, 22, 137, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(275, 50, 153, 118);
		frame.getContentPane().add(scrollPane);
		
		JList<String> listMusicisti = new JList<>(musicistiSelezionati);
		scrollPane.setViewportView(listMusicisti);
		
		JLabel lblNewLabel_5 = new JLabel("Evento creato");
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel_5.setBounds(22, 195, 169, 53);
		frame.getContentPane().add(lblNewLabel_5);
		
		JButton btnNewButton = new JButton("Home");
		btnNewButton.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				IGestore.frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(314, 212, 90, 29);
		frame.getContentPane().add(btnNewButton);
	}
}
