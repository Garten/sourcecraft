package minecraft.reader.nbt;

import java.util.HashMap;
import java.util.Map;

import basic.RunnableThrowing;

public class NbtTasks {

	public static final NbtTasks I = new NbtTasks();

	private Map<NamedTag, RunnableThrowing> tasks;

	private NbtTasks() {

	}

	public NbtTasks put(NamedTag namedTag, RunnableThrowing task) {
		this.tasks.put(namedTag, task);
		return this;
	}

	public NbtTasks create(NamedTag namedTag, RunnableThrowing task) {
		NbtTasks result = new NbtTasks();
		result.tasks = new HashMap<>();
		result.put(namedTag, task);
		return result;
	}

	public NbtTasks create() {
		NbtTasks result = new NbtTasks();
		result.tasks = new HashMap<>();
		return result;
	}

	public RunnableThrowing get(NamedTag namedTag) {
		return this.tasks.get(namedTag);
	}
}
