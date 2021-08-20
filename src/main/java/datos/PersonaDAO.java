package datos;

import static datos.Conexion.close;
import static datos.Conexion.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Persona;

public class PersonaDAO {
	private Connection conexionTrans;

	private static final String SQL_SELECT = "SELECT * FROM persona";
	private static final String SQL_INSERT = "INSERT INTO persona(nombre,apellido,email,telefono) VALUES(?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE persona SET nombre=?,apellido=?,email=?,telefono=? where persona=?";
	private static final String SQL_DELETE = "DELETE  FROM persona where persona=?";

	public PersonaDAO() {

	}

	public PersonaDAO(Connection conn) {
		this.conexionTrans = conn;
	}

	public List<Persona> seleccionar() throws SQLException {

		Connection conn = null; // la conexxion a la bd
		PreparedStatement stmt = null; // metodo para ejecutar las consultas
		ResultSet rs = null; // aca se guarda el resultado de la consulta
		Persona persona = null;
		List<Persona> personas = new ArrayList<>();

		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();
			stmt = conn.prepareStatement(SQL_SELECT);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int idPersona = rs.getInt("persona");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String email = rs.getString("email");
				String telefono = rs.getString("telefono");

				persona = new Persona(idPersona, nombre, apellido, email, telefono);
				personas.add(persona);

			}

		} finally {
			try {
				Conexion.close(rs);
				Conexion.close(rs);
				if (this.conexionTrans == null)
					Conexion.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}
		}
		return personas;
	}

	public int insertar(Persona persona) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int registros = 0;
		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();

			stmt = conn.prepareStatement(SQL_INSERT);

			stmt.setString(1, persona.getNombre());
			stmt.setString(2, persona.getApellido());
			stmt.setString(3, persona.getEmail());
			stmt.setString(4, persona.getTelefono());

			registros = stmt.executeUpdate();

		} finally {
			try {
				close(stmt);
				if (this.conexionTrans == null)
					Conexion.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}
		}
		return registros;
	}

	public int actualizar(Persona persona) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int registros = 0;

		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE);

			stmt.setString(1, persona.getNombre());
			stmt.setString(2, persona.getApellido());
			stmt.setString(3, persona.getEmail());
			stmt.setString(4, persona.getTelefono());
			stmt.setInt(5, persona.getIdPersona());
			registros = stmt.executeUpdate();

		} finally {
			try {
				close(stmt);
				if (this.conexionTrans == null)
					Conexion.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}
		}
		return registros;
	}

	public boolean eliminar(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();
			stmt = conn.prepareStatement(SQL_DELETE);

			stmt.setInt(1, id);
			stmt.executeUpdate();

		} finally {
			try {
				close(stmt);
				if (this.conexionTrans == null)
					Conexion.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}
		}
		return true;
	}
}
