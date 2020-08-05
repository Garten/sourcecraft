package nbtReader;

import java.io.IOException;

import converter.Converter;

public class Chunk {

	private static final NamedTag SECTIONS = new NamedTag(NbtTag.LIST, "Sections");
	private static final NamedTag LEVEL = new NamedTag(NbtTag.COMPOUND, "Level");

	public Chunk readNbt(NbtReader reader, WorldPiece piece, Converter target) throws IOException {
		if (reader.readTag() == NbtTag.COMPOUND) {
			reader.readTitle(); // is empty
			reader.doCompound(NbtTasks.I.create()
					.put(LEVEL, () -> reader.doCompound(NbtTasks.I.create()
							.put(SECTIONS, () -> reader.doListOfCompounds(() -> {
								Section section = new Section(piece);
								section.readNbt(reader);
								target.addSection(section);
							})))));
		}
		return this;
	}
}
