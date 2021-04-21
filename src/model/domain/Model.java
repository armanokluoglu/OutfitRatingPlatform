package model.domain;

import java.util.Date;
import java.util.List;
import model.data_access.InputOutput;
import model.data_access.OutfitRepository;
import model.data_access.UserRepository;

public class Model {

	private UserRepository userRepo;
	private OutfitRepository outfitRepo;
	
	public Model() {
		InputOutput io = new InputOutput();
		this.userRepo = new UserRepository(io);
		this.outfitRepo = new OutfitRepository(io);
	}
	
	public User login(String username, String password) {
		User user = userRepo.findUserByUsername(username);
		if (user.getPassword() != password) {
			throw new IllegalArgumentException("Invalid password.");
		}
		return user;
	}
	
	public Collection createCollectionForUser(String name, User user) {
		int id = outfitRepo.createCollectionId();
		Collection collection = new Collection(id, name);
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
		userToFollow.addFollower(getCurrentUser());
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
	
	public Outfit getMostLikedOutfit() {
		return outfitRepo.getOutfitWithMostLikes();
	}
	
	public Outfit getMostDislikedOutfit() {
		return outfitRepo.getOutfitWithMostDislikes();
	}
	
	public Outfit getMostFollowedUser() {
		return userRepo.getUserWithMostFollowers();
	}
}
