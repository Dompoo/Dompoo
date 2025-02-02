package memory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Memory {
	
	private long used;
	private long max;
	
	@Override
	public String toString() {
		return "Memory{" +
				"used=" + used +
				", max=" + max +
				'}';
	}
}
