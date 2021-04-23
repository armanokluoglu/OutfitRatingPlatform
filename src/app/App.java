package app;

import model.data_access.InputOutput;
import model.data_access.OutfitRepository;
import model.data_access.UserRepository;
import model.domain.*;
import model.utilities.*;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
	public static void main(String[] args) {




		OutfitRepository outfitRepository = new OutfitRepository(null);

		InputOutput io = new InputOutput();
//		io.outputOutfits(outfits);
//		io.xmlOutput(userList);
//
		io.inputUsers();
		io.inputOutfits();
		Model model = new Model();
		OutfitRating or = new OutfitRating();
		or.start();
	}
}