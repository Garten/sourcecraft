package vmfWriter;

import converter.Orientation;

/**
 *
 *
 */
public class Skin {

	public String materialFront;
	public String materialLeft;
	public String materialRight;
	public String materialTop;
	public String materialBottom;
	public String materialBack;
	public double scale;

	public Skin() {
		this.materialFront = null;
		this.materialLeft = null;
		this.materialRight = null;
		this.materialTop = null;
		this.materialBottom = null;
		this.materialBack = null;
		this.scale = 64 / 256;
	}

	public Skin(String newMaterial, double newScale) {
		this.materialFront = newMaterial;
		this.materialLeft = newMaterial;
		this.materialRight = newMaterial;
		this.materialTop = newMaterial;
		this.materialBottom = newMaterial;
		this.materialBack = newMaterial;
		this.scale = newScale;
	}

	public Skin(String material, String materialTopBottom, double newScale) {
		this.materialFront = material;
		this.materialLeft = material;
		this.materialRight = material;
		this.materialBack = material;

		this.materialTop = materialTopBottom;
		this.materialBottom = materialTopBottom;

		this.scale = newScale;
	}

	public Skin(String newMaterial, String newMaterialTop, String newMaterialFront, double newScale) {
		this.materialLeft = newMaterial;
		this.materialRight = newMaterial;
		this.materialBack = newMaterial;

		this.materialTop = newMaterialTop;
		this.materialBottom = newMaterialTop;

		this.materialFront = newMaterialFront;

		this.scale = newScale;
	}

	public Skin(String newMaterial, String newMaterialTop, String newMaterialFront, Orientation orientation,
			double newScale) {
		this.materialLeft = newMaterial;
		this.materialRight = newMaterial;
		this.materialBack = newMaterial;
		this.materialFront = newMaterial;

		this.materialTop = newMaterialTop;
		this.materialBottom = newMaterialTop;

		switch (orientation) {
		case SOUTH:
			this.materialFront = newMaterialFront;
			break;
		case EAST:
			this.materialRight = newMaterialFront;
			break;
		case NORTH:
			this.materialBack = newMaterialFront;
			break;
		case WEST:
			this.materialLeft = newMaterialFront;
			break;
		}
		this.scale = newScale;
	}

	public Skin(String newMaterial, String newMaterialTop, String newMaterialFront, String newMaterialBottom,
			double newScale) {
		this.materialLeft = newMaterial;
		this.materialRight = newMaterial;
		this.materialBack = newMaterial;

		this.materialTop = newMaterialTop;
		this.materialBottom = newMaterialBottom;
		this.materialFront = newMaterialFront;

		this.scale = newScale;
	}

	public Skin(String newMaterial, String newMaterialTop, String newMaterialFront, String newMaterialBottom,
			Orientation orientation, double newScale) {
		this.materialLeft = newMaterial;
		this.materialRight = newMaterial;
		this.materialBack = newMaterial;
		this.materialFront = newMaterial;

		this.materialTop = newMaterialTop;
		this.materialBottom = newMaterialBottom;

		switch (orientation) {
		case SOUTH:
			this.materialFront = newMaterialFront;
			break;
		case EAST:
			this.materialRight = newMaterialFront;
			break;
		case NORTH:
			this.materialBack = newMaterialFront;
			break;
		case WEST:
			this.materialLeft = newMaterialFront;
			break;
		}
		this.scale = newScale;
	}
}
