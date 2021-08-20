import java.sql.Connection;
import java.sql.SQLException;

import datos.Conexion;
import datos.PersonaDAO;
import domain.Persona;

public class TestManejoPersonas {
	public static void main(String[] args) {

		Connection conexion = null;
		try {
			conexion = Conexion.getConnection();
			if (conexion.getAutoCommit())
				conexion.setAutoCommit(false);

			PersonaDAO personaDao = new PersonaDAO(conexion);
			Persona persona = new Persona(2, "Eluney", "Perdomoss", "elu@gmail.com", "15368-1325");
			personaDao.actualizar(persona);

			Persona personaNueva = new Persona("Susu", "Perdomo", "susu@gmail.com");
			personaDao.insertar(personaNueva);

			conexion.commit();
			System.out.println("commit realizado con exito");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
			System.out.println("Entramos al roolback");
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(System.out);
			}

		}

//		

	}
}
