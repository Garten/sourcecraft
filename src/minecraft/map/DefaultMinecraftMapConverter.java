package minecraft.map;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import basic.Loggger;
import basic.RunnableWith;
import basic.Tuple;
import cuboidFinder.SubBlockMapCuboidFinder;
import main.ConverterData;
import minecraft.Bounds;
import minecraft.ChunkPosition;
import minecraft.ConvertingReport;
import minecraft.McaSection;
import minecraft.Minecraft;
import minecraft.OrientationStairs;
import minecraft.Position;
import minecraft.SubBlockPosition;
import minecraft.WorldPiece;
import minecraft.reader.RegionFile;
import minecraft.reader.nbt.McaReader;
import periphery.ConvertOption;
import periphery.Place;
import periphery.SourceGame;
import source.Material;
import source.MaterialFilter;
import source.SkinManager;
import source.addable.AddableManager;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;
import vmfWriter.Orientation;
import vmfWriter.Ramp;
import vmfWriter.Skin;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.pointEntity.pointEntity.LightEnvironment;
import vmfWriter.entity.pointEntity.pointEntity.ShadowControl;

public class DefaultMinecraftMapConverter extends MinecraftMapConverter {

	private final Bounds bound;

	private final Position arraySize;

	private int[][][] materialField;
	private int[][][] materiatFieldRerun;
	private boolean[][][] isNextToAir;

	private Map<Position, Integer> subBlocks;
	private Queue<Position> subBlockPositions;

	private boolean isAirBlock[][][];

	private AddableManager addableManager;

	private int rerun = 0;

	private final ConvertOption convertOption;

	private int scale;

	public DefaultMinecraftMapConverter(ConverterData converterData) {
		super(new DefaultSourceMap(converterData.getTexturePack(), converterData.getOption()
				.getScale()));

		this.convertOption = converterData.getOption();
		super.target.setSkyTexture(this.convertOption.getSkyTexture());

		this.scale = this.convertOption.getScale();

		this.addableManager = new AddableManager(this, converterData.getGame(), this.convertOption.getAddablesAsStrings());

		this.bound = converterData.getPlace()
				.createBound();

		Position start = converterData.getPlace()
				.getStart();
		Position end = converterData.getPlace()
				.getEnd();

		this.arraySize = new Position(3 + end.getX() - start.getX(), 3 + end.getY() - start.getY(), 3 + end.getZ() - start.getZ());

		this.isNextToAir = this.createBooleanArray(this.arraySize);
		this.isAirBlock = this.createBooleanArray(this.arraySize);

		this.materialField = this.createIntArray(this.arraySize);
		this.materiatFieldRerun = this.createIntArray(this.arraySize);

		this.forAllPositions(p -> {
			this.materialField[p.x][p.y][p.z] = Material._UNSET;
			this.materiatFieldRerun[p.x][p.y][p.z] = Material._UNSET;
			this.isNextToAir[p.x][p.y][p.z] = true;
		});

		this.subBlocks = new HashMap<>();
		this.subBlockPositions = new ArrayDeque<>();
	}

	private boolean[][][] createBooleanArray(Position size) {
		return new boolean[size.getX()][size.getY()][size.getZ()];
	}

	private int[][][] createIntArray(Position size) {
		return new int[size.getX()][size.getY()][size.getZ()];
	}

	public void markBorder() {
		// top + bottom
		for (int x = 0; x < this.arraySize.getX(); x++) {
			for (int z = 0; z < this.arraySize.getZ(); z++) {
				this.isNextToAir[x][0][z] = true;
				this.isNextToAir[x][this.arraySize.getY() - 1][z] = true;
			}
		}
		// sides
		for (int y = 1; y < this.arraySize.getY() - 2; y++) {
			for (int x = 0; x < this.arraySize.getX(); x++) {
				this.isNextToAir[x][y][0] = true;
				this.isNextToAir[x][y][this.arraySize.getZ() - 1] = true;
			}
			for (int z = 1; z < this.arraySize.getZ() - 1; z++) {
				this.isNextToAir[0][y][z] = true;
				this.isNextToAir[this.arraySize.getX() - 1][y][z] = true;
			}
		}
	}

