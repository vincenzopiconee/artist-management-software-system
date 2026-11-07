package boundary;

import exception.LoginException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUILoginMusicista {

	JFrame frame;
	private JTextField textEmail;
	private JPasswordField passwordField;
	private JTextField textError;

	public GUILoginMusicista() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textEmail = new JTextField();
		textEmail.setBounds(220, 36, 157, 20);
		frame.getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(57, 39, 153, 14);
		frame.getContentPane().add(lblEmail);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(220, 67, 157, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblpassword = new JLabel("PASSWORD");
		lblpassword.setBounds(57, 70, 153, 14);
		frame.getContentPane().add(lblpassword);
		
		JLabel lblPrincipale = new JLabel("LOGIN MUSICISTA");
		lblPrincipale.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrincipale.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblPrincipale.setBounds(57, 11, 320, 14);
		frame.getContentPane().add(lblPrincipale);
		
		textError = new JTextField();
		textError.setEditable(false);
		textError.setBounds(57, 154, 320, 76);
		frame.getContentPane().add(textError);
		textError.setColumns(10);
		
		JButton btnInvia = new JButton("INVIA");
		btnInvia.addActionListener(new ActionListener() {
                    @Override
		    public void actionPerformed(ActionEvent e) {
                        try {
                            int id = Login.CheckPassword(textEmail.getText(), new String(passwordField.getPassword()));
							if (id != -1) {
								GUIMusicisti gui = new GUIMusicisti(id);
								gui.frame.setVisible(true);
								frame.setVisible(false);
							}
                        } catch (LoginException ex) {
							textError.setText(ex.getMessage());
                        }
		    }
		});
		
		btnInvia.setBounds(163, 98, 89, 23);
		frame.getContentPane().add(btnInvia);
	}
}
