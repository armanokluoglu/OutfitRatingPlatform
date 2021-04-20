package model.domain;

import java.util.List;
import model.utilities.Brand;
import model.utilities.Color;
import model.utilities.Gender;
import model.utilities.Occasion;
import model.utilities.Size;
import model.utilities.Type;

public class Outfit {

	private int id;
	private Brand brand;
	private Type type;
	private Occasion occasion;
	private Gender gender;
	private List<Size> sizes;
	private Color color;
	private int likes;
	private int dislikes;
	private List<Comment> comments;

	public Outfit(int id, Brand brand, Type type, Occasion occasion, Gender gender, List<Size> sizes, Color color,
			int likes, int dislikes, List<Comment> comments) {
		this.id = id;
		this.brand = brand;
		this.type = type;
		this.occasion = occasion;
		this.gender = gender;
		this.sizes = sizes;
		this.color = color;
		this.likes = likes;
		this.dislikes = dislikes;
		this.comments = comments;
	}

	public void like() {
		int likes = getLikes();
		likes++;
		setLikes(likes);
	}

	public void removeLike() {
		int likes = getLikes();
		likes--;
		setLikes(likes);
	}

	public void dislike() {
		int dislikes = getDislikes();
		dislikes++;
		setDislikes(dislikes);
	}

	public void removeDislike() {
		int dislikes = getDislikes();
		dislikes--;
		setDislikes(dislikes);
	}

	public Comment getCommentFromId(int id) {
		List<Comment> comments = getComments();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				return comment;
			}
		}
		return null;
	}

	public void addComment(Comment comment) {
		List<Comment> comments = getComments();
		comments.add(comment);
		setComments(comments);
	}

	public void removeComment(Comment comment) {
		List<Comment> comments = getComments();
		comments.remove(comment);
		setComments(comments);
	}

	// GETTERS AND SETTERS
	
	public int getId() {
		return id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Occasion getOccasion() {
		return occasion;
	}

	public void setOccasion(Occasion occasion) {
		this.occasion = occasion;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<Size> getSizes() {
		return sizes;
	}

	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
