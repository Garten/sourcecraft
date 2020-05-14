package vmfWriter;

/**
 * Contains 2 counter.
 *
 */
public class Counter {

	private int brushId;
	private int sideId;

	public Counter() {
		this.brushId = 1; // first is 2
		this.sideId = 0; // fist is 1
	}

	public int getNewBrushId() {
		this.brushId++;
		return this.brushId;
	}

	public int getNewSideId() {
		this.sideId++;
		return this.sideId;
	}
}
