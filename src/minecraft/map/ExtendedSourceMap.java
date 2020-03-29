package minecraft.map;

import minecraft.Position;

public interface ExtendedSourceMap extends SourceMap {

	public abstract void setScale(int scale);

	public abstract int getScale();

	public abstract Position getLocalPoint();

}
