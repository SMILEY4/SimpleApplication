package de.ruegnerlukas.simpleapplication.core.presentation.slot;

import java.util.HashMap;
import java.util.Map;

public final class SlotProvider {


	/**
	 * Utility class
	 */
	private SlotProvider() {
	}




	/**
	 * A map of all registered slots and their identifiers.
	 */
	private static final Map<String, Slot> SLOTS = new HashMap<>();




	/**
	 * Creates a new slot with the given id and action
	 *
	 * @param slotId the unique identifier of the new slot
	 * @param action the action of the new slot
	 * @return the created slot or null if a slot with the given id already exists
	 */
	public static Slot newSlot(final String slotId, final SlotAction action) {
		if (SLOTS.containsKey(slotId)) {
			System.err.println("Slot with id '" + slotId + "' already exists.");
			return null;
		} else {
			final Slot slot = new Slot(slotId, action);
			SLOTS.put(slotId, slot);
			return slot;
		}
	}




	/**
	 * @param slotId the unique identifier of the slot
	 * @return the slot with the given id or null
	 */
	public static Slot getSlot(final String slotId) {
		return SLOTS.get(slotId);
	}


}
