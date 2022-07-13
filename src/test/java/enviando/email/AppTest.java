package enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import email.EnvioEmail;

public class AppTest {

	private String userName ="seu-melhor-email@gmail.com";
	private String password = "nao-vou-revelar-a-senha";

	@Test
	public void testeEmail() {
		try {
			Properties properties = new Properties();
			
			properties.put("mail.smtp.ssl.trust", "*");
			
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
			
			Address[] toUser = InternetAddress.parse("tadeupalermoti@gmail.com,alex.fernando.egidio@gmail.com");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, "Tadeu Espindola Palermo"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Chegou e-mail enviado com Java pelo Tadeu!");
			message.setText("Parabéns! Você recebeu um e-mail do Tadeu, enviado com JavaMail!");
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeEmailComObjeto() {
		EnvioEmail envioEmail = new EnvioEmail(
			"tadeupalermoti@gmail.com,alex.fernando.egidio@gmail.com", 
			"Tadeu Espindola Palermo", 
			"Chegou e-mail enviado com Java pelo Tadeu - Com objeto de envio!", 
			"Parabéns! Você recebeu um e-mail do Tadeu, enviado com JavaMail! Com objeto de envio!"
		);
		
		envioEmail.enviarEmail(false);
	}
	
	@Test
	public void testeEmailComObjetoHTML() {
		
		String remetente = "Alex";
		
		StringBuilder html = new StringBuilder();
		
		html
			.append("Olá, ")
			.append(remetente)
			.append("!")
			.append("<br/><br/>");
		
		html.append("Você está recebendo o acesso ao curso de Java<br/><br/>");
		
		html.append("Para ter acesso clique no botão abaixo.<br/><br/>");
		
		html.append("<b>Login: mybestlogin@gmail.com</b><br/>");
		html.append("<b>Senha: java-the-best</b><br/><br/>");
		
		html.append("<a target=\"_blank\" href=\"https://www.cobradev.com.br\" style=\"color: #2525A7; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block; border-radius: 30px; font-size: 20px; font-family: courier; border: 3px solid green; background-color: #99DA39; font-weight: 900;\">Acessar Portal</a><br/><br/>");
		
		html.append("<span style=\"font-size: 10px;\">Ass: CobraDev Cursos Tecnológicos</span>");
		
		EnvioEmail envioEmail = new EnvioEmail(
			"tadeupalermoti@gmail.com,alex.fernando.egidio@gmail.com", 
			"Tadeu Espindola Palermo", 
			"Chegou e-mail enviado com Java pelo Tadeu - Com objeto de envio!", 
			html.toString()
		);
		
		envioEmail.enviarEmail(true);
	}
	
}
