package nbtReader;

import java.io.DataInputStream;
import java.io.IOException;

import converter.Converter;
import minecraft.Block;
import minecraft.Blocks;

public class ChunkReader extends NbtReader {

	private static final NamedTag Y = new NamedTag(NbtTag.BYTE, "Y");
	private static final NamedTag SECTIONS = new NamedTag(NbtTag.LIST, "Sections");
	private static final NamedTag PALETTE = new NamedTag(NbtTag.LIST, "Palette");
	private static final NamedTag BLOCK_STATES = new NamedTag(NbtTag.LONG_ARRAY, "BlockStates");

	private static final NamedTag PALETTE_NAME = new NamedTag(NbtTag.STRING, "Name");
	private static final NamedTag PALETTE_PROPERTIES = new NamedTag(NbtTag.COMPOUND, "Properties");

	private static final NamedTag LEVEL = new NamedTag(NbtTag.COMPOUND, "Level");

	private Converter map;
	private WorldPiece convertee;

	public ChunkReader(DataInputStream stream, Converter map, WorldPiece convertee) {
		super(stream);
		this.map = map;
		this.convertee = convertee;
	}

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
			Section section = this.readSection();
			this.map.addMcaSection(section);
		});
	}

	/**
	 * Reads the section after the title has already be been read.
	 */
	private Section readSection() throws IOException {
		Section section = new Section(this.convertee);
		this.doCompound(NbtTasks.I.create()
				.put(Y, () -> section.setHeight(this.readByte()))
				.put(BLOCK_STATES, () -> section.readBlocksRaw(this))
				.put(PALETTE, () -> this.readPalette(section)));
//		section.translateRawInfo(mapping.get()); // TODO lazy on output
		return section;
	}

	private void readPalette(Section section) throws IOException {
		this.doListOfCompounds(pos -> {
			section.addPalette(pos, this.readBlock());
		});
	}

	private Block readBlock() throws IOException {
		return Blocks.I.getIO(template -> {
			this.doCompound(NbtTasks.I.create()
					.put(PALETTE_NAME, () -> template.setName(this.readString()))
					.put(PALETTE_PROPERTIES,
							() -> this.doCompond(title -> template.addProperty(title, this.readString()))));
		});
	}
}
