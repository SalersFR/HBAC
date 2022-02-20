package gg.salers.honeybadger.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class HPacket {

    private final PacketType type;
    private final PacketContainer container;

    /**
     * CREDITS: MEDUSA (not the exact same thing but inspired of) github.com/GladUrBad/Medusa
     */

    public HPacket(PacketType type, PacketContainer container) {
        this.type = type;
        this.container = container;
    }

    public PacketContainer getContainer() {
        return container;
    }

    public PacketType getType() {
        return type;
    }

    public boolean isMove() {
        return type == PacketType.Play.Client.POSITION || type == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isUseEntity() {
        return type == PacketType.Play.Client.USE_ENTITY;
    }

    public boolean isArmAnimation() {
        return type == PacketType.Play.Client.ARM_ANIMATION;
    }

    public boolean isClientKP() {
        return type == PacketType.Play.Client.KEEP_ALIVE;
    }

    public boolean isServerKP() {
        return type == PacketType.Play.Server.KEEP_ALIVE;
    }

    public boolean isAttack() {
        return type == PacketType.Play.Client.USE_ENTITY && container.getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.ATTACK;
    }

    public boolean isDigging() {
        return type == PacketType.Play.Client.BLOCK_DIG;
    }

    public boolean isRot() {
        return type == PacketType.Play.Client.LOOK || type == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isFlying() {
        return type == PacketType.Play.Client.POSITION || type == PacketType.Play.Client.POSITION_LOOK ||
                type == PacketType.Play.Client.LOOK || type == PacketType.Play.Client.FLYING;
    }

    public boolean isRelMove() {
        return type == PacketType.Play.Server.REL_ENTITY_MOVE;
    }


}