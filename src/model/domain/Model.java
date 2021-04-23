package model.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.data_access.InputOutput;
import model.data_access.OutfitRepository;
import model.data_access.UserRepository;
import model.utilities.Brand;
import model.utilities.Color;
import model.utilities.Gender;
import model.utilities.Observer;
import model.utilities.Occasion;
import model.utilities.Size;
import model.utilities.Subject;
import model.utilities.Type;

public class Model implements Observer, Subject {

	private UserRepository userRepo;
	private OutfitRepository outfitRepo;
	private boolean changed;
	private List<Observer> observers;
	private List<Subject> subjects;
	
	public Model() {
		InputOutput io = new InputOutput();
		this.userRepo = new UserRepository(io);
		this.outfitRepo = new OutfitRepository(io);
		this.changed = false;
		setObservers(new ArrayList<Observer>());
		this.subjects = new ArrayList<Subject>();
		
		User user1 = new User(1,"test","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		User user2 = new User(2,"test2","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		User user3 = new User(3,"test3","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		User user4 = new User(4,"test4","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		User user5 = new User(5,"test5","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		User user6 = new User(6,"test6","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

		user1.addFollower(user2);
		user1.addFollower(user3);
		user1.addFollower(user4);
		user1.addFollower(user5);
		user2.addFollower(user4);
		user2.addFollower(user5);
		user3.addFollower(user1);
		user3.addFollower(user6);
		user5.addFollower(user1);
		user6.addFollower(user1);

		user2.addFollowing(user1);
		user3.addFollowing(user1);
		user4.addFollowing(user1);
		user5.addFollowing(user1);
		user4.addFollowing(user2);
		user5.addFollowing(user2);
		user1.addFollowing(user3);
		user6.addFollowing(user3);
		user1.addFollowing(user5);
		user1.addFollowing(user6);

		List<Size> sizeList = new ArrayList<>();
		sizeList.add(Size.L);
		sizeList.add(Size.M);

		Outfit outfit = new Outfit(1, Brand.GAP,Type.BIKINIBOTTOM, Occasion.CASUAL, Gender.WOMEN,sizeList,Color.BLACK,135,44,new ArrayList<>());
		Outfit outfit2 = new Outfit(2, Brand.BURBERRY,Type.BRACELET, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.GREEN,55,14,new ArrayList<>());
		Outfit outfit3 = new Outfit(3, Brand.CALVINKLEIN,Type.COAT, Occasion.FORMAL, Gender.WOMEN,sizeList,Color.BLACK,15,40,new ArrayList<>());
		Outfit outfit4 = new Outfit(4, Brand.FENDI,Type.BLOUSE, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.BLACK,5,4,new ArrayList<>());
		Outfit outfit5 = new Outfit(5, Brand.GAP,Type.BRA, Occasion.SPORTY, Gender.WOMEN,sizeList,Color.ORANGE,3,4,new ArrayList<>());
		Outfit outfit6 = new Outfit(6, Brand.ARMANI,Type.BELT, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.GRAY,50,4,new ArrayList<>());

		Collection collection = new Collection(1,"collection1",user1,new Date(1618923364000L), Arrays.asList(outfit,outfit2));
		Collection collection2 = new Collection(2,"collection2",user2,new Date(1618836964000L), Arrays.asList(outfit3,outfit));
		Collection collection3 = new Collection(3,"collection3",user3,new Date(1618750564000L), Arrays.asList(outfit4,outfit3));
		Collection collection4 = new Collection(4,"collection4",user4,new Date(1619009764000L), Arrays.asList(outfit,outfit2,outfit3));
		Collection collection5 = new Collection(5,"collection5",user5,new Date(1619096164000L), Arrays.asList(outfit5,outfit2,outfit6));
		Collection collection6 = new Collection(6,"collection6",user6,new Date(1618919764000L), Arrays.asList(outfit6,outfit2,outfit,outfit3));

		user1.addCollection(collection);
		user2.addCollection(collection2);
		user3.addCollection(collection3);
		user4.addCollection(collection4);
		user5.addCollection(collection5);
		user6.addCollection(collection6);

		List<User> userList = Arrays.asList(user1,user2,user3,user4,user5,user6);
		List<Outfit> outfits = Arrays.asList(outfit, outfit2,outfit3,outfit4,outfit4,outfit5,outfit6);
		
		this.outfitRepo.setOutfits(outfits);
		this.userRepo.setUsers(userList);
	}
	
	public User login(String username, String password) throws IllegalArgumentException, IllegalStateException {
		username = "test";
		password = "1234";
		User user = userRepo.findUserByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("Invalid password.");
		}
		return user;
	}
	
	public Collection createCollectionForUser(String name, User user) {
		int id = outfitRepo.createCollectionId();
		Collection collection = new Collection(id, name, user);
		user.addCollection(collection);
		return collection;
	}
	
	public void addOutfitToCollection(int outfitId, Collection collection) throws IllegalStateException {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		collection.addOutfit(outfit);
	}
	
	public void removeOutfitFromCollection(int outfitId, Collection collection) throws IllegalStateException {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		collection.removeOutfit(outfit);
	}
	
	public void likeOutfit(int outfitId) {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		outfit.like();
	}
	
	public void dislikeOutfit(int outfitId) {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		outfit.dislike();
	}
	
	public Comment commentOnOutfitAsUser(int outfitId, String commentContent, User user) {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		int id = outfitRepo.createCommentId();
		Comment comment = new Comment(id, commentContent, user, new Date());
		outfit.addComment(comment);
		return comment;
	}
	
	public void removeCommentOnOutfit(int outfitId, Comment comment) {
		Outfit outfit = outfitRepo.findOutfitById(outfitId);
		outfit.removeComment(comment);
	}
	
	public void followUserAsUser(int userId, User user) {
		User userToFollow = userRepo.findUserById(userId);
		user.addFollowing(userToFollow);
		userToFollow.addFollower(user);
	}
	
	public void unfollowUser(int userId, User user) {
		User userToUnfollow = userRepo.findUserById(userId);
		user.removeFollowing(userToUnfollow);
		userToUnfollow.removeFollower(user);
	}
	
	public List<User> getFollowingsOfUser(int userId) {
		User user = userRepo.findUserById(userId);
		return user.getFollowings();
	}
	
	public List<User> getFollowersOfUser(int userId) {
		User user = userRepo.findUserById(userId);
		return user.getFollowers();
	}
	
	public List<Collection> getCollectionsOfFollowingsOfUserChronologically(User user) {
		List<User> followings = user.getFollowings();
		List<Collection> collections = new ArrayList<>();
		for (User following : followings) {
			collections.addAll(following.getCollections());
		}
		Collections.sort(collections, new Comparator<Collection>() {
	        public int compare(Collection col1, Collection col2) {
	            return col2.getCreationDate().compareTo(col1.getCreationDate());
	        }
	    });
		return collections;
		
	}
	
	public Outfit getMostLikedOutfit() {
		return outfitRepo.getOutfitWithMostLikes();
	}
	
	public Outfit getMostDislikedOutfit() {
		return outfitRepo.getOutfitWithMostDislikes();
	}
	
	public Outfit getMostFollowedUser() {
		return userRepo.getUserWithMostFollowers();
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
	
	// SUBJECT METHODS

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
	
	// OBSERVER METHODS

	@Override
	public void update() {
		setChanged(true);
		notifyObservers();
	}

	@Override
	public void addSubject(Subject sub) {
		this.subjects.add(sub);
	}
	
	@Override
	public void removeSubject(Subject sub) {
		this.subjects.remove(sub);
	}
}
