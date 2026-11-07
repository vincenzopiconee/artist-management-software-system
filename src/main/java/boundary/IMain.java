package boundary;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Interfaccia fittizzia per aprire le varie interfacce.
 * Ai musicisti è fornita direttamente la loro interfaccia di login e al gestore la propria.
 * L'interfaccia del tempo è simulata per permettere di testare il caso d'uso automatico
 */
public class IMain {

	private JFrame frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
                        @Override
			public void run() {
				IMain window = new IMain();
				window.frame.setVisible(true);
			}
		});
	}

	public IMain() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 633, 414);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLoginMusicista = new JButton("Login musicista");
		btnLoginMusicista.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				GUILoginMusicista login = new GUILoginMusicista();
				login.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnLoginMusicista.setBounds(205, 97, 165, 23);
		frame.getContentPane().add(btnLoginMusicista);
		
		JButton btnAccessoGestore = new JButton("Accesso Gestore");
		btnAccessoGestore.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				IGestore GUIGestore = new IGestore();
				GUIGestore.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnAccessoGestore.setBounds(205, 170, 165, 23);
		frame.getContentPane().add(btnAccessoGestore);
		
		JButton btnSimulaTempo = new JButton("Simula tempo");
		btnSimulaTempo.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				ITempo guiTempo = new ITempo();
				guiTempo.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnSimulaTempo.setBounds(205, 243, 165, 23);
		frame.getContentPane().add(btnSimulaTempo);
	}
}
