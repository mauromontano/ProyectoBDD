package vuelos;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.sql.CallableStatement;

import quick.dbtable.DBTable;

@SuppressWarnings({ "serial", "unused" })
public class VentanaEmpleado extends javax.swing.JInternalFrame {
	private JPanel inicio, panelIda, panelVuelta;
	private JComboBox<String> ida, vuelta;
	private JLabel origen, destino, fechaSal, fechaVuelt, checkVueltaLabel;
	private JTextField fechaSalida, fechaVuelta;
	private JCheckBox checkVuelta;
	private JLabel tituloIda, tituloVuelta;
	private DBTable tablaIda, tablaVuelta, tablaPIda, tablaPVuelta;
	private JButton botonReserva;
	private boolean hayVuelta;
	private String legajo;
	private String fechaI,fechaV;
	private CallableStatement st;
	private Connection c;
	
	private String nroVueloIda, nroVueloVuelta,claseIda,claseVuelta;
	private int filaVueloIda,filaVueloVuelta,filaClaseIda,filaClaseVuelta;
	DBTable tp;
	

	public VentanaEmpleado(String legajo) {
		super();
		this.legajo=legajo;
		iniciarGui();
	}

	private void iniciarGui() {
		setPreferredSize(new Dimension(1100, 900));
		this.setBounds(0, 0, 1200, 1000);
		setVisible(true);
		BoxLayout thisLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		this.setTitle("Consultas sobre vuelos.");
		getContentPane().setLayout(thisLayout);
		this.setClosable(true);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setMaximizable(true);
		// panel sup
		panelIda = new JPanel();
		panelIda.setLayout(new GridLayout(1, 2));

		// panel inf
		panelVuelta = new JPanel();
		panelVuelta.setLayout(new GridLayout(1, 2));
		// Agrego paneles
		tituloIda = new JLabel("");
		tituloIda.setSize(new Dimension(20, 60));
		tituloVuelta = new JLabel("");
		this.add(tituloIda);
		this.add(panelIda);
		this.add(tituloVuelta);
		this.add(panelVuelta);
		
		//Creo tablas
		tablaIda=new DBTable();
		tablaVuelta=new DBTable();
		tablaPIda=new DBTable();
		tablaPVuelta=new DBTable();
		
		//creo fechas
		//fechaI=new String("");
		//fechaV=new String("");
		
		
		//Creo boton
		botonReserva=new JButton("Reservar");
		botonReserva.addActionListener(new oyenteBotonReserva(tablaIda,tablaPIda,tablaVuelta,tablaPVuelta,legajo));
		this.add(botonReserva);
		ventanaInicio();

	}
	
	public String getFechaI(){
		return fechaI;
	}
	
