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

public class ITempo {

	JFrame frame;
	private JTextField textMese;
	private JTextField textAnno;
	private JTextField textError;

	public ITempo() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textMese = new JTextField();
		textMese.setBounds(240, 55, 86, 20);
		frame.getContentPane().add(textMese);
		textMese.setColumns(10);
		
		JLabel lblMese = new JLabel("Inserisci mese");
		lblMese.setBounds(80, 58, 150, 14);
		frame.getContentPane().add(lblMese);
		
		textAnno = new JTextField();
		textAnno.setColumns(10);
		textAnno.setBounds(240, 105, 86, 20);
		frame.getContentPane().add(textAnno);
		
		JLabel lblInserisciAnno = new JLabel("Inserisci anno");
		lblInserisciAnno.setBounds(80, 108, 150, 14);
		frame.getContentPane().add(lblInserisciAnno);
		
		textError = new JTextField();
		textError.setEditable(false);
		textError.setBounds(80, 187, 246, 52);
		frame.getContentPane().add(textError);
		textError.setColumns(10);
		
		JButton btnConferma = new JButton("CONFERMA");
		btnConferma.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnConferma.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				// Invocare metodo per generare report
				GestioneEventi ge = GestioneEventi.getInstance();
				try {
					ge.inviaReport(Integer.parseInt(textMese.getText()), Integer.parseInt(textAnno.getText()));
				} catch (NumberFormatException | ControllerException e1) {
					textError.setText(e1.getMessage());
				}           
			}
		});
		btnConferma.setBounds(164, 136, 89, 23);
		frame.getContentPane().add(btnConferma);
		
	}
}
