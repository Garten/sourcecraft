package vmfWriter.entity.solidEntity;

import java.io.IOException;

import source.SkinManager;
import vmfWriter.Solid;
import vmfWriter.ValveWriter;

public class Buyzone extends SolidEntity {

	private static final String TEAM_NUM_TAG = "TeamNum";

	private static final int TERRORIST = 2;
	private static final int COUNTER_TERRORIST = 3;

	int teamnumber;

	public Buyzone(Solid s, boolean police) {
		super(s);
		s.setSkin(SkinManager.TRIGGER);
		if (police) {
			this.teamnumber = COUNTER_TERRORIST;
		} else {
			this.teamnumber = TERRORIST;
		}
	}

	@Override
	public String getName() {
		return "func_bomb_target";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put(TEAM_NUM_TAG, this.teamnumber);
	}
}