	public String getFechaV(){
		return fechaV;
	}
	private void ventanaInicio() {
		inicio = new JPanel();

		inicio.setLayout(new GridLayout(5, 2));
		// ComboBox de salida
		ida = new JComboBox<String>();
		origen = new JLabel("Ciudad de origen:");
		inicio.add(origen);
		inicio.add(ida);

		// ComboBoxDestino
		vuelta = new JComboBox<String>();
		destino = new JLabel("Ciudad de destino");
		inicio.add(destino);
		inicio.add(vuelta);

		// Cargar ciudades

		cargarCiudades();

		// Fecha de salida
		fechaSalida = new JTextField();
		fechaSal = new JLabel("Fecha de salida(dd/mm/aaaa):");
		inicio.add(fechaSal);
		inicio.add(fechaSalida);

		// Check button
		checkVuelta = new JCheckBox();
		checkVuelta.addActionListener(new checkListener());
		checkVueltaLabel = new JLabel("ida y vuelta?");
		inicio.add(checkVueltaLabel);
		inicio.add(checkVuelta);

		// Fecha de vuelta
		fechaVuelta = new JTextField();
		fechaVuelta.setEnabled(false);
		fechaVuelt = new JLabel("Fecha de vuelta(dd/mm/aaaa):");
		inicio.add(fechaVuelt);
		inicio.add(fechaVuelta);

		// Creacion de ventana emergente

		String[] options = new String[] { "Ejecutar", "Cancelar" };
		int option = JOptionPane.showOptionDialog(null, inicio, "Ingrese la informacion del viaje.",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		String ciudadSalida, ciudadLLegada;
		hayVuelta = false;
		ciudadSalida = (String) ida.getSelectedItem();
		ciudadLLegada = (String) vuelta.getSelectedItem();
		fechaI=fechaSalida.getText();
		if (checkVuelta.isSelected()) {
			fechaV = fechaVuelta.getText();
			hayVuelta = true;
		}
		if (option == 0) {
			if (hayVuelta) {
				if (esFechaValida(fechaI) && esFechaValida(fechaV)) {
					crearTabla(ciudadSalida, ciudadLLegada, fechaI, tablaIda, tablaPIda, panelIda, tituloIda);
					crearTabla(ciudadLLegada, ciudadSalida, fechaV, tablaVuelta, tablaPVuelta, panelVuelta,
							tituloVuelta);
				}
			} else if (esFechaValida(fechaI)) {
				crearTabla(ciudadSalida, ciudadLLegada, fechaI, tablaIda, tablaPIda, panelIda, tituloIda);
			}

		}

	}

	private void crearTabla(String ciudadSalida, String ciudadLLegada, String fecha, DBTable tabla, DBTable tablaP,
			JPanel panel, JLabel titulo) {
		String sql = "SELECT DISTINCT nro_vuelo AS 'Numero de vuelo',aero_salida AS 'Aeropuerto de salida',salida AS 'Horario de salida',aero_llegada AS 'Aeropuerto de llegada',llegada AS 'Horario de llegada',avion AS Avion,tiempo_estimado AS 'Tiempo estimado' FROM vuelos_disponibles WHERE ciudad_salida='"
				+ ciudadSalida + "' AND ciudad_llegada='" + ciudadLLegada + "' AND fecha='"
				+ Fechas.convertirStringADateSQL(fecha) + "';";
		conectarBD(tabla);
		refrescarTabla(tabla, sql);
		titulo.setText("Vuelos que salen desde " + ciudadSalida + " hasta " + ciudadLLegada + " el " + fecha + ":");
		panel.add(tabla);
		tabla.setEditable(false);
		tabla.setVisible(true);
		tabla.addMouseListener(new oyenteTabla(tabla, panel, tablaP, fecha));
	}

	private void refrescarTabla(DBTable tabla, String s) {
		try {
			// seteamos la consulta a partir de la cual se obtendran los datos
			// para llenar la tabla
			tabla.setSelectSql(s.trim());

			// obtenemos el modelo de la tabla a partir de la consulta para
			// modificar la forma en que se muestran de algunas columnas
			tabla.createColumnModelFromQuery();
			for (int i = 0; i < tabla.getColumnCount(); i++) { // para que
																// muestre
																// correctamente
																// los valores
																// de tipo TIME
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
			tabla.refresh();
			// No es necesario establecer una conexion, crear una sentencia y
			// recuperar el
			// resultado en un resultSet, esto lo hace automaticamente la tabla
			// (DBTable) a
			// patir de la conexion y la consulta seteadas con connectDatabase()
			// y setSelectSql() respectivamente.

		} catch (SQLException ex) {
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n",
					"Error al ejecutar la consulta.", JOptionPane.ERROR_MESSAGE);

		}

	}

	private boolean esFechaValida(String fecha) {
		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
			formatoFecha.setLenient(false);
			formatoFecha.parse(fecha);
			return true;
		} catch (ParseException e) {
			mensajeError("La fecha ingresada " + fecha + " no es valida.");
			return false;
		}
	}

	private void mensajeError(String s) {
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, s, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
	}

