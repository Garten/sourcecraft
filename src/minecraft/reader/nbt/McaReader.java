package minecraft.reader.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import minecraft.McaSection;
import minecraft.WorldPiece;
import minecraft.map.DefaultMinecraftMapConverter;
import source.Material;

public class McaReader extends NbtReader {

	private static final NamedTag Y = new NamedTag(NbtTag.BYTE, "Y");
	private static final NamedTag SECTIONS = new NamedTag(NbtTag.LIST, "Sections");
	private static final NamedTag PALETTE = new NamedTag(NbtTag.LIST, "Palette");
	private static final NamedTag BLOCK_STATES = new NamedTag(NbtTag.LONG_ARRAY, "BlockStates");
	private static final NamedTag PALETTE_NAME = new NamedTag(NbtTag.STRING, "Name");
	private static final NamedTag PALETTE_PROPERTIES = new NamedTag(NbtTag.COMPOUND, "Properties");

	private DefaultMinecraftMapConverter map;
	private WorldPiece convertee;

	public McaReader(DataInputStream stream, DefaultMinecraftMapConverter map, WorldPiece convertee) {
		super(stream);
		this.map = map;
		this.convertee = convertee;
	}

	private static final NamedTag LEVEL = new NamedTag(NbtTag.COMPOUND, "Level");

	public void readChunk() throws IOException {
		if (this.readTag() == NbtTag.COMPOUND) {
			this.readTitle();
			this.doCompound(NbtTasks.I.create()
					.put(LEVEL, () -> this.doCompound(NbtTasks.I.create()
							.put(SECTIONS, this::readSections))));
		}
	}

	private void readSections() throws IOException {
		this.doListOfCompounds(() -> {
			McaSection section = this.readSection();
			this.map.addMcaSection(section);
		});
	}

	private class Mapping {

		int[] mapping;

		public int[] get() {
			return this.mapping;
		}

		public void set(int[] mapping) {
			this.mapping = mapping;
		}
	}

	/**
	 * Reads the section after the title has already be been read.
	 */
	private McaSection readSection() throws IOException {
		McaSection section = new McaSection(this.convertee);
		Mapping mapping = new Mapping();
		NbtTasks sectionTasks = NbtTasks.I.create()
				.put(Y, () -> section.setHeight(this.readByte()))
				.put(BLOCK_STATES, () -> section.readBlocksRaw(this))
				.put(PALETTE, () -> this.readPalette(mapping));
		this.doCompound(sectionTasks);
		section.translateRawInfo(mapping.get());
		return section;
	}

	private void readPalette(Mapping mapping) throws IOException {
		Queue<String> palette = new LinkedList<>();
		NbtTasks paletteTasks = NbtTasks.I.create()
				.put(PALETTE_NAME, () -> palette.add(this.readString()))
				.put(PALETTE_PROPERTIES, this::skipCompound);
		this.doListOfCompounds(() -> this.doCompound(paletteTasks));
		mapping.set(this.getMapping(palette));
	}

	private int[] getMapping(Queue<String> palette) {
		int[] mapping = new int[palette.size()];
		for (int i = 0; i < mapping.length; i++) {
			String name = palette.poll();
			mapping[i] = Material.get(name);
		}
		return mapping;
	}

}
