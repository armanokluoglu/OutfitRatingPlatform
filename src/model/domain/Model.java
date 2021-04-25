package model.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import model.data_access.InputOutput;
import model.data_access.OutfitRepository;
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

public class Model implements Observer, Subject {

	private UserRepository userRepo;
	private OutfitRepository outfitRepo;
	private boolean changed;
	private List<Observer> observers;
	private List<Subject> subjects;
	
	public Model() throws UserAlreadyException {
		InputOutput io = new InputOutput();
		this.userRepo = new UserRepository(io);
		this.outfitRepo = new OutfitRepository(io);
		this.changed = false;
		setObservers(new ArrayList<Observer>());
		this.subjects = new ArrayList<Subject>();
		
//		User user1 = new User(1,"test","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
//		User user2 = new User(2,"test2","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
//		User user3 = new User(3,"test3","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
//		User user4 = new User(4,"test4","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
//		User user5 = new User(5,"test5","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
//		User user6 = new User(6,"test6","1234",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
//
//		user1.addFollower(user2);
//		user1.addFollower(user3);
//		user1.addFollower(user4);
//		user1.addFollower(user5);
//		user2.addFollower(user4);
//		user2.addFollower(user5);
//		user3.addFollower(user1);
//		user3.addFollower(user6);
//		user5.addFollower(user1);
//		user6.addFollower(user1);
//
//		user2.addFollowing(user1);
//		user3.addFollowing(user1);
//		user4.addFollowing(user1);
//		user5.addFollowing(user1);
//		user4.addFollowing(user2);
//		user5.addFollowing(user2);
//		user1.addFollowing(user3);
//		user6.addFollowing(user3);
//		user1.addFollowing(user5);
//		user1.addFollowing(user6);
//
//		List<Size> sizeList = new ArrayList<>();
//		sizeList.add(Size.L);
//		sizeList.add(Size.M);
//		
//		Comment com1 = new Comment(1, "Denemeeeee", user1, new Date());
//		Comment com2 = new Comment(2, "YORUUm", user5, new Date());
//		Comment com3 = new Comment(3, "yorum deneme", user3, new Date());
//		Comment com4 = new Comment(4, "testtesttest", user4, new Date());
//
//		Outfit outfit = new Outfit(1, Brand.GAP,Type.BIKINIBOTTOM, Occasion.CASUAL, Gender.WOMEN,sizeList,Color.BLACK,Arrays.asList(com1, com2, com3, com4));
//		Outfit outfit2 = new Outfit(2, Brand.BURBERRY,Type.BRACELET, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.GREEN,new ArrayList<>());
//		Outfit outfit3 = new Outfit(3, Brand.CALVINKLEIN,Type.COAT, Occasion.FORMAL, Gender.WOMEN,sizeList,Color.BLACK,new ArrayList<>());
//		Outfit outfit4 = new Outfit(4, Brand.FENDI,Type.BLOUSE, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.BLACK,new ArrayList<>());
//		Outfit outfit5 = new Outfit(5, Brand.GAP,Type.BRA, Occasion.SPORTY, Gender.WOMEN,sizeList,Color.ORANGE,new ArrayList<>());
//		Outfit outfit6 = new Outfit(6, Brand.ARMANI,Type.BELT, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.GRAY,new ArrayList<>());
//		try {
//			outfit4.setImage(ImageIO.read(new File("assets/fendi_black_blouse.jpg")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Collection collection = new Collection(1,"collection1",user1,new Date(1618923364000L), Arrays.asList(outfit,outfit2));
//		Collection collection2 = new Collection(2,"collection2",user2,new Date(1618836964000L), Arrays.asList(outfit3,outfit));
//		Collection collection3 = new Collection(3,"collection3",user3,new Date(1618750564000L), Arrays.asList(outfit4,outfit3));
//		Collection collection4 = new Collection(4,"collection4",user4,new Date(1619009764000L), Arrays.asList(outfit,outfit2,outfit3));
//		Collection collection5 = new Collection(5,"collection5",user5,new Date(1619096164000L), Arrays.asList(outfit5,outfit2,outfit6));
//		Collection collection6 = new Collection(6,"collection6",user6,new Date(1618919764000L), Arrays.asList(outfit6,outfit2,outfit,outfit3));
//
//		user1.addCollection(collection);
//		user2.addCollection(collection2);
//		user3.addCollection(collection3);
//		user4.addCollection(collection4);
//		user5.addCollection(collection5);
//		user6.addCollection(collection6);
//
//		outfit.like(user1);outfit2.like(user2);outfit2.like(user3);outfit2.like(user4);outfit3.like(user5);
//		outfit3.like(user6);outfit4.like(user1);outfit4.like(user2);outfit5.like(user3);outfit6.like(user4);
//
//
//		outfit.dislike(user2);outfit.dislike(user3);
//		outfit2.dislike(user1);outfit4.dislike(user6);
//
//
//		List<User> userList = Arrays.asList(user1,user2,user3,user4,user5,user6);
//		List<Outfit> outfits = Arrays.asList(outfit,outfit2,outfit3,outfit4,outfit5,outfit6);
//
//		io.outputOutfits(outfits);

		io.inputUsers();
		io.inputOutfits();

		this.outfitRepo.setOutfits(io.getOutfits());
		this.userRepo.setUsers(io.getUsers());
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
	
	public void likeOutfitAsUser(Outfit outfit, User user) {
		outfit.like(user);
	}
	
	public void dislikeOutfitAsUser(Outfit outfit, User user) {
		outfit.dislike(user);
	}
	
	public Comment commentOnOutfitAsUser(Outfit outfit, String commentContent, User user) {
		int id = outfitRepo.createCommentId();
		Comment comment = new Comment(id, commentContent, user, new Date());
		outfit.addComment(comment);
		return comment;
	}
	
	public void removeCommentOnOutfit(Outfit outfit, Comment comment) {
		outfit.removeComment(comment);
	}
	
	public void followUserAsUser(int userId, User user) {
		User userToFollow = userRepo.findUserById(userId);
		user.addFollowing(userToFollow);
		userToFollow.addFollower(user);
	}
	
	public void unfollowUserAsUser(int userId, User user) {
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
	
	public List<Outfit> getAllOutfits() {
		return outfitRepo.findAll();
	}
	
	public Outfit getMostLikedOutfit() {
		return outfitRepo.getOutfitWithMostLikes();
	}
	
	public Outfit getMostDislikedOutfit() {
		return outfitRepo.getOutfitWithMostDislikes();
	}
	
	public User getMostFollowedUser() {
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
