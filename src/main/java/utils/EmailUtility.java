package utils;



import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A utility class for sending e-mail messages
 * 
 * @author www.codejava.net
 *
 */
public class EmailUtility {	
	
	/**
	 * Send an email.<br>
	 * 
	 * @param host
	 *            The host of the SMTP Server
	 * @param port
	 *            The port of the SMTP Server
	 * @param userName
	 *            The login userName
	 * @param password
	 *            The login password
	 * @param fromName
	 *            The name for from 
	 * @param fromAddress
	 *            The email address from
	 * @param toName
	 *            The name for to field
	 * @param toAddress
	 *            The email address to
	 * @param subject
	 *            The subject
	 * @param message
	 *            The message of email
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendEmail(String host, String port, final String userName, final String password,
			String fromName, String fromAddress, String toName, String toAddress, String subject, String message)
			throws AddressException, MessagingException, UnsupportedEncodingException {

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		//  creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(fromAddress, fromName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress, toName) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setText(message);

		// sends the e-mail
		Transport.send(msg);

	}
}
