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
		Outfit mostLikedOutfit = null;
		for (Outfit outfit : outfitList) {
			if (mostLikedOutfit == null) {
				mostLikedOutfit = outfit;
			} else {
				if (mostLikedOutfit != null && outfit.getLikes() > mostLikedOutfit.getLikes()) {
					mostLikedOutfit = outfit;
				}
			}
		}
		return mostLikedOutfit;
	}

	public Outfit getOutfitWithMostDislikes() {
		Outfit mostDislikedOutfit = null;
		for (Outfit outfit : outfitList) {
			if (mostDislikedOutfit == null) {
				mostDislikedOutfit = outfit;
			} else {
				if (mostDislikedOutfit != null && outfit.getDislikes() > mostDislikedOutfit.getDislikes()) {
					mostDislikedOutfit = outfit;
				}
			}
		}
		return mostDislikedOutfit;
	}
	
}