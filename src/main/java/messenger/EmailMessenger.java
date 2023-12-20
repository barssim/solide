package messenger;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import utils.EmailUtility;
import utils.ServerConfiguration;

public class EmailMessenger {
	private static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	String messageToSend;
	String toName; 
	String toAddress;
	String subject;

	public EmailMessenger( String messageToSend, String toName, String toAddress, String subject) {
		super();
		this.messageToSend = messageToSend;
		this.toName = toName;
		this.toAddress = toAddress;
		this.subject = subject;
		
	}
	public void sendEmail() throws AddressException, UnsupportedEncodingException, MessagingException
	{
		EmailUtility.sendEmail(clsConfig.emailHost, 
				clsConfig.emailPort,
				clsConfig.emailUser, 
				clsConfig.emailpass,
				clsConfig.emailFromName,
				clsConfig.emailFromAddress,
				toName, 
				toAddress,
				subject,
				messageToSend);
	}
}
