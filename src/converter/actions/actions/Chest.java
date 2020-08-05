package converter.actions.actions;

import converter.Skins;
import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import minecraft.Property;
import vmfWriter.Cuboid;
import vmfWriter.Orientation;
import vmfWriter.Skin;

public class Chest extends Action {

    @Override
    public void add(Mapper context, Position pos, Block block) {
        Position end = pos;
        int pixels = 16;
        Position startOffset = new Position(1, 0, 1);
        Position endOffset = new Position(1, 2, 1);
        Cuboid chest = context.createCuboid(pos, end, pixels, startOffset, endOffset, block);
        chest.setSkin(this.constructSkin(block));
        context.addDetail(chest);
    }

    private Skin constructSkin(Block block) {
        Skin oldSkin = Skins.INSTANCE.getSkin(block);
        Orientation dir = this.getOrientation(block);
        String main = oldSkin.materialFront;
        String front = oldSkin.materialBack;
        String top = oldSkin.materialTop;
        return new Skin(main, top, front, dir, oldSkin.scale);
    }

    private Orientation getOrientation(Block block) {
        String dir = block.getProperty(Property.facing);
        if (dir.equals("north")) return Orientation.NORTH;
        else if (dir.equals("south")) return Orientation.SOUTH;
        else if (dir.equals("east")) return Orientation.EAST;
        else return Orientation.WEST;
    }
}