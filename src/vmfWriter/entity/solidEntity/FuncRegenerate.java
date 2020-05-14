package vmfWriter.entity.solidEntity;

import java.io.IOException;

import converter.SkinManager;
import vmfWriter.Solid;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;

public class FuncRegenerate extends SolidEntity {

	private int tf2Team;
	private String target;

	public FuncRegenerate(Solid solid, int tf2Team, PointEntity target) {
		super(solid);
		solid.setSkin(SkinManager.TRIGGER);
		this.tf2Team = tf2Team;
		this.target = target.getName();
	}

	@Override
	public String getName() {
		return "func_regenerate";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("associatedmodel", this.target);
		writer.put("StartDisabled", 0);
		writer.put("TeamNum", this.tf2Team);
	}
}