	public void forAllPositions(RunnableWith<Position> execute) {
		this.forPositions(new Position(1, 1, 1), this.arraySize.getOffset(-2, -2, -2), execute);
	}

	public void forPositions(Position start, Position end, RunnableWith<Position> execute) {
		for (int y = start.y; y <= end.y; y++) {
			for (int x = start.x; x <= end.x; x++) {
				for (int z = start.z; z <= end.z; z++) {
					execute.run(new Position(x, y, z));
				}
			}
		}
	}

	public void addObjects(SourceMap target, SourceGame game) {
		this.setAirBlocks();
		do {
			Loggger.log("run");
			this.rerun--;
			this.markBorder(); // temp fix
			this.forAllPositions(position -> {
				if (this.isNextToAir(position) == true) {
					this.addableManager.add(position);
				}
			});
			if (this.rerun > 0) {
				Loggger.log("run");
				int[][][] temp = this.materiatFieldRerun;
				this.materiatFieldRerun = this.materialField;
				this.materialField = temp;
				this.rerun--;
				this.forAllPositions(position -> {
					if (this.isNextToAir(position) == true) {
						this.addableManager.add(position);
					}
				});
				temp = this.materiatFieldRerun;
				this.materiatFieldRerun = this.materialField;
				this.materialField = temp;
				this.rerun--;
			}
		} while (this.rerun > 0);
		this.addSubBlocksMap();
		this.addSkyShell();
		this.addSpawnPoint(game);
	}

	/*
	 * adds a spawn point in the middle of the map
	 */
	private void addSpawnPoint(SourceGame game) {
		Position middle = Position.getMean(new Position(1, 1, 1), this.arraySize.getOffset(-1, -1, -1));
		Position middleX = middle.getOffset(-1, 0, 0);
		Position middleZ = middle.getOffset(0, 0, -1);
		Position middleXZ = middle.getOffset(-1, 0, -1);
		int y = this.arraySize.getY() - 2;
		for (; y > 1; y--) {
			middle.setY(y);
			middleX.setY(y);
			middleZ.setY(y);
			middleXZ.setY(y);
			if (!(this.isAir(middle) && this.isAir(middleX) && this.isAir(middleZ) && this.isAir(middleXZ))) {
				break;
			}
		}
		middle.setY(y + 1);
		Position converted = this.convert(middle);
		converted.move(0, 0, 10);
		this.addPointEntity(game.getSpawnEntity(), converted);
		this.setCameraPosition(converted.move(0, 0, 72));
		this.setCameraLook(new Position(0, 0, 0));
	}

	private void setAirBlocks() {
		this.forAllPositions(position -> {
			if (this.isAirBlockInitiate(position)) {
				this.setAirBlock(position, true);
				this.markNeighbors(position);
			} else {
				this.setAirBlock(position, false);
			}
		});
		this.markBorder();
	}

	// marks neighbor blocks and itself to be needed
	public void markNeighbors(Position position) {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		this.isNextToAir[x][y][z] = true;
		this.isNextToAir[x + 1][y][z] = true;
		this.isNextToAir[x - 1][y][z] = true;
		this.isNextToAir[x][y + 1][z] = true;
		this.isNextToAir[x][y - 1][z] = true;
		this.isNextToAir[x][y][z + 1] = true;
		this.isNextToAir[x][y][z - 1] = true;
	}

	@Override
	public boolean isAirBlock(Position position) {
		return this.isAirBlock[position.getX()][position.getY()][position.getZ()];
	}

	public boolean isAir(Position position) {
		int material = this.materialField[position.getX()][position.getY()][position.getZ()];
		return material == Material.AIR || material == Material.VOID_AIR || material == Material.CAVE_AIR || material == Material._UNSET;
	}

	@Override
	public boolean isAirBlockInitiate(Position position) {
		int material = this.materialField[position.getX()][position.getY()][position.getZ()];
		return this.addableManager.isAirMaterial(material);
	}

