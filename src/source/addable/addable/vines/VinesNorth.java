/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source.addable.addable.vines;

import minecraft.Position;
import source.addable.Addable;
import vmfWriter.entity.solidEntity.FuncIllusionary;

/**
 *
 *
 */
public class VinesNorth extends Addable {

	public VinesNorth() {
		int[] temp = { 350 };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public String getName() {
		return "vinesNorth";
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestY(p, material);
		int parts = 8;
		Position offset = new Position(0, 0, 0);
		Position negativeOffset = new Position(0, 0, 7);
		this.map.addSolidEntity(new FuncIllusionary(this.map.createCuboid(p, end, parts, offset, negativeOffset, material)));
		this.map.markAsConverted(p, end);
	}
}
