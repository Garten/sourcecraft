package vmfWriter.entity.solidEntity;

import java.io.IOException;

import source.SkinManager;
import vmfWriter.Cuboid;
import vmfWriter.ValveWriter;

public class BombTarget extends SolidEntity {

	private static final String HEISTBOMB = "heistbomb";

	public BombTarget(String name, Cuboid s) {
		super(s);
		s.setSkin(SkinManager.TRIGGER);
	}

	@Override
	public String getName() {
		return "func_bomb_target";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put(BombTarget.HEISTBOMB, 0);
	}
}
