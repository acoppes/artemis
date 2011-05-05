package com.artemis;

import java.util.HashMap;
import java.util.Map;

import com.artemis.utils.Bag;

/**
 * If you need to communicate with systems from other system, then look it up here.
 * Use the world instance to retrieve a instance.
 * 
 * @author Arni Arent
 *
 */
public class SystemManager {
	private World world;
	private Map<Class<?>, EntitySystem> systems;
	private Bag<EntitySystem> bagged;
	private long nextSystemBits = 1;
	
	public SystemManager(World world) {
		this.world = world;
		systems = new HashMap<Class<?>, EntitySystem>();
		bagged = new Bag<EntitySystem>();
	}
	
	public EntitySystem setSystem(EntitySystem system) {
		system.setWorld(world);
		system.setSystemBit(nextSystemBits());
		systems.put(system.getClass(), system);
		bagged.add(system);
		return system;
	}

	private long nextSystemBits() {
		long systemBits = nextSystemBits;
		nextSystemBits *= 2;
		return systemBits;
	}
	
	public <T extends EntitySystem> T getSystem(Class<T> type) {
		return type.cast(systems.get(type));
	}
	
	public Bag<EntitySystem> getSystems() {
		return bagged;
	}
	
	/**
	 * After adding all systems to the world, you must initialize them all.
	 */
	public void initializeAll() {
		for(EntitySystem es : systems.values()) {
			es.initialize();
		}
	}

}
