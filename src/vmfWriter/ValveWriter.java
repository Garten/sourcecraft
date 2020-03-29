package vmfWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ValveWriter {

	private static final String INDENTATION = "\t";
	private static final String OPEN_TAG = "{";
	private static final String CLOSE_TAG = "}";
	private static final String QUOTE = "\"";
	private static final String LINE_BREAK = "\n";

	private Writer writer;
	private Counter counter;
	private int indentation = 0;

	public ValveWriter(File file) throws IOException {
		this(new FileWriter(file), new Counter());
	}

	public ValveWriter(Writer w, Counter counter) {
		this.writer = w;
		this.counter = counter;
	}

	public Counter getCounter() {
		return this.counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	public ValveWriter setIndentation(int indentation) {
		this.indentation = indentation;
		return this;
	}

	public ValveWriter putATry(String title, Runnable body) throws IOException {
		this.open(title);
		body.run();
		this.close();
		return this;
	}

	public ValveWriter open(String title) throws IOException {
		this.writeLine(title);
		this.writeLine(OPEN_TAG);
		this.indentation++;
		return this;
	}

	public ValveWriter close() throws IOException {
		this.indentation--;
		this.writeLine(CLOSE_TAG);
		return this;
	}

	public ValveWriter put(String key, String value) throws IOException {
		this.writeLine(QUOTE + key + QUOTE + " " + QUOTE + value + QUOTE);
		return this;
	}

	public ValveWriter put(String key, int value) throws IOException {
		return this.put(key, value + "");
	}

	public ValveWriter put(String key, boolean value) throws IOException {
		if (value) {
			return this.put(key, "1");
		} else {
			return this.put(key, "0");
		}
	}

	public ValveWriter put(String key, Color color) throws IOException {
		return this.put(key, color.red + " " + color.green + " " + color.blue);
	}

	public ValveWriter putInBrackets(String key, java.awt.Color color) throws IOException {
		if (color != null) {
			this.put(key, "{" + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + "}");
		}
		return this;
	}

	public ValveWriter putTransparentColor(String key, Color color) throws IOException {
		return this.put(key, color.red + " " + color.green + " " + color.blue + " " + color.alpha);
	}

	public ValveWriter put(String key, Angles angles) throws IOException {
		return this.put(key, angles.getX() + " " + angles.getY() + " " + angles.getZ());
	}

	private void writeLine(String title) throws IOException {
		this.writeIndentation()
				.write(title + ValveWriter.LINE_BREAK);
	}

	private ValveWriter writeIndentation() throws IOException {
		for (int i = 0; i < this.indentation; i++) {
			this.writer.write(INDENTATION);
		}
		return this;
	}

	private ValveWriter write(String text) throws IOException {
		this.writer.write(text);
		return this;
	}

	public void finish() throws IOException {
		this.writer.close();
	}

	public void putBrushID() throws IOException {
		this.put(ValveElement.ID_TAG, this.getCounter()
				.getBrushId());
	}

}
