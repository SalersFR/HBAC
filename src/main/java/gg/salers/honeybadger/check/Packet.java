package gg.salers.honeybadger.check;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class Packet extends PacketContainer {
    public Packet(PacketContainer packet)
    {
        super(packet.getType(), packet.getHandle(), packet.getModifier());
    }

    public boolean isSending()
    {
        return this.getType().isServer();
    }

    public boolean isReceiving()
    {
        return this.getType().isClient();
    }

    public boolean isFlying()
    {
        return isReceiving() && (getType() == PacketType.Play.Client.FLYING || getType() == PacketType.Play.Client.POSITION || getType() == PacketType.Play.Client.LOOK || getType() == PacketType.Play.Client.POSITION_LOOK);
    }

    public boolean isUseEntity()
    {
        return isReceiving() && getType() == PacketType.Play.Client.USE_ENTITY;
    }

    public boolean isExplosion()
    {
        return isSending() && getType() == PacketType.Play.Server.EXPLOSION;
    }

    public boolean isRotation()
    {
        return isReceiving() && (getType() == PacketType.Play.Client.LOOK || getType() == PacketType.Play.Client.POSITION_LOOK);
    }

    public boolean isPosition()
    {
        return isReceiving() && (getType() == PacketType.Play.Client.POSITION || getType() == PacketType.Play.Client.POSITION_LOOK);
    }

    public boolean isArmAnimation()
    {
        return isReceiving() && getType() == PacketType.Play.Client.ARM_ANIMATION;
    }

    public boolean isAbilities()
    {
        return isReceiving() && getType() == PacketType.Play.Client.ABILITIES;
    }

    public boolean isBlockPlace()
    {
        return isReceiving() && getType() == PacketType.Play.Client.BLOCK_PLACE;
    }

    public boolean isBlockDig()
    {
        return isReceiving() && getType() == PacketType.Play.Client.BLOCK_DIG;
    }

    public boolean isWindowClick()
    { return isReceiving() && getType() == PacketType.Play.Client.WINDOW_CLICK; }

    public boolean isEntityAction()
    {
        return isReceiving() && getType() == PacketType.Play.Client.ENTITY_ACTION;
    }

    public boolean isPosLook()
    {
        return isReceiving() && getType() == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isCloseWindow()
    { return isReceiving() && getType() == PacketType.Play.Client.CLOSE_WINDOW; }

    public boolean isKeepAlive()
    { return isReceiving() && getType() == PacketType.Play.Client.KEEP_ALIVE; }

    public boolean isSteerVehicle()
    {
        return isReceiving() && getType() == PacketType.Play.Client.STEER_VEHICLE;
    }

    public boolean isHeldItemSlot()
    {
        return isReceiving() && getType() == PacketType.Play.Client.HELD_ITEM_SLOT;
    }

    public boolean isClientCommand()
    {
        return isReceiving() && getType() == PacketType.Play.Client.CLIENT_COMMAND;
    }

    public boolean isCustomPayload()
    { return isReceiving() && getType() == PacketType.Play.Client.CUSTOM_PAYLOAD; }

    public boolean isIncomingTransaction ()
    {
        return isReceiving() && getType() == PacketType.Play.Client.TRANSACTION;
    }

    public boolean isSendingTransaction()
    {
        return isSending() && getType() == PacketType.Play.Server.TRANSACTION;
    }

    public boolean isAcceptingTeleport()
    {
        return isReceiving() && getType() == PacketType.Play.Client.TELEPORT_ACCEPT;
    }
    public boolean isTeleport()
    {
        return isSending() && getType() == PacketType.Play.Server.ENTITY_TELEPORT;
    }

    public boolean isOutPosition()
    {
        return isSending() && getType() == PacketType.Play.Server.POSITION;
    }

    public boolean isVelocity()
    {
        return isSending() && getType() == PacketType.Play.Server.ENTITY_VELOCITY;
    }

    public boolean isRelEntityMove()
    {
        return isSending() && getType() == PacketType.Play.Server.REL_ENTITY_MOVE;
    }

    public boolean isAttack()
    {
        return isReceiving() && getType() == PacketType.Play.Client.USE_ENTITY && getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.ATTACK;
    }
}
