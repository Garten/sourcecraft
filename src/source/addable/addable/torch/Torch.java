package source.addable.addable.torch;

import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.Color;
import vmfWriter.entity.pointEntity.pointEntity.InfoParticleSystem;
import vmfWriter.entity.pointEntity.pointEntity.Light;
import vmfWriter.entity.solidEntity.FuncIllusionary;

public class Torch extends Addable {

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

	public Torch() {
		int[] temp = { Material.TORCH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public String getName() {
		return "torch";
	}

	@Override
	public void add(Position p, int material) {
		int parts = 16;
		Position offset = new Position(7, 0, 7);
		Position negativeOffset = new Position(7, 6, 7);
		this.map.addSolidEntity(new FuncIllusionary(this.map.createCuboid(p, p, parts, offset, negativeOffset, material)));
		this.map.setPointToGrid(p);
		this.map.movePointInGridDimension(0.5, ((double) (parts - negativeOffset.getY())) / ((parts)), 0.5);
		this.map.addPointEntity(Torch.PARTICLE_SYSTEM);
		this.map.movePointInGridDimension(0, 1.0 / ((parts)), 0);
		this.map.addPointEntity(Torch.LIGHT);
		this.map.markAsConverted(p);
	}
}
