package vmfWriter;

public interface VmfValue {
	abstract String getVmf();

	abstract VmfValue copy();
}
