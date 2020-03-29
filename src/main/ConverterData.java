package main;

import periphery.ConvertOption;
import periphery.Place;
import periphery.SourceGame;
import periphery.TexturePack;

public class ConverterData {

	private Place place;
	private SourceGame game;
	private ConvertOption option;
	private TexturePack texturePack;
	private boolean updateTextures;

	public ConverterData() {

	}

	public boolean getUpdateTextures() {
		return this.updateTextures;
	}

	public void setUpdateTextures(boolean copyTextures) {
		this.updateTextures = copyTextures;
	}

	public Place getPlace() {
		return this.place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public SourceGame getGame() {
		return this.game;
	}

	public void setGame(SourceGame game) {
		this.game = game;
	}

	public ConvertOption getOption() {
		return this.option;
	}

	public void setOption(ConvertOption option) {
		this.option = option;
	}

	public TexturePack getTexturePack() {
		return this.texturePack;
	}

	public void setTexturePack(TexturePack texturePack) {
		this.texturePack = texturePack;
	}
}
