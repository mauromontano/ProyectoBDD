package vuelos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class VentanaPrincipal extends javax.swing.JFrame {

	/* declaro las ventanas */
	private VentanaAdmin ventanaAdmin;
	private VentanaEmpleado ventanaEmpleado;

	/* declaro los botones y el menu de opciones */
	private JDesktopPane panelUno;
	private JMenuBar menuBar;
	private JMenuItem menuAdmin;
	private JMenuItem menuEmpleado;
	private JMenuItem menuSalir;
	private JMenu menuOpciones;

	/* metodo para inicializar el JFrame */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VentanaPrincipal inst = new VentanaPrincipal();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public VentanaPrincipal() {
		super();

		iniciarGUI();

		this.ventanaAdmin = new VentanaAdmin();
		ventanaAdmin.setLocation(0, -12);
		this.ventanaAdmin.setVisible(false);
		this.panelUno.add(this.ventanaAdmin);

	}

	private void iniciarGUI() {

		try {
			{
				this.setTitle("Vuelos");
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			}
			{
				panelUno = new JDesktopPane();
				getContentPane().add(panelUno, BorderLayout.CENTER);
				panelUno.setPreferredSize(new java.awt.Dimension(800, 600));
			}
			{
				menuBar = new JMenuBar();
				setJMenuBar(menuBar);
				{
					menuOpciones = new JMenu();
					menuBar.add(menuOpciones);
					menuOpciones.setText("Opciones");
					{
						menuAdmin = new JMenuItem();
						menuOpciones.add(menuAdmin);
						menuAdmin.setText("Consultas a la base de datos mediante sentencias SQL");
						menuAdmin.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								menuAdminActionPerformed(evt);
							}
						});
					}

					{
						menuEmpleado = new JMenuItem();
						menuOpciones.add(menuEmpleado);
						menuEmpleado.setText("Consulta de disponibilidad de vuelos");
						menuEmpleado.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								menuEmpleadoActionPerformed(evt);
							}
						});
					}

					{
						menuSalir = new JMenuItem();
						menuOpciones.add(menuSalir);
						menuSalir.setText("Salir");
						menuSalir.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								menuSalirActionPerformed(evt);
							}
						});
					}

				}

			}
			this.setSize(800, 600);
			pack();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void menuSalirActionPerformed(ActionEvent ev) {
		this.dispose();
	}

	private void menuEmpleadoActionPerformed(ActionEvent ev) {

		loguearEmpleado();
	}

	private void menuAdminActionPerformed(ActionEvent ev) {
		try {
			this.ventanaAdmin.setMaximum(true);
		} catch (PropertyVetoException e) {
		}

		loguearAdmin();

	}

	private void loguearAdmin() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Ingresa la contraseña del usuario administrador:");
		JPasswordField pass = new JPasswordField(10);
		String url = "jdbc:mysql://localhost:3306/vuelos?";
		String user = "admin";
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK", "Cancelar" };
		int option = JOptionPane.showOptionDialog(null, panel, "Login Admin", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (option == 0) // pressing OK button
		{
			char[] password = pass.getPassword();
			String pwd = new String(password);
			try {
				@SuppressWarnings("unused")
				Connection con = DriverManager.getConnection(url, user, pwd);
				this.ventanaAdmin.setVisible(true);
			} catch (SQLException e) {
				mensajeError(e.getMessage());

			}

		}
	}

	public void mensajeError(String s) {
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, s, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
	}

	private void loguearEmpleado() {
		String url = "jdbc:mysql://localhost:3306/vuelos?";
		String user = "empleado";
		String pwd = "empleado";
		try {
			Connection con = DriverManager.getConnection(url, user, pwd);
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 2));
			JLabel u = new JLabel("User");
			JLabel p = new JLabel("Password");
			JTextField usuario = new JTextField();
			JPasswordField pass = new JPasswordField(10);
			panel.add(u);
			panel.add(usuario);
			panel.add(p);
			panel.add(pass);
			String[] options = new String[] { "OK", "Cancelar" };
			int option = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (option == 0)
				if (existeEmpleado(usuario.getText(), new String(pass.getPassword()), con)) {
					iniciarEmpleado();
				} else {
					this.mensajeError("Error: Los datos ingresados no son correctos.");
					loguearEmpleado();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void iniciarEmpleado() {
		this.ventanaEmpleado = new VentanaEmpleado();
		this.ventanaEmpleado.setVisible(true);
		this.panelUno.add(this.ventanaEmpleado);
		try {
			this.ventanaEmpleado.setMaximum(true);
		} catch (PropertyVetoException e) {
		}

	}

	private boolean existeEmpleado(String legajo, String pass, Connection con) {
		String sql = ("SELECT password FROM empleados WHERE legajo=" + legajo + " AND password=MD5('" + pass + "');");

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
