package refinedstorage.tile;

import refinedstorage.tile.data.TileDataParameter;

public interface IStorageGui {
    String getGuiTitle();

    TileDataParameter<Integer> getTypeParameter();

    TileDataParameter<Integer> getRedstoneModeParameter();

    TileDataParameter<Integer> getCompareParameter();

    TileDataParameter<Integer> getFilterParameter();

    TileDataParameter<Integer> getPriorityParameter();

    int getStored();

    int getCapacity();
}
