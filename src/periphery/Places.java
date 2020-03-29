package periphery;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Places {

	private List<Place> places;

	public Places() {
		this.places = new ArrayList<>();
	}

	public Places(List<Place> places) {
		this.places = places;
	}

	public String[] getPlaceNamesWithDefault(String defaultName) {
		Place[] placesArray = new Place[this.places.size()];
		placesArray = this.places.toArray(placesArray);
		String[] result = new String[this.places.size() + 1];
		result[0] = defaultName;
		for (int i = 1; i < this.places.size() + 1; i++) {
			result[i] = placesArray[i - 1].getDisplayName();
		}
		return result;
	}

	public String[] getPlaceNames() {
		Place[] placesArray = new Place[this.places.size()];
		placesArray = this.places.toArray(placesArray);
		String[] result = new String[this.places.size()];
		for (int i = 0; i < this.places.size(); i++) {
			result[i] = placesArray[i].getDisplayName();
		}
		return result;
	}

	public Place getPlace(String searchedName) {
		Object[] placesArray = this.places.toArray();
		for (Object object : placesArray) {
			Place posPlace = (Place) object;
			if (posPlace.getDisplayName()
					.equals(searchedName)) {
				return posPlace;
			}
		}
		return null;
	}

	public void addPlace(Place place) {
		this.places.add(place);
	}

	public void deletePlace(Place place) {
		this.places.remove(place);
	}

	public boolean placesEmpty() {
		return this.places.isEmpty();
	}

	public Vector<Place> getPlaces() {
		return new Vector<>(this.places);
	}
}
