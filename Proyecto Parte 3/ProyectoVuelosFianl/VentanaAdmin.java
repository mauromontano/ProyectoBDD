package vuelos;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import quick.dbtable.DBTable;

@SuppressWarnings("serial")
public class VentanaAdmin extends javax.swing.JInternalFrame {

	private JPanel panelAdmin;
	private JTextArea textoConsulta;
	private JButton botonBorrar;
	private JButton botonEjecutar;
	private DBTable tabla, tablaTablas;
	private JTable tablaAtributo;
	private JScrollPane scrConsulta, jstAtributos;
	private java.sql.Connection conet;

	public VentanaAdmin() {
		super();
		iniciarGUI();
	}

	private void iniciarGUI() {
		try {
			setPreferredSize(new Dimension(1100, 900));
			this.setBounds(0, 0, 1200, 1000);
			setVisible(true);
			this.setTitle("Consultas a la base de datos mediante sentencias SQL");
			getContentPane().setLayout(null);
			this.setClosable(true);
			this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			this.setMaximizable(true);
			this.addComponentListener(new ComponentAdapter() {
				public void componentHidden(ComponentEvent evt) {
					thisComponentHidden(evt);
				}

				public void componentShown(ComponentEvent evt) {
					thisComponentShown(evt);
				}
			});

			{
				panelAdmin = new JPanel();
				panelAdmin.setBounds(120, 50, 500, 270);
				getContentPane().add(panelAdmin);
				JLabel nombre = new JLabel("Ingrese datos de la consulta:");
				panelAdmin.add(nombre);
				{
					scrConsulta = new JScrollPane();
					panelAdmin.add(scrConsulta);
					{
						textoConsulta = new JTextArea();
						scrConsulta.setViewportView(textoConsulta);
						textoConsulta.setTabSize(3);
						textoConsulta.setColumns(70);
						textoConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						textoConsulta.setText("SELECT * \n" + "FROM pasajeros;");
						textoConsulta.setFont(new java.awt.Font("Monospaced", 0, 12));
						textoConsulta.setRows(10);
					}
				}
				{
					botonEjecutar = new JButton();
					panelAdmin.add(botonEjecutar);
					botonEjecutar.setText("Ejecutar");
					botonEjecutar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							botonEjecutarActionPerformed(evt);
						}
					});
				}
				{
					botonBorrar = new JButton();
					panelAdmin.add(botonBorrar);
					botonBorrar.setText("Borrar");
					botonBorrar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							textoConsulta.setText("");
						}
					});
				}
			}
			{
				// creo y agrego la tabla de consulta
				tabla = new DBTable();
				tabla.setBounds(120, 320, 500, 185);
				tabla.setVisible(false);
				getContentPane().add(tabla);
				tabla.setEditable(false);
				// creo y agrego la tabla de tablas
				tablaTablas = new DBTable();

				tablaTablas.setBounds(700, 50, 205, 410);
				getContentPane().add(tablaTablas);
				tablaTablas.setEditable(false);
				tablaTablas.setVisible(true);
				cargarTabla();
				tablaTablas.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						tablaMouseClicked(evt, tablaTablas);
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void thisComponentShown(ComponentEvent evt) {
		this.conectarBD(tabla);
	}

	private void thisComponentHidden(ComponentEvent evt) {
		this.desconectarBD(tabla);
	}

	private void botonEjecutarActionPerformed(ActionEvent evt) {
		tabla.setVisible(true);
		this.refrescarTabla();
	}

	private void conectarBD(DBTable tabla) {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "vuelos";
			String usuario = "admin";
			String clave = "admin";
			String urlConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+ "?serverTimezone=America/Argentina/Buenos_Aires";

			// establece una conexión con la B.D. "batallas" usando directamante
			// una tabla DBTable
			tabla.connectDatabase(driver, urlConexion, usuario, clave);

			conet = DriverManager.getConnection(urlConexion, usuario, clave);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
					"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void desconectarBD(DBTable tabla) {
		try {
			tabla.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	private void refrescarTabla() {

		try {
			Statement stm = null;
			stm = conet.createStatement();

			if (stm.execute(textoConsulta.getText())) {
				// seteamos la consulta a partir de la cual se obtendrán los
				// datos para llenar la tabla
				tabla.setSelectSql(this.textoConsulta.getText().trim());
				
				// obtenemos el modelo de la tabla a partir de la consulta para
				// modificar la forma en que se muestran de algunas columnas
				tabla.createColumnModelFromQuery();
				for (int i = 0; i < tabla.getColumnCount(); i++) {
					// para que muestre correctamente los valores de tipo TIME
					// (hora)
					if (tabla.getColumn(i).getType() == Types.TIME) {
						tabla.getColumn(i).setType(Types.CHAR);
					}
					// cambiar el formato en que se muestran los valores de tipo
					// DATE
					if (tabla.getColumn(i).getType() == Types.DATE) {
						tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
					}
					
				}
				// actualizamos el contenido de la tabla.
				//cargarTabla();
				tabla.refresh();
				
				
				//cargarTabla();
				// No es necesario establecer una conexión, crear una sentencia
				// y recuperar el
				// resultado en un resultSet, esto lo hace automáticamente la
				// tabla (DBTable) a
				// patir de la conexión y la consulta seteadas con
				// connectDatabase() y setSelectSql() respectivamente.
			} else {
				if (tabla.getRowCount() > 0)
					tabla.refresh();
					cargarTabla();
				JOptionPane.showMessageDialog(this, "Comando " + textoConsulta.getText() + " con Exito\n", "Exito",
						JOptionPane.INFORMATION_MESSAGE);

			}
		} catch (SQLException ex) {
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n",
					"Error al ejecutar la consulta.", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarTabla() {
		conectarBD(tablaTablas);
		try {
			tablaTablas.setSelectSql("SHOW tables from vuelos;");

			// actualizamos el contenido de la tabla.
			tablaTablas.refresh();
			// No es necesario establecer una conexión, crear una sentencia y
			// recuperar el
			// resultado en un resultSet, esto lo hace automáticamente la tabla
			// (DBTable) a
			// patir de la conexión y la consulta seteadas con connectDatabase()
			// y setSelectSql() respectivamente.
		} catch (SQLException ex) {
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n",
					"Error al ejecutar la consulta.", JOptionPane.ERROR_MESSAGE);

		}
		desconectarBD(tablaTablas);
	}

	private void tablaMouseClicked(MouseEvent evt, DBTable tabla) {

		if ((tabla.getSelectedRow() != -1)) {
			this.seleccionarFila(tabla);
		}
	}

	private void seleccionarFila(DBTable tabla) {
		if (tablaAtributo == null) {
			tablaAtributo = new JTable();
			jstAtributos = new JScrollPane(tablaAtributo);
			jstAtributos.setBounds(905, 50, 100, 410);
			getContentPane().add(jstAtributos);
			// tablaAtributo.setEditable(false);
			jstAtributos.setVisible(true);
		}
		String str = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
		Statement stm = null;
		try {
			// Esto te permite contar todos los atributos de la tabla, ver como
			// mostrarlo.
			stm = conet.createStatement();
			ResultSet rs;
			rs = stm.executeQuery("SELECT * FROM " + str);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnas = rsmd.getColumnCount();
			String[] name = new String[columnas];

			String[] colName = { "Atributos" };
			DefaultTableModel model = new DefaultTableModel(null, colName);
			for (int i = 0; i < columnas; i++) {
				name[i] = rsmd.getColumnName(i + 1);
				model.insertRow(0, new Object[] { name[i] });
			}

			tablaAtributo.setModel(model);
			jstAtributos.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
