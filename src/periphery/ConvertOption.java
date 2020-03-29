package periphery;

import java.util.Stack;

import vmfWriter.Color;

public class ConvertOption {

	private String name;
	private int scale;
	private String skyTexture;
	private Color sunLight;
	private Color sunAmbient;
	private Color sunShadow;
	private Stack<String> addables;

	public static ConvertOption create() {
		return new ConvertOption();
	}

	public ConvertOption() {
		this.addables = new Stack<String>();
	}

	public ConvertOption(String aName) {
		this.addables = new Stack<String>();
		this.name = aName;
		this.scale = 40;
	}

	public ConvertOption setName(String name) {
		this.name = name;
		return this;
	}

	public ConvertOption setScale(int scale) {
		this.scale = scale;
		return this;
	}

	public ConvertOption setSkyTexture(String skyTexture) {
		this.skyTexture = skyTexture;
		return this;
	}

	public ConvertOption setSunAmbient(Color sunAmbient) {
		this.sunAmbient = sunAmbient;
		return this;
	}

	public ConvertOption setSunLight(Color sunLight) {
		this.sunLight = sunLight;
		return this;
	}

	public ConvertOption setSunShadow(Color sunShadow) {
		this.sunShadow = sunShadow;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public int getScale() {
		return this.scale;
	}

	public String getSkyTexture() {
		return this.skyTexture;
	}

	public Color getSunAmbient() {
		return this.sunAmbient;
	}

	public Color getSunLight() {
		return this.sunLight;
	}

	public Color getSunShadow() {
		return this.sunShadow;
	}

	public ConvertOption addAddable(String add) {
		this.addables.push(add);
		return this;
	}

	public String[] getAddablesAsStrings() {
		Stack<String> addablesNew = new Stack<String>();
		int l = this.addables.size();
		String[] result = new String[l];
		for (int i = 0; i < l; i++) {
			result[i] = this.addables.pop();
			addablesNew.push(result[i]);
		}
		this.addables = addablesNew;
		return result;
	}
}
