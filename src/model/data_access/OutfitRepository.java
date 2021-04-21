package model.data_access;

import model.domain.Outfit;

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
	
}
