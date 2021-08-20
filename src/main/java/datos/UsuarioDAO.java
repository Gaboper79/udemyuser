package datos;

import static datos.Conexion.close;
import static datos.Conexion.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Usuario;

public class UsuarioDAO {
	private Connection conexionTrans;
	private static final String SQL_SELECT = "SELECT * FROM usuario";
	private static final String SQL_INSERT = "INSERT INTO usuario(usuario,password) VALUES(?, ?)";
	private static final String SQL_UPDATE = "UPDATE usuario SET usuario=?,password=? where idusuario=?";
	private static final String SQL_DELETE = "DELETE  FROM usuario where idusuario=?";

	public UsuarioDAO() {

	}

	public UsuarioDAO(Connection conexionTrans) {
		super();
		this.conexionTrans = conexionTrans;
	}

	public List<Usuario> listarUsuarios() throws SQLException {
		Connection conn = null; // la conexxion a la bd
		PreparedStatement stmt = null; // metodo para ejecutar las consultas
		ResultSet rs = null; // aca se guarda el resultado de la consulta
		Usuario usuario = null;
		List<Usuario> usuarios = new ArrayList<>();

		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();
			stmt = conn.prepareStatement(SQL_SELECT);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int idUsuario = rs.getInt("idusuario");
				String usuarioNom = rs.getString("usuario");
				String pass = rs.getString("password");

				usuario = new Usuario(idUsuario, usuarioNom, pass);
				usuarios.add(usuario);

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
		return usuarios;
	}

	public int ingresarUsuario(Usuario user) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int registros = 0;
		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();

			stmt = conn.prepareStatement(SQL_INSERT);

			stmt.setString(1, user.getUsuario());
			stmt.setString(2, user.getPassword());

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

	public int modificarUsuario(Usuario user) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int registros = 0;
		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();

			stmt = conn.prepareStatement(SQL_UPDATE);

			stmt.setString(1, user.getUsuario());
			stmt.setString(2, user.getPassword());
			stmt.setInt(3, user.getIdusuario());
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

	public int eliminarUsuario(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int registros = 0;
		try {
			conn = this.conexionTrans != null ? this.conexionTrans : getConnection();

			stmt = conn.prepareStatement(SQL_DELETE);

			stmt.setInt(1, id);
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
}
