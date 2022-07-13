package email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class EnvioEmail {
	
	private String userName ="seu-melhor-email@gmail.com";
	private String password = "nao-vou-revelar-a-senha";
	
	private String destinatarios = "";
	private String remetente = "";
	private String assunto = "";
	private String texto = "";
	
	public EnvioEmail(String destinatarios, String remetente, String assunto, String texto) {
		this.destinatarios = destinatarios;
		this.remetente = remetente;
		this.assunto = assunto;
		this.texto = texto;
	}
	
	public void enviarEmail(boolean html) {
		try {
			Properties properties = new Properties();
			
			// Alex porque o envio dele parou de funcionar
			properties.put("mail.smtp.ssl.trust", "*"); 
			
			// Tadeu Sonar Lint por causa do "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"
			properties.put("mail.smtp.ssl.checkserveridentity", "true"); 
			
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
			
			Address[] toUser = InternetAddress.parse(destinatarios);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, remetente));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			
			if (html) {
				message.setContent(texto, "text/html; charset=utf-8");
			} else {
				message.setText(texto);
			}
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviarEmailComAnexo(boolean html) {
		try {
			Properties properties = new Properties();
			
			// Alex porque o envio dele parou de funcionar
			properties.put("mail.smtp.ssl.trust", "*"); 
			
			// Tadeu Sonar Lint por causa do "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"
			properties.put("mail.smtp.ssl.checkserveridentity", "true"); 
			
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
			
			Address[] toUser = InternetAddress.parse(destinatarios);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, remetente));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			
			
			// Texto
			MimeBodyPart body = new MimeBodyPart();
			
			if (html) {
				body.setContent(texto, "text/html; charset=utf-8");
			} else {
				body.setText(texto);
			}
			
			List<FileInputStream> anexos = new ArrayList<>();
			anexos.add(simularPDF(1));
			anexos.add(simularPDF(2));
			anexos.add(simularPDF(3));
			anexos.add(simularPDF(4));
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);
			
			int index = 0;
			for (FileInputStream file : anexos) {
				// PDF anexo
				MimeBodyPart anexo = new MimeBodyPart();
				anexo.setDataHandler(new DataHandler(new ByteArrayDataSource(file, "application/pdf")));
				anexo.setFileName("anexo-pdf-itext-" + index + ".pdf");
				multipart.addBodyPart(anexo);
				index ++;
			}
			
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private FileInputStream simularPDF(int index) {
		try {
			Document document = new Document();
			
			File file = new File("file-anexo-" + index + ".pdf");
			boolean createNewFile = file.createNewFile();
			
			PdfWriter.getInstance(document, new FileOutputStream(file));
			
			document.open();
			document.add(new Paragraph("Conteúdo 00" + index + " do PDF em anexo com JavaMail iText. Tadeu E. P."));
			document.close();
			return createNewFile ? new FileInputStream(file) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
