package minecraft.reader.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import main.Converter;
import minecraft.McaBlock;
import minecraft.McaSection;
import minecraft.WorldPiece;

public class McaReader extends NbtReader {

	private static final NamedTag Y = new NamedTag(NbtTag.BYTE, "Y");
	private static final NamedTag SECTIONS = new NamedTag(NbtTag.LIST, "Sections");
	private static final NamedTag PALETTE = new NamedTag(NbtTag.LIST, "Palette");
	private static final NamedTag BLOCK_STATES = new NamedTag(NbtTag.LONG_ARRAY, "BlockStates");

	private Converter map;
	private WorldPiece convertee;

	public McaReader(DataInputStream stream, Converter map, WorldPiece convertee) {
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

		McaBlock[] mapping;

		public McaBlock[] get() {
			return this.mapping;
		}

		public void set(McaBlock[] mapping) {
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
		Queue<McaBlock> palette = new LinkedList<>();
		this.doListOfCompounds(() -> {
			McaBlock block = new McaBlock();
			block.read(this);
			palette.add(block);
		});
		mapping.set(getMapping(palette));
	}

	private static McaBlock[] getMapping(Queue<McaBlock> palette) {
		McaBlock[] mapping = new McaBlock[palette.size()];
		for (int i = 0; i < mapping.length; i++) {
			McaBlock block = palette.poll();
			mapping[i] = block;
//			mapping[i] = Material.get(block);
		}
		return mapping;
	}

}
