package storagecraft.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import storagecraft.StorageCraft;
import storagecraft.network.MessageTileUpdate;

public abstract class TileBase extends TileEntity implements IUpdatePlayerListBox
{
	public static final int UPDATE_RANGE = 256;

	private EnumFacing direction = EnumFacing.NORTH;

	protected int ticks;

	@Override
	public void update()
	{
		ticks++;

		if (!worldObj.isRemote)
		{
			if (this instanceof INetworkTile)
			{
				TargetPoint target = new TargetPoint(worldObj.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), UPDATE_RANGE);

				StorageCraft.NETWORK.sendToAllAround(new MessageTileUpdate(this), target);
			}
		}
	}

	public void setDirection(EnumFacing direction)
	{
		this.direction = direction;
	}

	public EnumFacing getDirection()
	{
		return direction;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		direction = EnumFacing.getFront(nbt.getInteger("Direction"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("Direction", direction.ordinal());
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("Direction", direction.ordinal());

		return new S35PacketUpdateTileEntity(pos, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		direction = EnumFacing.getFront(packet.getNbtCompound().getInteger("Direction"));
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return false;
	}

	public IInventory getDroppedInventory()
	{
		return null;
	}
}