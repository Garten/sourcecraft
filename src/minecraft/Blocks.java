package minecraft;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import basic.IOConsumer;
import periphery.Minecraft;

/**
 * Class that lets you create immutable {@code Block} instances without
 * duplicates.
 */
public class Blocks {
	public static final Blocks I = new Blocks();
	private static final BlockTemplate TEMPLATE = new BlockTemplate();
	public static final Map<Block, Block> KNOWN = new HashMap<Block, Block>();

	public static final Block _UNSET = Blocks.get(t -> t.setName("_unset"));

	public static Block _RAMP_SOUTH_EAST = Blocks.get(t -> t.setName("sourcecraft:ramp_south_east"));
	public static Block _RAMP_NORTH_EAST = Blocks.get(t -> t.setName("sourcecraft:ramp_nort_east"));
	public static Block _RAMP_SOUTH_WEST = Blocks.get(t -> t.setName("sourcecraft:ramp_south_west"));
	public static Block _RAMP_NORTH_WEST = Blocks.get(t -> t.setName("sourcecraft:ramp_north_west"));

	public static Block _RAMP_NORTH = Blocks.get(t -> t.setName("sourcecraft:ramp_north"));
	public static Block _RAMP_EAST = Blocks.get(t -> t.setName("sourcecraft:ramp_east"));
	public static Block _RAMP_SOUTH = Blocks.get(t -> t.setName("sourcecraft:ramp_south"));
	public static Block _RAMP_WEST = Blocks.get(t -> t.setName("sourcecraft:ramp_west"));

	private static Block get() {
		Block hashed = KNOWN.get(TEMPLATE);
		if (hashed != null) {
			TEMPLATE.reset();
			return hashed;
		}
		Block lookup = I.new ImmutableBlock(TEMPLATE);
		KNOWN.put(lookup, lookup);
		return lookup;
	}

	public static Block get(Consumer<BlockTemplate> setter) {
		TEMPLATE.reset();
		setter.accept(TEMPLATE);
		return get();
	}

	public static Block get(Supplier<Consumer<BlockTemplate>> supplier) {
		return get(supplier.get());
	}

	public static Block get(String title) {
		return get(t -> t.setName(title));
	}

	public static Block getMC(String title) {
		return get(t -> t.setName(Minecraft.toMaterial(title)));
	}

	public Block getIO(IOConsumer<BlockTemplate> setter) throws IOException {
		TEMPLATE.reset();
		setter.run(TEMPLATE);
		return get();
	}

	public class ImmutableBlock extends Block {

		private final String title;
		private final Map<String, String> properties;

		ImmutableBlock(BlockTemplate template) {
			this.title = template.getName();
			this.properties = template.getProperties();
		}

		@Override
		public String getName() {
			return this.title;
		}

		@Override
		public Map<String, String> getProperties() {
			return this.properties;
		}

		@Override
		public String getProperty(Property property) {
			return this.properties.get(property.toString());
		}
	}
}