	private void setAirBlock(Position position, boolean value) {
		this.isAirBlock[position.x][position.y][position.z] = value;
	}

	@Override
	public void markAsConverted(Position start, Position end) {
		this.forPositions(start, end, this::markAsConverted);
	}

	@Override
	public void markAsConverted(Position p) {
		this.materialField[p.x][p.y][p.z] = -this.materialField[p.x][p.y][p.z];
	}

	public static DefaultMinecraftMapConverter open(File fileFolder, ConverterData converterData) throws IOException {
		Place place = converterData.getPlace();
		SkinManager.init(converterData.getTexturePack(), converterData.getOption()
				.getScale());

		final DefaultMinecraftMapConverter converter = new DefaultMinecraftMapConverter(converterData);

		Position start = place.getStart();
		Position end = place.getEnd();

		Position writeTarget = new Position(1, 1, 1);

		for (int mcX = start.getX(); mcX <= end.getX();) {
			Position diff = new Position();
			writeTarget.z = 1;
			for (int mcZ = start.getZ(); mcZ <= end.getZ();) {

				Loggger.log("from " + mcX + "," + mcZ + " to target " + writeTarget.x + " " + writeTarget.z);

				WorldPiece convertee = new WorldPiece(new Position(mcX, start.getY(), mcZ), end, writeTarget);
				converter.readChunk(fileFolder, convertee, writeTarget);
				diff = convertee.getAreaXZ();
				writeTarget.z += diff.z;

				mcZ += diff.z;
			}
			writeTarget.x += diff.x;
			mcX += diff.x;
		}
		return converter;
	}

	private void readChunk(File fileFolder, WorldPiece worldPiece, Position writeTarget) throws IOException {
		File file = Minecraft.getFileOfChunk(fileFolder, worldPiece);
		this.logReadingChunk(worldPiece, file);
		RegionFile regionfile = new RegionFile(file);
		if (file.exists() == false) {
			throw new IOException("File does not exist: " + file.toString());
		}
		DataInputStream inputStream = regionfile.getChunkDataInputStream(worldPiece.getLocalChunk()
				.getX(),
				worldPiece.getLocalChunk()
						.getZ());
		if (inputStream == null) {
			// throw new IOException("cannot find chunk in " + file.toString());
			Loggger.log("cannot find chunk file " + file.toString());
		} else {
			// Create NBTReader for chunk.
			McaReader reader = new McaReader(inputStream, this, worldPiece);
			reader.readChunk();
			inputStream.close();
		}
	}

	private void logReadingChunk(WorldPiece convertee, File file) {
		Loggger.log(convertee.toString());
		ChunkPosition chunk = convertee.getGlobalChunk();
		Loggger.log("reading global chunk (" + formatChunkNumber(chunk.getX()) + "," + formatChunkNumber(chunk.getZ()) + ") from " + file.toString());
	}

	private static String formatChunkNumber(int localChunk) {
		String string = localChunk + "";
		if (string.length() == 1) {
			string = " " + string;
		}
		return string;
	}

	public void addMcaSection(McaSection section) {
		Tuple<Bounds, Position> toWrite = section.getBoundAndTarget();
		Bounds bounds = toWrite.getFirst();
		Position target = toWrite.getSecond();
		for (int y = bounds.getLower().y; y <= bounds.getHigher().y; y++) {
			for (int z = bounds.getLower().z; z <= bounds.getHigher().z; z++) {
				for (int x = bounds.getLower().x; x <= bounds.getHigher().x; x++) {
					try {
						int sectionMaterial = section.getBlocks()[x][y][z];

						this.materialField[target.x + x][target.y + y][target.z + z] = sectionMaterial;

						// block next to air will be set true
						this.isNextToAir[target.x + x][target.y + y][target.z + z] = false;

					} catch (java.lang.ArrayIndexOutOfBoundsException e) {
						Loggger.log((target.x + x) + " " + (target.y + y) + " " + (target.z + z));
						Loggger.log("out of bound");
					}
				}
			}
		}
	}

