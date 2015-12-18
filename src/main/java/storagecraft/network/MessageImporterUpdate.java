package storagecraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import storagecraft.tile.TileImporter;

public class MessageImporterUpdate implements IMessage, IMessageHandler<MessageImporterUpdate, IMessage> {
	private int x;
	private int y;
	private int z;
	private int compareFlags;

	public MessageImporterUpdate() {
	}

	public MessageImporterUpdate(int x, int y, int z, int compareFlags) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.compareFlags = compareFlags;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		compareFlags = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(compareFlags);
	}

	@Override
	public IMessage onMessage(MessageImporterUpdate message, MessageContext context) {
		EntityPlayerMP player = context.getServerHandler().playerEntity;

		TileEntity tile = player.worldObj.getTileEntity(message.x, message.y, message.z);

		if (tile instanceof TileImporter) {
			((TileImporter) tile).setCompareFlags(message.compareFlags);
		}

		return null;
	}
}