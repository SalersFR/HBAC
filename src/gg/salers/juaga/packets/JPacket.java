package gg.salers.juaga.packets;

import com.comphenix.protocol.events.PacketContainer;

public class JPacket {
	
	private PacketType type;
	private PacketContainer packetContainer;

	public PacketType getType() {
		return type;
	}

	public void setType(PacketType type) {
		this.type = type;
		
	}

	public JPacket(PacketType type, PacketContainer container) {
		this.type = type;
		this.packetContainer = container;
	}

	public PacketContainer getPacketContainer() {
		return packetContainer;
	}
	
	
	
	

}
