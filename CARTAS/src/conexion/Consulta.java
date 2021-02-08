package conexion;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consulta {

	public Connection connection() throws ClassNotFoundException, SQLException, IOException {
		Connection connection = null;
		try {
			Properties properties = new Properties();
			properties.load(new FileReader("src/conexion/db.properties"));
			String driver = properties.getProperty("database.driver");
			String url = properties.getProperty("database.url");
			String user = properties.getProperty("database.user");
			String password = properties.getProperty("database.password");

			// Relizamos el registro del driver y obtenemos la conexion
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);

			connection.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			throw e;
		}
		return connection;
	}

	/**
	 * Desconecta de la bbdd
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public void disconect(Connection connection) throws SQLException {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Devuelve todas las cartas
	 * 
	 * @param nombre
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Carta devuelveCarta(String nombre) throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Consulta consulta = new Consulta();
		Carta carta=null;
		
		try {
			connection = consulta.connection();
			preparedStatement = connection.prepareStatement("SELECT * FROM CARTAS WHERE NOMBRE = ?");
			preparedStatement.setString(1, nombre);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				carta = new Carta();
				carta.setAtaque(resultSet.getInt("ATAQUE"));
				carta.setCalidad(resultSet.getString("CALIDAD"));
				carta.setFotoURL(resultSet.getString("FOTO"));
				carta.setNombre(resultSet.getString("NOMBRE"));
				carta.setVida(resultSet.getInt("VIDA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// cerramos todos los resources
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					consulta.disconect(connection);
					;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return carta;
	}
}
