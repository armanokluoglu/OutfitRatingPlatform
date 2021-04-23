package model.data_access;

import model.domain.Outfit;
import model.domain.User;

import java.util.List;

public class OutfitRepository {
	
	private InputOutput io;
	private List<Outfit> outfitList;
	
	public OutfitRepository(InputOutput io) {
		this.io = io;
	}

	public List<Outfit> findAll(){
		return outfitList;
	}
	
	public void setOutfits(List<Outfit> outfits) {
		this.outfitList = outfits;
	}

	public int createCollectionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Outfit findOutfitById(int outfitId) {
		// TODO Auto-generated method stub
		return null;
	}

	public int createCommentId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Outfit getOutfitWithMostLikes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Outfit getOutfitWithMostDislikes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}