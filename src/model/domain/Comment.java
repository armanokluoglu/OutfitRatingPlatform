package model.domain;

import model.utilities.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public static Comment parseJson(org.json.simple.JSONObject commentJSON) {
		int id = ((Long) commentJSON.get("Id")).intValue();
		String content = (String) commentJSON.get("Content");


		return new Comment(id,content,null,null);
	}
}
