package gg.salers.juaga.packets;

import net.minecraft.server.v1_8_R3.Packet;

public class JPacket {
	
	private PacketType type;
	private Packet<?> rawPacket;
	
	public JPacket(PacketType type, Packet<?> raw) {
		this.type = type;
		this.rawPacket = raw;
	}
	
	public PacketType getType() {
		return type;
	}

	public Packet<?> getRawPacket() {
		return rawPacket;
	}
	
	

}
