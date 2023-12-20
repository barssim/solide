package models;


public class ArticleBean {
  String articleName;
  int articleCategory;
  String articleDescription;
  
String articleModel;
  String articleType;
  int articlePrice;
  String articleOldNew;
  String articleImage1;
  String articleImage2;
String articleImage3;
  int articleNo;
  String articleStatus;
  String barcode;
  int OwnerNo;
  

public ArticleBean() {
	super();
}

/**
   * 
   * @param articleName
   * @param articleCategory
   * @param articleDescription
   * @param articleModel
   * @param articleType
   * @param articlePrice
   * @param articleOldNew
   * @param articleImage1
   * @param articleImage2
   * @param articleImage3
   * @param articleNo
   * @param articleStatus
   * @param articleManufacturer
   */
  public ArticleBean(String articleName, int articleCategory, String articleDescription, String articleModel,
		String articleType, int articlePrice, String articleOldNew, int articleNo, String articleStatus, String barcode, int OwnerNo) {
	super();
	this.articleName = articleName;
	this.articleCategory = articleCategory;
	this.articleDescription = articleDescription;
	this.articleModel = articleModel;
	this.articleType = articleType;
	this.articlePrice = articlePrice;
	this.articleOldNew = articleOldNew;
	this.articleNo = articleNo;
	this.articleStatus = articleStatus;
	this.barcode = barcode;
	this.OwnerNo = OwnerNo;
}
  
public int getOwnerNo() {
	return OwnerNo;
}

public void setOwnerNo(int ownerNo) {
	OwnerNo = ownerNo;
}

public String getArticleStatus() {
	  return articleStatus;
  }
  public void setArticleStatus(String articleStatus) {
	  this.articleStatus = articleStatus;
  }
  
  public int getArticleNo() {
	return articleNo;
}
public void setArticleNo(int articleNo) {
	this.articleNo = articleNo;
}
public int getArticleCategory() {
		return articleCategory;
	}
	public void setArticleCategory(int articleCategory) {
		this.articleCategory = articleCategory;
	}
public String getArticleOldNew() {
	return articleOldNew;
}
public void setArticleOldNew(String articleOldNew) {
	this.articleOldNew = articleOldNew;
}
public String getArticleName() {
	return articleName;
}
public void setArticleName(String articleName) {
	this.articleName = articleName;
}
public String getArticleDescription() {
	return articleDescription;
}
public void setArticleDescription(String articleDescription) {
	this.articleDescription = articleDescription;
}
public int getArticlePrice() {
	return articlePrice;
}
public void setArticlePrice(int articlePrice) {
	this.articlePrice = articlePrice;
}
public String getArticleImage1() {
	return articleImage1;
}
public String getArticleImage1WithParcedChars() {
	if( articleImage1 != null)
	{
		articleImage1 = articleImage1.replace( "\\", "%2F").replace( ":", "%3A").replace( "/", "%2F").replace( " ", "%20");
	}
	return articleImage1;
}
public String getArticleImage2WithParcedChars() {
	if( articleImage2 != null)
	{
		articleImage2 = articleImage2.replace( "\\", "%2F").replace( ":", "%3A").replace( "/", "%2F").replace( " ", "%20");
	}
	return articleImage2;
}
public String getArticleImage3WithParcedChars() {
	if( articleImage3 != null)
	{
		articleImage3 = articleImage3.replace( "\\", "%2F").replace( ":", "%3A").replace( "/", "%2F").replace( " ", "%20");
	}
	return articleImage3;
}
public void setArticleImage1(String articleImage1) {
	this.articleImage1 = articleImage1;
}
public String getArticleImage2() {
	return articleImage2;
}
public void setArticleImage2(String articleImage2) {
	this.articleImage2 = articleImage2;
}
public String getArticleImage3() {
	return articleImage3;
}
public void setArticleImage3(String articleImage3) {
	this.articleImage3 = articleImage3;
}  
String articleManufacturer;
public String getArticleManufacturer() {
	return articleManufacturer;
}
public void setArticleManufacturer(String articleManufacturer) {
	this.articleManufacturer = articleManufacturer;
}
public String getArticleModel() {
	return articleModel;
}
public void setArticleModel(String articleModel) {
	this.articleModel = articleModel;
}
public String getArticleType() {
	return articleType;
}
public void setArticleType(String articleType) {
	this.articleType = articleType;
}
public String getBarcode() {
	return barcode;
}

public void setBarcode(String barcode) {
	this.barcode = barcode;
}
  
}
