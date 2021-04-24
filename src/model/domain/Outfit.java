package model.domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.data_access.UserRepository;
import model.exception.UserAlreadyException;
import model.utilities.Brand;
import model.utilities.Color;
import model.utilities.Gender;
import model.utilities.Observer;
import model.utilities.Occasion;
import model.utilities.Size;
import model.utilities.Subject;
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
	private BufferedImage image;
	private int likes;
	private int dislikes;
	private List<User> likedUsers = new ArrayList<>();
	private List<User> dislikedUsers = new ArrayList<>();;
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
		this.image = null;
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

	public void like(User user) throws UserAlreadyException {
 		if(likedUsers.contains(user))
 			throw new UserAlreadyException("User already Liked");
		likedUsers.add(user);
	}

	public void removeLike(User user) throws UserAlreadyException {
		if(!likedUsers.contains(user))
			throw new UserAlreadyException("User never liked");
		likedUsers.remove(user);
}

	public void dislike(User user) throws UserAlreadyException {
		if(dislikedUsers.contains(user))
			throw new UserAlreadyException("User already disliked");
		dislikedUsers.add(user);
	}

	public void removeDislike(User user) throws UserAlreadyException {
		if(!dislikedUsers.contains(user))
			throw new UserAlreadyException("User never disliked");
		dislikedUsers.remove(user);
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public List<User> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<User> likedUsers) {
		this.likedUsers = likedUsers;
	}

	public List<User> getDislikedUsers() {
		return dislikedUsers;
	}

	public void setDislikedUsers(List<User> dislikedUsers) {
		this.dislikedUsers = dislikedUsers;
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

	// JSON METHODS
	
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

		List<Integer> likedUserIds = new ArrayList<>();
		if(likedUsers!=null){
			for(User user:likedUsers){
				likedUserIds.add(user.getId());
			}
		}
		List<Integer> dislikedUserIds = new ArrayList<>();
		if(dislikedUsers!=null){
			for(User user:dislikedUsers){
				dislikedUserIds.add(user.getId());
			}
		}

		outfitJSON.put("LikedUserIds", likedUserIds);
		outfitJSON.put("DislikedUserIds", dislikedUserIds);

		List<JSONObject> commentsList = new ArrayList<>();
		if(comments!=null){
			for(Comment comment:comments){
				commentsList.add(comment.toJSON());
			}
		}
		outfitJSON.put("Comments", commentsList);
		return outfitJSON;
	}
	
	public static Outfit parseJson(org.json.simple.JSONObject outfitJSON, UserRepository repository) {
		int id = ((Long) outfitJSON.get("Id")).intValue();
		Brand brand = Brand.valueOf((String) outfitJSON.get("Brand"));
		Type type = Type.valueOf((String) outfitJSON.get("Type"));
		Occasion occasion = Occasion.valueOf((String) outfitJSON.get("Occasion"));
		Gender gender = Gender.valueOf((String) outfitJSON.get("Gender"));
		Color color = Color.valueOf((String) outfitJSON.get("Color"));
		int likes = ((Long) outfitJSON.get("Likes")).intValue();
		int dislikes = ((Long) outfitJSON.get("Dislikes")).intValue();

		List<User> likedUsers = new ArrayList<>();
		List<User> dislikedUsers = new ArrayList<>();

		org.json.simple.JSONArray likedUserIds = (org.json.simple.JSONArray) outfitJSON.get("LikedUserIds");
		org.json.simple.JSONArray dislikedUserIds = (org.json.simple.JSONArray) outfitJSON.get("DislikedUserIds");
		likedUserIds.forEach(entry -> likedUsers.add(repository.findUserById(((Long) entry).intValue())));
		dislikedUserIds.forEach(entry -> dislikedUsers.add(repository.findUserById(((Long) entry).intValue())));


		List<Size> sizes = new ArrayList<>();
		org.json.simple.JSONArray sizesJson = (org.json.simple.JSONArray) outfitJSON.get("Sizes");
		sizesJson.forEach(entry -> sizes.add(Size.valueOf((String) entry)));

		List<Comment> comments = new ArrayList<>();
		org.json.simple.JSONArray commentsJSON = (org.json.simple.JSONArray) outfitJSON.get("Comments");
		commentsJSON.forEach(entry -> comments.add(Comment.parseJson((org.json.simple.JSONObject) entry,repository)));

		Outfit outfit = new Outfit(id,brand,type,occasion,gender,sizes,color,likes,dislikes,comments);
		outfit.setLikedUsers(likedUsers);
		outfit.setDislikedUsers(dislikedUsers);
		return outfit;
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
