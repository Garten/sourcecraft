package converter.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import basic.Loggger;
import minecraft.Block;
import minecraft.BlockTemplate;

public class BlockMap<Value> {

	private static final String SEPERATOR = "_";

	private Value fallback;
	private Map<Block, Value> map;

	private BlockTemplate newKey = new BlockTemplate();
	private BlockTemplate newSimpleKey = new BlockTemplate();

	public BlockMap() {
		this.map = new HashMap<>();
	}

	public BlockMap<Value> put(Supplier<Block> keyHolder, Value value) { // TODO
		this.map.put(keyHolder.get(), value);
		return this;
	}

	public Value getFallBackToPrefix(Block key) {
		this.newKey.copyFrom(key);
		Value result = this.getFallBackNoProperties(key);
		if (result != null) {
			return result;
		}
		String title = key.getName();
		while (true) {
			int i = title.lastIndexOf(SEPERATOR);
			if (i < 1) {
				break;
			}
			if (title.contains("ramp")) {
				Loggger.log("break");
			}
			title = title.substring(0, i);
			this.newKey.setName(title);
			result = this.getFallBackNoProperties(this.newKey);
			if (result != null) {
				this.put(key, result);
				return result;
			}
		}
		return this.fallback;
	}

	public Value getFallBackToSuffix(Block key) {
		Value result = this.getFallBackNoProperties(key);
		if (result != null) {
			return result;
		}
		String title = key.getName();
		int colonPos = title.indexOf(":");
		String nameSpace = title.substring(0, colonPos);
		while (true) {
			int i = title.indexOf(SEPERATOR);
			if (i < 1) {
				break;
			}
			title = title.substring(i + 1);
			this.newKey.setName(nameSpace + ":" + title);
			result = this.getFallBackNoProperties(this.newKey);
			if (result != null) {
				this.put(key, result);
				return result;
			}
		}
		return this.fallback;
	}

	private Value getFallBackNoProperties(Block key) {
		Value value = this.map.get(key);
		if (value != null) {
			return value;
		}
		// test without properties
		this.newSimpleKey.setName(key.getName());
		this.newSimpleKey.setProperties(null);
		value = this.map.get(this.newSimpleKey);
		if (value != null) {
			this.put(key, value);
			return value;
		}
		return null;
	}

	public Value get(Block key) {
		assert this.fallback != null;
		Value value = this.map.get(key);
		if (value == null) {
			return this.fallback;
		}
		return value;
	}

	public BlockMap<Value> setDefault(Value fallback) {
		this.fallback = fallback;
		return this;
	}

	public void print() {
		this.map.forEach((key, value) -> Loggger.log(key.toString() + " mapsTo " + value.toString()));
	}
}
