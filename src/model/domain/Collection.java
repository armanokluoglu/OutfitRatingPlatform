package model.domain;

import java.util.List;

public class Collection {

	private int id;
	private String name;
	private List<Outfit> outfits;

	public Collection(int id, String name, List<Outfit> outfits) {
		this.id = id;
		this.name = name;
		this.outfits = outfits;
	}

	public void addFollower(Outfit user) {
		List<Outfit> outfits = getOutfits();
		outfits.add(user);
		setOutfits(outfits);
	}

	public void removeFollower(Outfit user) {
		List<Outfit> outfits = getOutfits();
		outfits.remove(user);
		setOutfits(outfits);
	}

	public Outfit findOutfitById(int id) {
		List<Outfit> outfits = getOutfits();
		for (Outfit outfit : outfits) {
			if (outfit.getId() == id) {
				return outfit;
			}
		}
		return null;
	}
	
	// GETTERS AND SETTERS
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Outfit> getOutfits() {
		return outfits;
	}

	public void setOutfits(List<Outfit> outfits) {
		this.outfits = outfits;
	}

}
