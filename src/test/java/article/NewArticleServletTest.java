package article;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import models.ArticleBean;
import models.UserBean;
import utils.DatabaseClass;
import utils.ServerConfiguration;
import utils.SolideConstants;
import utils.SolideLogCreater;;

public class NewArticleServletTest extends Mockito{
//	public static ServerConfiguration clsConfig;
//	public static DatabaseClass clsDB;
//	private UserBean userBean;
//	private ArticleBean articleBean;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		
//		    clsConfig = ServerConfiguration.getInstance();			
//			clsConfig.confige();
//			// new SolideLogCreater(clsConfig);
//			clsDB = clsConfig.clsDB;			
//	}
//	
//	
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		String sql = "insert into tb_article values('Article', '900000001','999999999', '999999999','2016-11-06','1','1','52')";
//		try(PreparedStatement pstCreate = clsDB.getConnection().prepareStatement(sql))
//		{
//			pstCreate.execute();
//		};
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//		String sql = "delete from tb_article where noobj = ? and locno = ? ";
//		try(PreparedStatement pstDelete = clsDB.getConnection().prepareStatement(sql))
//		{
//			pstDelete.setString(1, "Article");
//			pstDelete.setInt(2, 52);
//			pstDelete.execute();
//		};
//	}
//
//	@Test
//	public void testDoPost() throws Exception {
//		HttpServletRequest request = mock(HttpServletRequest.class);       
//        HttpServletResponse response = mock(HttpServletResponse.class);   
//        String resultMessage = SolideConstants.unsuccessfullArticleAded;
//		
//		articleBean = new ArticleBean();
//		userBean = new UserBean();
//		userBean.setUserName("Adil");		
//		articleBean.setArticleCategory(9);
//		articleBean.setArticleDescription("sdflksjdflk");
//		articleBean.setArticleManufacturer("MAZDA");;
//		articleBean.setArticleName("capot Mazda");
//		articleBean.setArticleOldNew("New");
//		articleBean.setArticlePrice(100);
//		
//		// Create a factory for disk-based file items
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        // Sets the size threshold beyond which files are written directly to
//        // disk.
//        factory.setSizeThreshold(MAX_MEMORY_SIZE);
//
//        // Sets the directory used to temporarily store files that are larger
//        // than the configured size threshold. We use temporary directory for
//        // java
//        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setSizeMax(MAX_REQUEST_SIZE);
//		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	        int add = 16;
//	        int num = 0;
//	        for (int i = 0;  i < 16384;  i += add) {
//	            if (++add == 32) {
//	                add = 16;
//	            }
//	            String header = "-----1234\r\n"
//	                + "Content-Disposition: form-data; name=\"field" + (num++) + "\"\r\n"
//	                + "\r\n";
//	            baos.write(header.getBytes("US-ASCII"));
//	            for (int j = 0;  j < i;  j++) {
//	                baos.write((byte) j);
//	            }
//	            baos.write("\r\n".getBytes("US-ASCII"));
//	        }
//	        baos.write("-----1234--\r\n".getBytes("US-ASCII"));
//	        
//	        Mockito.when(upload.parseRequest(request)).thenReturn(fileItems);
//---
//	        List fileItems = (new FileUploadTestCase()).parseUpload(baos.toByteArray());
//		
//		new NewArticleServlet().doPost(request, response);
//		Assert.assertEquals(request.getAttribute("Message"), resultMessage);
//		
//	}
//	
}
