package email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
}
