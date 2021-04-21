package model.domain;

import org.json.JSONObject;
import model.utilities.Observer;
import model.utilities.Subject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.List;


public class Collection implements Subject {

	private int id;
	private String name;
	private List<Outfit> outfits;
	private boolean changed;
	private List<Observer> observers;

	public Collection(int id, String name) {
		this.id = id;
		this.name = name;
		this.outfits = new ArrayList<Outfit>();;
		this.changed = false;
		this.observers = new ArrayList<Observer>();
	}

	public void addOutfit(Outfit outfit) throws IllegalStateException {
		List<Outfit> outfits = getOutfits();
		if (outfits.contains(outfit)) {
			throw new IllegalStateException("Collection already contains outfit.");
		}
		outfits.add(outfit);
		setOutfits(outfits);
	}

	public void removeOutfit(Outfit outfit) throws IllegalStateException {
		List<Outfit> outfits = getOutfits();
		if (!outfits.contains(outfit)) {
			throw new IllegalStateException("Collection does not contain outfit.");
		}
		outfits.remove(outfit);
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
		setChanged(true);
		notifyObservers();
		this.name = name;
	}

	public List<Outfit> getOutfits() {
		return outfits;
	}

	public void setOutfits(List<Outfit> outfits) {
		setChanged(true);
		notifyObservers();
		this.outfits = outfits;
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
		JSONObject collectionJSON = new JSONObject();
		collectionJSON.put("Id", getId());
		collectionJSON.put("Name", getName());
		List<JSONObject> outfitsJSON = new ArrayList<>();
		for(Outfit outfit:outfits){
			outfitsJSON.add(outfit.toJSON());
		}
		collectionJSON.put("Outfits", outfitsJSON);

		return collectionJSON;
	}
	
	// XML METHODS
	
	public Node toXMLNode(Document doc) {
		Element collection = doc.createElement("Collection");
		//set id attribute
		collection.setAttribute("id", String.valueOf(getId()));
		//create name element
		collection.appendChild(getOutfitElements(doc, collection, "Name", getName()));

		for(Outfit outfit:outfits){
			collection.appendChild(getOutfitElements(doc,collection,"OutfitId",String.valueOf(outfit.getId())));
		}

		return collection;
	}
	
	private Node getOutfitElements(Document doc, Element element, String name, String value) {
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
