package messenger;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import utils.DatabaseClass;
import utils.ServerConfiguration;

@Ignore
public class EmailMessengerTest {
	public static ServerConfiguration clsConfig;
	public static DatabaseClass clsDB;
	private EmailMessenger emailMessenger;

	// test data
	String messagTestToSend = " Message test to send";
	String toTestName = "toTestName";
	String toTestAddress = "qarfaoui@gmail.com";
	String subject = "EmailMessenger test";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		clsConfig = ServerConfiguration.getInstance();
		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		clsDB = clsConfig.clsDB;

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendEmail() throws AddressException, MessagingException, UnsupportedEncodingException {
		emailMessenger = new EmailMessenger(messagTestToSend, toTestName, toTestAddress, subject);
		emailMessenger.sendEmail();
	}

}
