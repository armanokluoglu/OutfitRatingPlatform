package app;

import model.data_access.InputOutput;
import model.data_access.OutfitRepository;
import model.data_access.UserRepository;
import model.domain.Collection;
import model.domain.Comment;
import model.domain.Outfit;
import model.domain.User;
import model.utilities.*;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
	public static void main(String[] args) {


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

		user1.addFollowing(user2);
		user2.addFollowing(user3);
		user2.addFollowing(user4);
		user3.addFollowing(user1);
		user3.addFollowing(user5);
		user4.addFollowing(user6);
		user5.addFollowing(user6);

		List<Size> sizeList = new ArrayList<>();
		sizeList.add(Size.L);
		sizeList.add(Size.M);


		Outfit outfit = new Outfit(1, Brand.GAP,Type.BIKINIBOTTOM, Occasion.CASUAL, Gender.WOMEN,sizeList,Color.BLACK,135,44,new ArrayList<>());
		Outfit outfit2 = new Outfit(2, Brand.BURBERRY,Type.BRACELET, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.GREEN,55,14,new ArrayList<>());
		Outfit outfit3 = new Outfit(3, Brand.CALVINKLEIN,Type.COAT, Occasion.FORMAL, Gender.WOMEN,sizeList,Color.BLACK,15,40,new ArrayList<>());
		Outfit outfit4 = new Outfit(4, Brand.FENDI,Type.BLOUSE, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.BLACK,5,4,new ArrayList<>());
		Outfit outfit5 = new Outfit(5, Brand.GAP,Type.BRA, Occasion.SPORTY, Gender.WOMEN,sizeList,Color.ORANGE,3,4,new ArrayList<>());
		Outfit outfit6 = new Outfit(6, Brand.ARMANI,Type.BELT, Occasion.ELEGANT, Gender.WOMEN,sizeList,Color.GRAY,50,4,new ArrayList<>());

		Collection collection = new Collection(1,"collection1",Arrays.asList(outfit,outfit2));
		Collection collection2 = new Collection(2,"collection2",Arrays.asList(outfit3,outfit));
		Collection collection3 = new Collection(3,"collection3",Arrays.asList(outfit4,outfit3));
		Collection collection4 = new Collection(4,"collection4",Arrays.asList(outfit,outfit2,outfit3));
		Collection collection5 = new Collection(5,"collection5",Arrays.asList(outfit5,outfit2,outfit6));
		Collection collection6 = new Collection(6,"collection6",Arrays.asList(outfit6,outfit2,outfit,outfit3));

		user1.addCollection(collection);
		user1.addCollection(collection2);
		user1.addCollection(collection3);
		user2.addCollection(collection2);
		user2.addCollection(collection3);
		user3.addCollection(collection4);
		user4.addCollection(collection5);
		user5.addCollection(collection6);
		user6.addCollection(collection);

		List<User> userList = Arrays.asList(user1,user2,user3,user4,user5,user6);
		List<Outfit> outfits = Arrays.asList(outfit, outfit2,outfit3,outfit4,outfit4,outfit5,outfit6);

		OutfitRepository outfitRepository = new OutfitRepository(null);

		InputOutput io = new InputOutput();
		io.outputOutfits(outfits);
		io.xmlOutput(userList);

		io.inputOutfits();

		OutfitRating or = new OutfitRating();
		or.start();
	}
}