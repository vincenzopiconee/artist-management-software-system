package boundary;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PopUpEsitoGestisciConvocazione {

	JFrame frame;
	private final String message;

	public PopUpEsitoGestisciConvocazione(String _message) {
		message = _message;
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 449, 164);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setAlwaysOnTop(true);
		
		JLabel lblEsito = new JLabel(message);
		lblEsito.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEsito.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsito.setBounds(39, 21, 365, 82);
		frame.getContentPane().add(lblEsito);
	}

}
