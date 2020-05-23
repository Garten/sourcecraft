package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;

public class EnvFire extends PointEntity {

	private int fireSize = 3;

	public EnvFire() {
		super();
	}

	@Override
	public EnvFire create(Position origin) {
		EnvFire result = new EnvFire();
		result.setOrigin(origin);
		return result;
	}

	private static final int FIREATTACK = 4;
	private static final int FIRETYPE = 0;

	private static final String FIREATTACK_TAG = "fireattack";
	private static final String FIRESIZE_TAG = "firesize";
	private static final String FIRETYPE_TAG = "firetype";
	private static final String HEALTH_TAG = "health";
	private static final String IGNITIONPOINT_TAG = "ignitionpoint";
	private static final String SPAWNFLAGS_TAG = "spawnflags";
	private static final String STARTDISABLED_TAG = "StartDisabled";

	@Override
	public String getName() {
		return "env_fire";
	}

	public EnvFire setFireSize(int fireSize) {
		this.fireSize = fireSize;
		return this;
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put(FIREATTACK_TAG, FIREATTACK)
				.put("damagescale", "1.0")
				.put(FIRESIZE_TAG, this.fireSize) //
				.put(FIRETYPE_TAG, FIRETYPE)
				.put(HEALTH_TAG, 60)
				.put(IGNITIONPOINT_TAG, 32)
				.put(SPAWNFLAGS_TAG, 21) // 16// 25
				.put(STARTDISABLED_TAG, 0);
	}

}