	@Override
	public int getMaterial(Position p) {
		return this.materialField[p.getX()][p.getY()][p.getZ()];
	}

	@Override
	public void addMaterialForRerun(Position p, int material) {
		this.materiatFieldRerun[p.getX()][p.getY()][p.getZ()] = material;
	}

	@Override
	public boolean hasMaterial(Position position, int... materials) {
		for (int material : materials) {
			try {
				if (this.materialField[position.getX()][position.getY()][position.getZ()] == material) {
					return true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				Loggger.log(position.toAxisString());
			}
		}
		return false;
	}

	@Override
	public boolean hasOrHadMaterial(Position p, int... materials) {
		for (int material : materials) {
			if (this.hasOrHadMaterial(p, material)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasOrHadMaterial(Position position, int material) {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		return this.materialField[x][y][z] == material || this.materialField[x][y][z] == -material;
	}

	@Override
	public boolean hasOrHadMaterial(Position p, MaterialFilter filter) {
		return filter.filter(this.getMaterial(p));
	}

	@Override
	public boolean isNextToAir(Position p) {
		return this.isNextToAir[p.getX()][p.getY()][p.getZ()];
	}

	@Override
	public int getScale() {
		return this.scale;
	}

	@Override
	public void addSubBlock(Position position, SubBlockPosition pos, int materialInt) {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		this.addSubBlock(x, y, z, pos, materialInt);
	}

	private void addSubBlock(int x, int y, int z, SubBlockPosition pos, int materialInt) {
		Integer material = Integer.valueOf(materialInt);
		x = x * 2 + 1;
		y = y * 2 + 1;
		z = z * 2 + 1;
		Position point;
		switch (pos) {
		case BOTTOM_EAST_SOUTH:
			point = new Position(x, y - 1, z);
			break;
		case BOTTOM_EAST_NORTH:
			point = new Position(x, y - 1, z - 1);
			break;
		case BOTTOM_WEST_SOUTH:
			point = new Position(x - 1, y - 1, z);
			break;
		case BOTTOM_WEST_NORTH:
			point = new Position(x - 1, y - 1, z - 1);
			break;
		case TOP_EAST_SOUTH:
			point = new Position(x, y, z);
			break;
		case TOP_EAST_NORTH:
			point = new Position(x, y, z - 1);
			break;
		case TOP_WEST_SOUTH:
			point = new Position(x - 1, y, z);
			break;
		case TOP_WEST_NORTH:
			point = new Position(x - 1, y, z - 1);
			break;
		default:
			point = new Position(1, 1, 1);
			Loggger.warn("default case of subBlocks occured");
			break;
		}
		this.subBlocks.put(point, material);
		this.subBlockPositions.add(point);
	}

	@Override
	public ConvertingReport write(File file, SourceGame game) throws IOException {
		this.addObjects(this.target, game);
		this.target.write(file, game);
		return null;
	}

	/**
	 * adds skybox, light_environment and shadow_control
	 */
	public void addSkyShell() {
		int verticalHeight = this.bound.getHigher()
				.getY()
				- this.bound.getLower()
						.getY()
				+ 3;

		Position aPoint = new Position(0, -this.arraySize.getZ(), 0);
		Position hollow = new Position(1, -this.arraySize.getZ() + 1, 1);
		Position gPoint = new Position(this.arraySize.getX(), 0, verticalHeight + 2);
		aPoint.scale(this.scale);
		hollow.scale(this.scale);
		gPoint.scale(this.scale);
		this.createHollow(aPoint, hollow, gPoint, SkinManager.SKYBOX);

		aPoint = new Position(-this.scale * 4, 0, (verticalHeight + 2) * this.scale);
		hollow = new Position(-this.scale * 3, this.scale * 1, (verticalHeight + 2 + 1) * this.scale);
		gPoint = new Position(0, this.scale * 4, (verticalHeight + 2 + 4) * this.scale);
		this.createHollow(aPoint, hollow, gPoint, SkinManager.SKYBOX);

		// addSun
		Position p = new Position(-this.scale * 2, this.scale * 2, (verticalHeight + 4) * this.scale);
		this.target.addPointEntity(p, new LightEnvironment(p, this.convertOption.getSunLight(), this.convertOption.getSunAmbient()));
		p.move(this.scale / 2, 0, 0);
		this.target.addPointEntity(p, new ShadowControl(p, this.convertOption.getSunShadow()));
	}

	private void createHollow(Position aPoint, Position hollow, Position gPoint, Skin skin) {
		int hollowX = hollow.getX() - aPoint.getX();
		int hollowY = hollow.getY() - aPoint.getY();
		int hollowZ = hollow.getZ() - aPoint.getZ();
		// bottom
		this.target.addSolid(new Cuboid(new Tuple<>(aPoint, new Position(gPoint.getX(), gPoint.getY(), aPoint.getZ() + hollowZ)), skin));
		// top
		this.target.addSolid(new Cuboid(new Tuple<>(new Position(aPoint.getX(), aPoint.getY(), gPoint.getZ() - hollowZ), gPoint), skin));

		// x side
		this.target.addSolid(
				new Cuboid(new Tuple<>(aPoint.getOffset(0, 0, hollowZ), new Position(aPoint.getX() + hollowX, gPoint.getY(), gPoint.getZ() - hollowZ)), skin));
		this.target.addSolid(
				new Cuboid(new Tuple<>(new Position(gPoint.getX() - hollowX, aPoint.getY(), aPoint.getZ() + hollowZ), gPoint.getOffset(0, 0, -hollowZ)), skin));
		// y side
		this.target.addSolid(new Cuboid(
				new Tuple<>(aPoint.getOffset(hollowX, 0, hollowZ), new Position(gPoint.getX() - hollowX, aPoint.getY() + hollowY, gPoint.getZ() - hollowZ)),
				skin));
		this.target.addSolid(new Cuboid(
				new Tuple<>(new Position(aPoint.getX() + hollowX, gPoint.getY() - hollowY, aPoint.getZ() + hollowZ), gPoint.getOffset(-hollowX, 0, -hollowZ)),
				skin));
	}

	private void addSubBlocksMap() {
		super.target.setScale(super.target.getScale() / 2);
		this.scale = this.scale / 2;
		SubBlockMapCuboidFinder finder = new SubBlockMapCuboidFinder(this);
		while (this.subBlockPositions.isEmpty() == false) {
			Position startPoint = this.subBlockPositions.remove();
			Position start = new Position(startPoint);
			Integer materialInteger = this.subBlocks.get(start);
			if (materialInteger != null) {
				int material = materialInteger.intValue();
				Position end = finder.getBestXYZ(start, material);
				// super.target.addCuboid( start, end, material );

				this.addSolid(this.createCuboid(start, end, material));
				this.markAsConvertedSubMaterial(start, end);
			}
		}
		this.scale = this.scale * 2;
		super.target.setScale(super.target.getScale() * 2);
	}

	public boolean hasSubMaterial(Position position, int... materials) {
		// TODO
		int material = materials[0];
		// int presentMaterial = subBlocks[x][y][z];
		Integer presentMaterial = this.subBlocks.get(position);
		if (presentMaterial != null) {
			return presentMaterial == material;
		}
		return false;
	}

	private void markAsConvertedSubMaterial(Position start, Position end) {
		for (int xMark = start.getX(); xMark <= end.getX(); xMark++) {
			for (int yMark = start.getY(); yMark <= end.getY(); yMark++) {
				for (int zMark = start.getZ(); zMark <= end.getZ(); zMark++) {
					this.subBlocks.remove(new Position(xMark, yMark, zMark));
					// subBlocks[xMark][yMark][zMark] = -1;
				}
			}
		}
	}

	@Override
	public void setPointToGrid(Position position) {
		this.target.setPointToGrid(this.convert(position));
	}

	@Override
	public void movePointInGridDimension(double x, double y, double z) {
		// ugly
		super.target.movePointInGridDimension(x * this.scale, -z * this.scale, y * this.scale);
	}

	private Position convert(Position p) {
		// z and y axis are switched
		// NORTH-SOUTH axis is flipped
		return new Position(p.getX() * this.scale, -p.getZ() * this.scale, p.getY() * this.scale);
	}

	@Override
	public Position getMovedPointInGridDimension(Position p, double x, double y, double z) {
		int xDistance = (int) (x * this.scale);
		int yDistance = (int) (-z * this.scale);
		int zDistance = (int) (y * this.scale);
		return p.move(xDistance, yDistance, zDistance);
	}

	@Override
	public void movePointExactly(Position offset) {
		Position translated = new Position(offset.x, -offset.z, offset.y);
		this.target.movePointExactly(translated);
	}

	@Override
	public void addPointEntitys(Position start, Position end, int space, PointEntity type) {
		Tuple<Position, Position> conveted = this.convertPositions(start, end);
		super.target.addPointEntitys(conveted.getFirst(), conveted.getSecond(), space, type);
	}

	@Override
	public void addClipStairs(Position position, OrientationStairs orientationStairs) {
		this.addCustomPointEntity(position, orientationStairs.toString());
	}

	@Override
	public void addCustomPointEntity(Position p, String name) {
		this.target.addCustomPointEntity(this.getMovedPointInGridDimension(this.convert(p), 0.5, 0.5, 0.5), name);
	}

	@Override
	public void setMaterial(Position p, int material) {
		this.materialField[p.getX()][p.getY()][p.getZ()] = material;
	}

	@Override
	public void enableRerun(int amount) {
		if (amount > this.rerun) {
			this.rerun = amount;
		}
	}

	@Override
	public Cuboid createCuboid(Position start, Position end, int parts, Position offset, Position negativeOffset, int material) {
		double grid = (double) (this.scale) / (double) (parts);
		Position startNew = new Position(start.x * this.scale + offset.x * grid, (-end.z - 1) * this.scale + negativeOffset.z * grid,
				start.y * this.scale + offset.y * grid);
		Position endNew = new Position((end.x + 1) * this.scale - negativeOffset.x * grid, -start.z * this.scale - offset.z * grid,
				(end.y + 1) * this.scale - negativeOffset.y * grid);
		return new Cuboid(new Tuple<>(startNew, endNew), SkinManager.INSTANCE.getSkin(material));
	}

	@Override
	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align, int material) {
		double grid = ((double) (this.scale)) / (double) (parts);
		Position[] p = new Position[8];
		for (int i = 0; i < 8; i++) {
			int newX = (int) (start.x * this.scale + offset[i].x * grid);
			int newY = (int) -(start.z * this.scale + offset[i].z * grid);
			int newZ = (int) ((start.y + end.y - start.y) * this.scale + offset[i].y * grid);
			p[i] = new Position(newX, newY, newZ);
		}
		Skin skin = SkinManager.INSTANCE.getSkin(material);
		Free8Point shape = new Free8Point(p, skin, align);
		return shape;
	}

	public Tuple<Position, Position> convertPositions(Position start, Position endIn) {
		Position end = endIn.getOffset(1, 1, 1);
		Position startConverted = this.convert(start);
		Position endConverted = this.convert(end);
		Position.bringInOrder(startConverted, endConverted);

		return new Tuple<>(startConverted, endConverted);
	}

	@Override
	public Cuboid createCuboid(Position start, Position end, int material) {
		return new Cuboid(this.convertPositions(start, end), SkinManager.INSTANCE.getSkin(material));
	}

	@Override
	public Ramp createRamp(Cuboid cuboid, Orientation orientation) {
		return new Ramp(cuboid, orientation);
	}

	@Override
	public Ramp createRampCuttet(Cuboid cuboid, Orientation orientation, Orientation cut1, Orientation cut2) {
		// TODO Auto-generated method stub
		return null;
	}

}
