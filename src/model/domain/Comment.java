package model.domain;

import org.json.JSONObject;

import java.util.Date;

public class Comment {

	private int id;
	private String content;
	private User author;
	private Date date;

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

	public JSONObject toJSON(){
		JSONObject commentJson = new JSONObject();
		commentJson.put("Id", getId());
		commentJson.put("Content", getContent());
		commentJson.put("Author", author.toSimpleJSON());
		commentJson.put("Date", getDate().toString());
		return commentJson;
	}
}
