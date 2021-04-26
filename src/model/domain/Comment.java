package model.domain;

import model.data_access.UserRepository;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

	private int id;
	private String content;
	private User author;
	private Date date;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	public Comment(int id, String content, User author, Date date) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	// JSON METHODS

	public JSONObject toJSON(){
		JSONObject commentJson = new JSONObject();
		commentJson.put("Id", getId());
		commentJson.put("Content", getContent());
		commentJson.put("AuthorId", author.getId());
		commentJson.put("Date", dateFormat.format(getDate()));
		return commentJson;
	}
	
	public static Comment parseJson(org.json.simple.JSONObject commentJSON, UserRepository repository) throws ParseException {
		int id = ((Long) commentJSON.get("Id")).intValue();
		String content = (String) commentJSON.get("Content");
		int userId = ((Long) commentJSON.get("AuthorId")).intValue();
		String dateStr = (String) commentJSON.get("Date");
		Date date=dateFormat.parse(dateStr);

		User user = repository.findUserById(userId);

		return new Comment(id,content,user,date);
	}
}
