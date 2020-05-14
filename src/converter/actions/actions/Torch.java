package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;
import vmfWriter.Color;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.pointEntity.pointEntity.EnvFire;
import vmfWriter.entity.pointEntity.pointEntity.InfoParticleSystem;
import vmfWriter.entity.pointEntity.pointEntity.Light;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class Torch extends Action {

	public final static int red = 255;
	public final static int blue = 50;
	public final static int green = 243;
	public final static int brigthness = 40;
	public final static int distance50 = 96;
	public final static int distance100 = 256;
	public final static Color LIGHT_COLOR = new Color(Torch.red, Torch.green, Torch.blue, Torch.brigthness);
	public final static Light LIGHT = new Light(Torch.LIGHT_COLOR, Torch.distance50, Torch.distance100);
	private final static String EFFECT_NAME = "flaming_arrow";
	public final static InfoParticleSystem PARTICLE_SYSTEM = new InfoParticleSystem(Torch.EFFECT_NAME, 270, 0, 0);
	protected static final PointEntity FLAME = new EnvFire().setFireSize(3);

	public Torch() {
		int[] temp = { MaterialLegacy.TORCH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		int parts = 16;
		Position offset = new Position(7, 0, 7);
		Position negativeOffset = new Position(7, 6, 7);
		context.addSolidEntity(
				new FuncIllusionary(context.createCuboid(p, p, parts, offset, negativeOffset, material)));
		context.setPointToGrid(p);
		context.movePointInGridDimension(0.5, ((double) (parts - negativeOffset.getY())) / ((parts)), 0.5);
		this.addFlame(context);
		context.markAsConverted(p);
	}

	public void addFlame(Mapper context) {
		context.addPointEntity(Torch.PARTICLE_SYSTEM);
		context.movePointInGridDimension(0, 1.0 / ((16)), 0);
		context.addPointEntity(Torch.FLAME);
		context.addPointEntity(Torch.LIGHT);
	}
}
