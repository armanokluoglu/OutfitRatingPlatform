package model.domain;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import model.utilities.Observer;
import model.utilities.Subject;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class User implements Subject {

	private int id;
	private String username;
	private String password;
	private List<User> followers;
	private List<User> followings;
	private List<Collection> collections;
	private boolean changed;
	private List<Observer> observers;

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.followers = new ArrayList<>();
		this.followings = new ArrayList<>();
		this.collections = new ArrayList<>();
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}
	
	public User(int id, String username, String password, List<User> followers, List<User> followings,
			List<Collection> collections) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.followers = followers;
		this.followings = followings;
		this.collections = collections;
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}

	public void addFollower(User user) {
		List<User> followers = getFollowers();
		followers.add(user);
		setFollowers(followers);
	}

	public void removeFollower(User user) {
		List<User> followers = getFollowers();
		followers.remove(user);
		setFollowers(followers);
	}

	public User findFollowerById(int id) {
		List<User> followers = getFollowers();
		for (User user : followers) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public void addFollowing(User user) {
		List<User> followings = getFollowings();
		followings.add(user);
		setFollowings(followings);
	}

	public void removeFollowing(User user) {
		List<User> followings = getFollowings();
		followings.remove(user);
		setFollowings(followings);
	}

	public User findFollowingById(int id) {
		List<User> followings = getFollowings();
		for (User user : followings) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public void addCollection(Collection collection) {
		List<Collection> collections = getCollections();
		collections.add(collection);
		setCollections(collections);
	}

	public void removeCollection(Collection collection) {
		List<Collection> collections = getCollections();
		collections.remove(collection);
		setCollections(collections);
	}

	public Collection findCollectionById(int id) {
		List<Collection> collections = getCollections();
		for (Collection collection : collections) {
			if (collection.getId() == id) {
				return collection;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		return getId() == ((User) o).getId();
	}

	// GETTERS AND SETTERS

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		setChanged(true);
		notifyObservers();
		this.followers = followers;
	}

	public List<User> getFollowings() {
		return followings;
	}

	public void setFollowings(List<User> followings) {
		setChanged(true);
		notifyObservers();
		this.followings = followings;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		setChanged(true);
		notifyObservers();
		this.collections = collections;
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
		JSONObject userJSON = new JSONObject();
		userJSON.put("Id", getId());
		userJSON.put("Username", getUsername());
		userJSON.put("Password", getPassword());
		List<JSONObject> followersJSON = new ArrayList<>();
		List<JSONObject> followingsJSON = new ArrayList<>();
		for(User user:followers){
			followersJSON.add(user.toSimpleJSON());
		}
		for(User user:followings){
			followingsJSON.add(user.toSimpleJSON());
		}
		userJSON.put("Followers", followersJSON);
		userJSON.put("Followings", followingsJSON);
		List<JSONObject> collectionsList = new ArrayList<>();
		for(Collection collection:collections){
			collectionsList.add(collection.toJSON());
		}
		userJSON.put("Collections", collectionsList);

		return userJSON;
	}
	
	public JSONObject toSimpleJSON(){
		JSONObject simpleJSON = new JSONObject();
		simpleJSON.put("Id", getId());
		simpleJSON.put("Username", getUsername());
		return simpleJSON;
	}
	
	// XML METHODS
	
	public Node toXMLNode(Document doc) {
		Element user = doc.createElement("User");
		//set id attribute
		user.setAttribute("id", String.valueOf(getId()));
		//create name element
		user.appendChild(getUserElements(doc, user, "UserName", getUsername()));
		//create age element
		user.appendChild(getUserElements(doc, user, "Password", getPassword()));

		Element node = doc.createElement("Followers");
		if(!followers.isEmpty()){
			for(User follower:followers){
				node.appendChild(follower.toSimpleXMLNode(doc));
			}
		}
		Element node2 = doc.createElement("Followings");
		if(!followings.isEmpty()){
			for(User following:followings){
				node2.appendChild(following.toSimpleXMLNode(doc));
			}
		}
		Element node3 = doc.createElement("Collections");
		if(!collections.isEmpty()){
			for(Collection collection:collections){
				node3.appendChild(collection.toXMLNode(doc));
			}
		}
		user.appendChild(node);
		user.appendChild(node2);
		user.appendChild(node3);
		return user;
	}
	
	private Node toSimpleXMLNode(Document doc) {
		Element user = doc.createElement("User");

		//set id attribute
		user.setAttribute("id", String.valueOf(getId()));
		//create name element
		user.appendChild(getUserElements(doc, user, "UserName", getUsername()));
		//create age element
		user.appendChild(getUserElements(doc, user, "Password", getPassword()));

		return user;
	}
	
	private Node getUserElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
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
