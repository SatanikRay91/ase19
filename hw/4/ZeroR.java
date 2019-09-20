import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZeroR {
	private Tbl tbl;
	private Abcd abcd;
	private List<String> classifiedResults;
	private List<List<String>> file;
	private static final int TRAIN_LIMIT = 2;
	
	public ZeroR() {
		tbl = new Tbl();
		abcd = new Abcd();
		classifiedResults = new ArrayList<>();
		file = new ArrayList<List<String>>();
	}
	
	public void readFileAndClassify(String fileName) {
		BufferedReader csvReader;
		try {
			System.out.println("Reading file: "+fileName);
			csvReader = new BufferedReader(new FileReader(fileName));
			String line;
			int rowCount = 0, colCount = 0;
			try {
				while ((line = csvReader.readLine()) != null) {
					rowCount++;
					if(!line.isEmpty()) {
						String[] data = line.split(",");
						if(rowCount == 1) {
							colCount = data.length; 
						}
						if(data.length == colCount) {
							List<String> fileRow = new ArrayList<String>();
							for(String s: data) {
								if(!s.contains("#")) {
									/* Removing comments */
									fileRow.add(s.trim());
								} else {
									fileRow.add(s.trim().split(" ")[0]);
								}
							}
							file.add(fileRow);
						} else {
							file.add(null);
						}
					}
				}
				csvReader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		parseFileAndClassify();
	}
	
	public void displayResults() {
		abcd.abcdReport();
	}
	
	private void parseFileAndClassify() {
		for(int i=0; i<file.size(); i++) {
			if(i == 0) {
				List<String> row = file.get(i);
				tbl.setCols(row);
			} else {
				if(i <= TRAIN_LIMIT) {
					List<String> row = file.get(i);
					tbl.addRow(row);
				} else {
					List<String> row = file.get(i);
					String classification = tbl.getClassMode();
					abcd.classify(row.get(row.size()-1), classification);
					classifiedResults.add(classification);
					tbl.addRow(row);
				}
			}
		}
		//tbl.dump();
	}
}
