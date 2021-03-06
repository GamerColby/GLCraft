package net.codepixl.GLCraft.network.packet;

import net.codepixl.GLCraft.world.entity.mob.Mob;

/**
 * Sent from server to client to signify that a Mob's health has been set.
 */
public class PacketHealth extends Packet{
	public float health;
	public float airLevel;
	public int entityID;
	public PacketHealth(Mob m){
		this.health = m.health;
		this.airLevel = m.airLevel;
		this.entityID = m.getID();
	}
}
