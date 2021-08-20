package mycompani.prueba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMySqlJDBC {
	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/Test?useSSL=false&useTimezone=ture&serverTimezone=UTC&allowPublicKeyRetrieval=true";

		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection(url, "root", "1234");
			java.sql.Statement instruccion = conexion.createStatement();
			String sql = "SELECT * FROM persona";
			ResultSet resulado = instruccion.executeQuery(sql);

			while (resulado.next()) {
				System.out.println("id:" + resulado.getInt("persona") + ", Nombre:" + resulado.getString("nombre") + " "
						+ resulado.getString("apellido") + ", Email:" + resulado.getString("email") + ", Tel:"
						+ resulado.getString("telefono"));
			}

			resulado.close();
			instruccion.close();
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
	}
}
