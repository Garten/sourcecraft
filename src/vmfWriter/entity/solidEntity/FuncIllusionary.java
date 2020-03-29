package vmfWriter.entity.solidEntity;

import java.io.IOException;

import vmfWriter.Solid;
import vmfWriter.ValveWriter;

public class FuncIllusionary extends SolidEntity {

	public FuncIllusionary(Solid solid) {
		super(solid);
	}

	@Override
	public String getName() {
		return "func_illusionary";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("disablereceiveshadows", 0)
				.put("disableshadows", 0)
				.put("origin", "0 0 0")
				.put("renderamt", 255)
				.put("rendercolor", "255 255 255")
				.put("renderfx", 0)
				.put("rendermode", 0);
	}
}