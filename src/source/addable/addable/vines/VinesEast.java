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
public class VinesEast extends Addable {

	public VinesEast() {
		int[] temp = { 354 };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position p, int material) {
		Position end = this.cuboidFinder.getBestY(p, material);
		int parts = 8;
		Position offset = new Position(7, 0, 0);
		Position negativeOffset = new Position(0, 0, 0);
		this.map.addSolidEntity(new FuncIllusionary(this.map.createCuboid(p, end, parts, offset, negativeOffset, material)));
		this.map.markAsConverted(p, end);
	}
}
