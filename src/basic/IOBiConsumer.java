package basic;

import java.io.IOException;

public interface IOBiConsumer<K, V> {

	public abstract void accept(K k, V v) throws IOException;

}
