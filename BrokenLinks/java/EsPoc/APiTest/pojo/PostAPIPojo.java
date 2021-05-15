package EsPoc.APiTest.pojo;

public class PostAPIPojo {
	
	private int id;
	private String Title;
	private String Auther;
	private String CustomerId;
	
	public PostAPIPojo(int id, String title, String auther, String customerId) {
		this.id = id;
		this.Title = title;
		this.Auther = auther;
		this.CustomerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getAuther() {
		return Auther;
	}	
	
	public void setAuther(String auther) {
		this.Auther = auther;
	}
	
	public String getCustomerID() {
		return CustomerId;
	}

	public void setCustomerID(String customerId) {
		this.CustomerId = customerId;
	}
	

	@Override
	public String toString() {
		return "PostAPIPojo [Id=" + this.id + ", Title=" + this.Title + ", Auther=" + this.Auther + "]";
	}
	
	
	
}
