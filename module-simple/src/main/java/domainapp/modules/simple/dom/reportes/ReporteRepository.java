package domainapp.modules.simple.dom.reportes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.value.Blob;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReporteRepository {

	public Blob imprimirReporteIndividual(List<Object> objectsReport, String jrxml, String nombreArchivo) throws JRException, IOException{
		
		InputStream input = ReporteRepository.class.getResourceAsStream(jrxml);
		JasperDesign jd = JRXmlLoader.load(input);
		
		JRBeanArrayDataSource jArray= new JRBeanArrayDataSource(objectsReport.toArray());
		JasperReport reporte = JasperCompileManager.compileReport(jd);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jArray);
		
		File pdf = File.createTempFile("output.", ".pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
		
		byte[] fileContent = new byte[(int) pdf.length()];
		
		if (!(pdf.exists())) {
			try {
				pdf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(pdf);
			fileInputStream.read(fileContent);
			fileInputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return new Blob(nombreArchivo+".pdf", "application/pdf", fileContent);
			
		} catch (Exception e) {
			e.printStackTrace();
			byte[] result = new String(e.toString()).getBytes();
			return new Blob("error.txt", "text/plain", result);
		}
		
	}
	
	public Blob imprimirReporteIndividual() {
		byte[] result = new String("prueba").getBytes();
		return new Blob("error.txt", "text/plain", result);
	}
	
	public static Blob imprimirReporteLista(JasperPrint jasperPrint, String jrxml, String nombreArchivo) throws JRException, IOException{
		
		File pdf = File.createTempFile("output.", ".pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
		
		byte[] fileContent = new byte[(int) pdf.length()];
		
		if (!(pdf.exists())) {
			try {
				pdf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(pdf);
			fileInputStream.read(fileContent);
			fileInputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return new Blob(nombreArchivo + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now())+".pdf", "application/pdf", fileContent);
			
		} catch (Exception e) {
			byte[] result = new String("error en crear archivo").getBytes();
			return new Blob("error.txt", "text/plain", result);
		}
		
	}

}
