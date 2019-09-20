import java.util.ArrayList;
import java.util.List;

public class Row {
	private List<String> cells;
	
	public Row() {
		cells = new ArrayList<String>();
	}
	
	public void addCell(Integer cell) {
		cells.add(cell.toString());
	}
	
	public void addCell(Float cell) {
		cells.add(cell.toString());
	}

	public void addCell(String cell) {
		cells.add(cell);
	}
	
	public List<String> getCells() {
		return cells;
	}
}
