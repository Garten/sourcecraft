package nbtReader;

import java.util.HashMap;
import java.util.Map;

import basic.IORunnable;

public class NbtTasks {

	public static final NbtTasks I = new NbtTasks();

	private Map<NamedTag, IORunnable> tasks;

	private NbtTasks() {

	}

	public NbtTasks put(NamedTag namedTag, IORunnable task) {
		this.tasks.put(namedTag, task);
		return this;
	}

	public NbtTasks create(NamedTag namedTag, IORunnable task) {
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

	public IORunnable get(NamedTag namedTag) {
		return this.tasks.get(namedTag);
	}
}
