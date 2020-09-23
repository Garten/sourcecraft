package periphery;

import java.util.ArrayList;
import java.util.List;

import converter.actions.Action;
import converter.actions.ConvertEntity;
import minecraft.BlockTemplate;
import minecraft.Material;
import vmfWriter.Color;

public class ConvertOption {

	private String name;
	private int scale;
	private String skyTexture;
	private Color sunLight;
	private Color sunAmbient;
	private Color sunShadow;
	private List<ConvertEntity> convertEntities;

	public static ConvertOption create() {
		return new ConvertOption();
	}

	public ConvertOption() {
		this.convertEntities = new ArrayList<ConvertEntity>();
	}

	public ConvertOption(String aName) {
		this.convertEntities = new ArrayList<ConvertEntity>();
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

	public ConvertOption addConvertEntity(Material material, Action action) {
		this.convertEntities.add(new ConvertEntity(new BlockTemplate().setName(material), action));
		return this;
	}

	public ConvertOption addConvertEntity(ConvertEntity addable) {
		this.convertEntities.add(addable);
		return this;
	}

	public List<ConvertEntity> getConvertEntities() {
		return convertEntities;

	}
}
