package model.domain;

import java.util.ArrayList;
import java.util.List;
import model.utilities.Brand;
import model.utilities.Color;
import model.utilities.Gender;
import model.utilities.Occasion;
import model.utilities.Size;
import model.utilities.Type;
import org.json.JSONObject;

public class Outfit implements Subject {

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
	private boolean changed;
	private List<Observer> observers;

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
		this.changed = false;
		this.observers = new ArrayList<Observer>();
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
		setChanged(true);
		notifyObservers();
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		setChanged(true);
		notifyObservers();
		this.dislikes = dislikes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		setChanged(true);
		notifyObservers();
		this.comments = comments;
	}
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public JSONObject toJSON(){
		JSONObject outfitJSON = new JSONObject();
		outfitJSON.put("Id", getId());
		outfitJSON.put("Brand", getBrand());
		outfitJSON.put("Type", getType());
		outfitJSON.put("Occasion", getOccasion());
		outfitJSON.put("Gender", getGender());
		outfitJSON.put("Sizes", getSizes());
		outfitJSON.put("Color", getColor());
		outfitJSON.put("Likes", getLikes());
		outfitJSON.put("Dislikes", getDislikes());

		List<JSONObject> commentsList = new ArrayList<>();
		for(Comment comment:comments){
			commentsList.add(comment.toJSON());
		}
		outfitJSON.put("Domments", commentsList);
		return outfitJSON;
	}

	// OBSERVATION METHODS
	
	@Override
	public void register(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.add(obj);
			setObservers(observers);
		}
	}

	@Override
	public void unregister(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.remove(obj);
			setObservers(observers);
		}
	}

	@Override
	public void notifyObservers() {
		if (!isChanged()) {
			return;
		}
		setChanged(false);
		List<Observer> observers = getObservers();
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
