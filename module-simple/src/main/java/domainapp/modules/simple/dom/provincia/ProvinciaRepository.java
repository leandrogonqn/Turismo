package domainapp.modules.simple.dom.provincia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.message.MessageService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Provincia.class)
public class ProvinciaRepository {
	
	private Connection conexion = null;
	//para la conexion local -->
//	private String url = "jdbc:mysql://localhost:3306/afiliacion";
//	private String user = "root";
//	private String password = "Lean3366";
	//para la conexion a la mutual -->
	private String url = "jdbc:mysql://192.168.0.6:3306/afiliacion";
	private String user = "turismo";
	private String password = "pass";
	
	public List<Provincia> listar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			messageService.warnUser(ex.getMessage());
		}
		List<Provincia> provincias = new LinkedList<Provincia>();
		try {
			conexion = DriverManager.getConnection(url, user, password);
			if(!conexion.isClosed()) {
				String consulta = "select * from provincias";

				PreparedStatement stmt = conexion.prepareStatement(consulta);
				
				ResultSet rs = stmt.executeQuery(); 

				while(rs.next()){
					Provincia provincia= new Provincia();
					provincia.setProvinciasId(rs.getInt(1));
					provincia.setProvinciasNombre(rs.getString(2));
					provincias.add(provincia);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			}catch(SQLException ex) {
				messageService.warnUser(ex.getMessage());
			}
		}
		return provincias;
	}
	
	public Provincia buscarPorId(final int provinciasId) {
		Provincia provincia = new Provincia();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			messageService.warnUser(ex.getMessage());
		}
		try {
			conexion = DriverManager.getConnection(url, user, password);
			if(!conexion.isClosed()) {
				String consulta = "select * from provincias where id = ?";
				PreparedStatement stmt = conexion.prepareStatement(consulta);
				stmt.setInt(1, provinciasId);
				
				ResultSet rs = stmt.executeQuery(); 
				
				if(rs.next()) {
					provincia.setProvinciasId(rs.getInt(1));
					provincia.setProvinciasNombre(rs.getString(2));
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			}catch(SQLException ex) {
				messageService.warnUser(ex.getMessage());
			}
		}
		return provincia;
	}

	public List<Provincia> buscarPorNombre(final String provinciasNombre) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			messageService.warnUser(ex.getMessage());
		}
		List<Provincia> provincias = new LinkedList<Provincia>();
		try {
			conexion = DriverManager.getConnection(url, user, password);
			if(!conexion.isClosed()) {
				String consulta = "select * from provincias WHERE lower(nombre) LIKE '%"+provinciasNombre.toLowerCase()+"%'";

				PreparedStatement stmt = conexion.prepareStatement(consulta);
				
				ResultSet rs = stmt.executeQuery(); 

				while(rs.next()){
					Provincia provincia = new Provincia();
					provincia.setProvinciasId(rs.getInt(1));
					provincia.setProvinciasNombre(rs.getString(2));
					provincias.add(provincia);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			}catch(SQLException ex) {
				messageService.warnUser(ex.getMessage());
			}
		}
		return provincias;
	}
	
	@Inject
	MessageService messageService;

}
