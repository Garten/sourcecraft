package source.addable.addable.ttt;

import java.util.LinkedList;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import source.addable.addable.SimplePointEntity;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class TttEntites extends Addable {

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<>();
		list.add(new SimplePointEntity(Material.FLETCHING_TABLE)
				.setEntity(new RotateablePointEntity().setName("ttt_random_weapon")));
		list.add(new SimplePointEntity(Material.GRINDSTONE)
				.setEntity(new RotateablePointEntity().setName("ttt_random_ammo")));
		list.add(new SimplePointEntity(Material.ZOMBIE_HEAD)
				.setEntity(new RotateablePointEntity().setName("info_player_start")));
		return list;
	}

	@Override
	public void add(Position position, int material) {
		// not used
	}
}