	private void cargarCiudades() {
		String sql = "SELECT DISTINCT ciudad_salida from vuelos_disponibles;";
		String sql2 = "SELECT DISTINCT ciudad_llegada from vuelos_disponibles;";
		try {

			String servidor = "localhost:3306";
			String baseDatos = "vuelos";
			String usuario = "empleado";
			String clave = "empleado";
			String urlConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=America/Argentina/Buenos_Aires";

			Statement stmt = DriverManager.getConnection(urlConexion, usuario, clave).createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ciudad = rs.getString("ciudad_salida");
				ida.addItem(ciudad);
			}
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				String ciudad = rs.getString("ciudad_llegada");
				vuelta.addItem(ciudad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void conectarBD(DBTable tabla) {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "vuelos";
			String usuario = "empleado";
			String clave = "empleado";
			String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=America/Argentina/Buenos_Aires";

			// establece una conexion con la B.D. "batallas" usando directamante
			// una tabla DBTable
			tabla.connectDatabase(driver, uriConexion, usuario, clave);
			
			//c= DriverManager.getConnection(uriConexion,usuario,clave);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
					"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		/*} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private Connection conectarEmpleado(){
		try {
			String servidor = "localhost:3306";
			String baseDatos = "vuelos";
			String usuario = "empleado";
			String clave = "empleado";
			String urlConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=America/Argentina/Buenos_Aires";
			
			
			
			return DriverManager.getConnection(urlConexion, usuario, clave);
		}
			catch (SQLException ex) {
				JOptionPane.showMessageDialog(this,
					"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	private void tablaMouseClicked(MouseEvent evt, DBTable tabla, JPanel ubicacion, DBTable tablaPrecio, String fecha) {
		if ((tabla.getSelectedRow() != -1) && (evt.getClickCount() == 2)) {
			this.crearTablaPrecios(tabla, ubicacion, tablaPrecio, fecha);
		}
	}

	private class oyenteTabla extends MouseAdapter {
		private DBTable tabla, tablaPrecios;
		private JPanel ubicacion;
		private String fecha;

		public oyenteTabla(DBTable t, JPanel u, DBTable tp, String fecha) {
			tabla = t;
			tablaPrecios = tp;
			ubicacion = u;
			this.fecha = fecha;
		}

		public void mouseClicked(MouseEvent evt) {
			tablaMouseClicked(evt, tabla, ubicacion, tablaPrecios, this.fecha);
		}
	}

	private void crearTablaPrecios(DBTable tabla, JPanel ubicacion, DBTable tablaPrecio, String fecha) {
		String nroVuelo = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
		//System.out.println(fecha);
		String sql = "SELECT clase AS Clase,asientos_disponibles AS 'Asientos disponibles',precio AS Precio FROM vuelos_disponibles WHERE nro_vuelo='"
				+ nroVuelo + "' AND fecha='" + Fechas.convertirStringADateSQL(fecha) + "';";

		conectarBD(tablaPrecio);
		refrescarTabla(tablaPrecio, sql);
		ubicacion.add(tablaPrecio);
		ubicacion.repaint();
		this.pack();
		tablaPrecio.setEditable(false);
		tablaPrecio.setVisible(true);
		

	}

	private class checkListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			if (abstractButton.getModel().isSelected())
				fechaVuelta.setEnabled(true);
			else {
				fechaVuelta.setEnabled(false);
				fechaVuelta.setText("");
			}
		}
	}
	
	private class oyenteBotonReserva implements ActionListener{
		private DBTable ti,tip,tv,tvp;
		private String legajo,tdoc;
		private int ndoc;
		
		public oyenteBotonReserva(DBTable ti,DBTable tip, DBTable tv, DBTable tvp,String legajo){
			super();
			this.ti=ti;
			this.tip=tip;
			this.tv=tv;
			this.tvp=tvp;
			this.legajo=legajo;
			
		}
		public void actionPerformed(ActionEvent e) {
			
			
			int tIRow =ti.getSelectedRow();
			int tIPRow =tip.getSelectedRow();
			//System.out.println(""+tIRow+","+tIPRow);
			if (hayVuelta){
				int tVRow =tablaVuelta.getSelectedRow();
				int tVPRow =tablaPVuelta.getSelectedRow();
				if(tIRow!=-1 && tIPRow != -1 && tVRow != -1 && tVPRow != -1){
					recuperarInformacionConVuelta(tIRow,tIPRow,tVRow,tVPRow);
					
					try{
						
						
						String nroVueloIda = tablaIda.getValueAt(tablaIda.getSelectedRow(), 0).toString();
						//System.out.println(""+nroVueloIda);
						
						String nroVueloVuelta = tablaVuelta.getValueAt(tablaVuelta.getSelectedRow(), 0).toString();
						//System.out.println(""+nroVueloVuelta);
						
						claseIda= tip.getValueAt(tIPRow, 0).toString();
						//System.out.println(""+claseIda);
						
						claseVuelta= tvp.getValueAt(tVPRow, 0).toString();
						//System.out.println(""+claseVuelta);
						
						//System.out.println(""+fechaI);
						//System.out.println(""+fechaV);
						
						
					
						c= conectarEmpleado();
						st=c.prepareCall("{call reservar_vuelo_idaYVuelta(?,?,?,?,?,?,?,?,?,?)}");
	    				st.setString(1, tdoc);
	    				st.setString(2, String.valueOf(ndoc));
	    				st.setString(3, legajo);
	    				st.setString(4, nroVueloIda);
	    				st.setString(5, nroVueloVuelta);
	    				st.setDate(6, new java.sql.Date(obtenerFecha(fechaI).getTime()));
	    				st.setDate(7, new java.sql.Date(obtenerFecha(fechaV).getTime()));
	    				st.setString(8,claseIda);
	    				st.setString(9,claseVuelta);
	    				st.registerOutParameter(10, java.sql.Types.VARCHAR);	
	    				st.executeQuery();
	    				String salida=st.getString(10);
	    				JOptionPane.showMessageDialog(null,
	    	                    salida,
	    	                   	"Reserva de vuelos",
	    	                   	JOptionPane.INFORMATION_MESSAGE);
	    			}
	    			
	    			catch(SQLException ex){
		    			ex.printStackTrace();
		    		}
				}
				else{
					mensajeError("Debe seleccionar un vuelo y una clase de ida y vuelta de las tablas para poder hacer su reserva.");
					}
				}
			else{
				if(tIRow!=-1 && tIPRow != -1){
					recuperarInformacionSinVuelta(tIRow,tIPRow);
					
					try{
					
							String nroVuelo = tablaIda.getValueAt(tablaIda.getSelectedRow(), 0).toString();
							//System.out.println(""+nroVuelo);
							
							claseIda= tip.getValueAt(tIPRow, 0).toString();
							//System.out.println(""+claseIda);
						
							c= conectarEmpleado();
							st=c.prepareCall("{call reservar_vuelo_ida(?,?,?,?,?,?,?)}");
		    				st.setString(1, tdoc);
		    				st.setString(2, String.valueOf(ndoc));
		    				st.setString(3, legajo);
		    				st.setString(4, nroVuelo);
		    				st.setDate(5, new java.sql.Date(obtenerFecha(fechaI).getTime()));
		    				st.setString(6,claseIda);
		    				st.registerOutParameter(7, java.sql.Types.VARCHAR);	
		    				st.executeQuery();
		    				String salida=st.getString(7);
		    				JOptionPane.showMessageDialog(null,
		    	                    salida,
		    	                   	"Reserva de vuelos",
		    	                   	JOptionPane.INFORMATION_MESSAGE);
		    			}
		    			
		    			catch(SQLException ex){
			    			ex.printStackTrace();
			    		}
					
					
					
					
				}else{
					mensajeError("Debe seleccionar un vuelo y una clase de las tablas para poder hacer su reserva.");}
			}
			
		
		
		}
		private void recuperarInformacionSinVuelta(int tirow,int tiprow) 
		{
			pedirInformacionCliente();
			//System.out.println(""+fechaI+","+legajo+","+tirow+","+tiprow);
			
		}
		private void recuperarInformacionConVuelta(int tirow,int tiprow,int tvrow,int tvprow) 
		{
			pedirInformacionCliente();
		}
		
		private void pedirInformacionCliente(){
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 2));
			JLabel t = new JLabel("Tipo Doc.");
			JLabel n = new JLabel("Numero Doc.");
			JTextField tipodoc = new JTextField();
			JTextField nrodoc = new JTextField();
			panel.add(t);
			panel.add(tipodoc);
			panel.add(n);
			panel.add(nrodoc);
			String[] options = new String[] { "OK", "Cancelar" };
			int option = JOptionPane.showOptionDialog(null, panel, "Informacion del Cliente", JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (option == 0){
				tdoc=tipodoc.getText().toUpperCase();
				ndoc=Integer.parseInt(nrodoc.getText());
			}
		}
		
		private Date obtenerFecha(String s){
			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			 Date fecha1=null;

			try {
					//if not valid, it will throw ParseException
					fecha1 = sdf.parse(s);

			} catch (ParseException e) {
				System.out.println("errro date");
				}
			return fecha1;
		}

	}
}
	