package minecraft.map;

import java.io.File;
import java.io.IOException;

import minecraft.ConvertingReport;
import minecraft.Position;
import periphery.SourceGame;

public interface ExtendedSourceMap extends SourceMap {

	public abstract Position getLocalPoint();

	public ConvertingReport write(File file, SourceGame game) throws IOException;
}
