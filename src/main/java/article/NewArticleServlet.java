package article;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import dbServices.Category;
import dbServices.User;
import messenger.EmailMessenger;
import models.ArticleBean;
import models.UserBean;
import utils.NoRanger;
import utils.ServerConfiguration;
import utils.SolideConstants;

//import javax.ws.rs.GET;  
//import javax.ws.rs.Path;  
//import javax.ws.rs.Produces;  
//import javax.ws.rs.core.MediaType;  

/**
 * A servlet that takes message details from user and send it as a new e-mail
 * through an SMTP server.
 *
 * @author www.codejava.net
 *
 */
// @WebServlet("/NewArticleServlet")
@MultipartConfig(maxFileSize = 16177215)
public class NewArticleServlet extends HttpServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private UserBean userBean;
	private ArticleBean articleBean;
	private Category clsCategory;
	// private NewArticleFacade newArticleFacade;
	Logger logLogger = null;
	ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	EmailMessenger emailMessenger;
	private User clsUser;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// reads form fields
		clsConfig.confige();
		articleBean = new ArticleBean();
		userBean = new UserBean();
		clsCategory = new Category();
		userBean.setUserName(request.getSession().getAttribute("loggedInUser").toString());
		if (clsUser == null) {
			clsUser = new User();
		}
		int inserted = 0;
		String resultMessage = SolideConstants.UNSUCCESSFULL_ARTICLE_ADED;
		try {
			int locNo = 0;
			try (ResultSet rs = clsUser.getRecord(userBean)) {
				if (rs.next()) {
					locNo = rs.getInt(User.FLD_LOCATIONNO);
				}
			}

			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Sets the size threshold beyond which files are written directly to
			// disk.
			factory.setSizeThreshold(Integer.valueOf(clsConfig.max_memory_size));

			
			// create articlePhotos directory for this new user if not exist
			String strArticlePhotosPath = clsConfig.articlesPhotosPath;
			if (strArticlePhotosPath != null) {
				if (!strArticlePhotosPath.endsWith(System.getProperty("file.separator"))) {
					strArticlePhotosPath = strArticlePhotosPath + System.getProperty("file.separator");
				}
			}
//				File userFile = new File(strArticlePhotosPath + userBean.getUserName());
//
//				if (!userFile.exists()) {
//					userFile.mkdirs();
//				}
				// constructs the folder where uploaded file will be stored
				String uploadFolder = strArticlePhotosPath;
				// Database link for images
				String strImagesLink = clsConfig.ImagesLink;
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);

				// Set overall request size constraint
				upload.setSizeMax(Integer.valueOf(clsConfig.max_request_size));
				// Parse the request
				List<FileItem> items = upload.parseRequest(request); 
				if (! items.isEmpty()) {
					Iterator<FileItem> iter = items.iterator();
					int imageCounter = 0;
					while (iter.hasNext()) {
						FileItem item = (FileItem) iter.next();
						if (!item.isFormField()) {
							imageCounter++;
							String fileName = new File(item.getName()).getName();
							String filePath = uploadFolder +  fileName;
							File uploadedFile = new File(filePath);
							
							String targetFileName = strImagesLink + System.getProperty("file.separator") + NoRanger.getNext(SolideConstants.ARTICLE) + "_" + imageCounter + "."
									+ FilenameUtils.getExtension(filePath);
							File targetFile = new File(uploadFolder + NoRanger.getNext(SolideConstants.ARTICLE) + "_" + imageCounter + "."
									+ FilenameUtils.getExtension(filePath));
							// saves the file to upload directory
							if (!fileName.isEmpty()) {
								item.write(uploadedFile);
								uploadedFile.renameTo(targetFile);
							}
							if (imageCounter == 1) {
								articleBean.setArticleImage1(targetFileName);
							}
							if (imageCounter == 2) {
								articleBean.setArticleImage2(targetFileName);
							}
							if (imageCounter == 3) {
								articleBean.setArticleImage3(targetFileName);
							}

						}
						else
						{
							if(item.getFieldName().equals("piece"))
							{
								articleBean.setArticleName(item.getString());
							}
							if(item.getFieldName().equals("category"))
							{
								int categoryNo = 0;

								ResultSet rs = clsCategory.getRecord(item.getString());

								if (rs.next()) {
									categoryNo = rs.getInt(Category.FLD_CATEGORYNO);
								}
								articleBean.setArticleCategory(categoryNo);
							}

							if(item.getFieldName().equals("description"))
							{
								articleBean.setArticleDescription(item.getString());
							}
							if(item.getFieldName().equals("oldNew"))
							{
								articleBean.setArticleOldNew(item.getString());
							}
							if(item.getFieldName().equals("constructeur"))
							{
								articleBean.setArticleManufacturer(item.getString());
							}
							if(item.getFieldName().equals("model"))
							{
								articleBean.setArticleModel(item.getString());
							}
							if(item.getFieldName().equals("type"))
							{
								articleBean.setArticleType(item.getString());
							}
							if(item.getFieldName().equals("price"))
							{
								String strPrice = StringUtils.trimToEmpty(item.getString());
								if (strPrice != null) {
									articleBean.setArticlePrice(Integer.parseInt(strPrice));
								}
							}
						}
					}
				}
		

			ArticleFacade articleFacade = new ArticleFacade(articleBean, userBean);
			inserted = articleFacade.insertNewArticle();
			if (inserted != -1) {
				resultMessage = SolideConstants.SUCCESSFULL_ARTICLE_ADED;
			} else {
				resultMessage = SolideConstants.UNSUCCESSFULL_ARTICLE_ADED;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			
		}

		finally {

			request.setAttribute("Message", resultMessage);
			getServletContext().getRequestDispatcher("/mySolide.jsp").forward(request, response);
		}
	}


}