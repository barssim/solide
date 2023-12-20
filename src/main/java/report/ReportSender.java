package report;

import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import article.ArticleFacade;
import models.ArticleBean;


public class ReportSender {
	  
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
     
    private static String subject = "SolideQueue"; // Queue Name.You can create any/many queue names as per your requirement. 
     
    public static void main(String[] args) throws Exception {        
        // Getting JMS connection from the server and starting it
    	ConnectionFactory connFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connFactory.createConnection();
        connection.start();
         
        //Creating a non transactional session to send/receive JMS message.
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);  
         
        //Destination represents here our queue 'SolideQueue' on the JMS server. 
        //The queue will be created automatically on the server.
        Destination destination = session.createQueue(subject); 
         
        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);
         
        // We will send a all articles not reserved
        
        List<ArticleBean> myArticlesList = retrievData();
        TextMessage msg = null;
        for( ArticleBean articleBean : myArticlesList)
        {
        	msg = session.createTextMessage();
            msg.setIntProperty("artNo", articleBean.getArticleNo());
            msg.setStringProperty("artName", articleBean.getArticleName()); 
            msg.setStringProperty("artDesc", articleBean.getArticleDescription());
            msg.setIntProperty("artPrice", articleBean.getArticlePrice());
            msg.setStringProperty("artType", articleBean.getArticleType());
            msg.setStringProperty("artModel", articleBean.getArticleModel());
            msg.setIntProperty("artCategory", articleBean.getArticleCategory());
            msg.setStringProperty("artOldNew", articleBean.getArticleOldNew());
            msg.setStringProperty("artStatus", articleBean.getArticleStatus());
            // Here we are sending our message!
            producer.send(msg);
        }
        connection.close();
    }
    
  
	private static List<ArticleBean> retrievData() throws Exception {
		ArticleFacade articleFacade = null;
		List<ArticleBean> myArticlesList = null;
		articleFacade = new ArticleFacade(null, null);
		myArticlesList = articleFacade.fetchAllArticles();
		return myArticlesList;

	}
}