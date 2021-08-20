import java.sql.Connection;
import java.sql.SQLException;

import datos.Conexion;
import datos.UsuarioDAO;
import domain.Usuario;

public class TestManejoUsuarios {
	public static void main(String[] args) {
		Connection conexion = null;

		try {
			conexion = Conexion.getConnection();
			if (conexion.getAutoCommit())
				conexion.setAutoCommit(false);
			UsuarioDAO userdao = new UsuarioDAO(conexion);

			Usuario userUpdate = new Usuario(3, "Lau81", "12356");
			userdao.modificarUsuario(userUpdate);

			Usuario userNew = new Usuario("Susu", "12345");
			userdao.ingresarUsuario(userNew);

			conexion.commit();
			System.out.println("Commit realizado con exito");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
			System.out.println("entramos al rolblack");
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(System.out);
			}
		}

	}
}
