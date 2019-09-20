import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Nb {
	private Map<String, Tbl> tbls;
	private Set<String> classes;
	private Abcd abcd;
	private List<String> classifiedResults;
	private List<List<String>> file;
	private List<String> colRow;
	private static final int TRAIN_LIMIT = 4;
	private static final String OVERALL_TABLE = "*OT*";
	
	public Nb() {
		tbls = new HashMap<>();
		Tbl tbl = new Tbl();
		tbls.put(OVERALL_TABLE, tbl);
		abcd = new Abcd();
		classes = new HashSet<>();
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
			/*if(i == TRAIN_LIMIT+1) {
				for(String s: classes) {
					System.out.println("Table for class "+s);
					Tbl t = tbls.get(s);
					t.dump();
				}
			}*/
			if(i == 0) {
				Tbl tbl = tbls.get(OVERALL_TABLE);
				colRow = file.get(i);
				tbl.setCols(colRow);
				tbls.replace(OVERALL_TABLE, tbl);
			} else {
				if(i <= TRAIN_LIMIT) {
					List<String> row = file.get(i);
					if(classes.contains(row.get(row.size()-1))) {
						Tbl tbl = tbls.get(row.get(row.size()-1));
						tbl.addRow(row);
						tbls.replace(row.get(row.size()-1), tbl);
						tbl = tbls.get(OVERALL_TABLE);
						tbl.addRow(row);
						tbls.replace(OVERALL_TABLE, tbl);
					} else {
						Tbl tbl = new Tbl();
						tbl.setCols(colRow);
						tbl.addRow(row);
						tbls.put(row.get(row.size()-1), tbl);
						classes.add(row.get(row.size()-1));
						tbl = tbls.get(OVERALL_TABLE);
						tbl.addRow(row);
						tbls.replace(OVERALL_TABLE, tbl);
					}					
				} else {
					List<String> row = file.get(i);
					if(classes.contains(row.get(row.size()-1))) {
						String classification = classify(row);
						if(classification != "") {
							abcd.classify(row.get(row.size()-1), classification);
							classifiedResults.add(classification);
						}
						Tbl tbl = tbls.get(row.get(row.size()-1));
						tbl.addRow(row);
						tbls.replace(row.get(row.size()-1), tbl);
						tbl = tbls.get(OVERALL_TABLE);
						tbl.addRow(row);
						tbls.replace(OVERALL_TABLE, tbl);
					} else {
						Tbl tbl = new Tbl();
						tbl.setCols(colRow);
						tbls.put(row.get(row.size()-1), tbl);
						classes.add(row.get(row.size()-1));
						String classification = classify(row);
						abcd.classify(row.get(row.size()-1), classification);
						classifiedResults.add(classification);
						tbl.addRow(row);
						tbls.replace(row.get(row.size()-1), tbl);
						tbl = tbls.get(OVERALL_TABLE);
						tbl.addRow(row);
						tbls.replace(OVERALL_TABLE, tbl);
					}
				}
			}
		}
		//tbl.dump();
	}
	
	private String classify(List<String> row) {
		Tbl ov = tbls.get(OVERALL_TABLE);
		int n = ov.getRowCount();
		float maxProb = Float.MIN_VALUE;
		String classified = "";
		int maxfreq = 0;
		List<Float> ps = new ArrayList<>();
		String maxFreqClass = "";
		for(String s: classes) {
			//System.out.println(s);
			Tbl t = tbls.get(s);
			Set<Integer> ic = t.getIgnoreCol();
			List<Col> cols = t.getCols();
			int j = 0;
			float classProb = 1.0f;
			int classCount = t.getRowCount();
			if(classCount > maxfreq) {
				maxfreq = classCount;
				maxFreqClass = s;
			}
			float pc = (float) classCount/n;
			for(int i=0; i<row.size()-1; i++) {
				
				if(!ic.contains(i)) {
					Col c = cols.get(j);
					if(c.getClass() == Num.class) {
						Num num = (Num) c;
						float p = num.numLike(Float.parseFloat(row.get(i)));
						//System.out.println(c.getTxt()+" Pr: "+p);
						classProb*=p;
					} else if(c.getClass() == Sym.class) {
						Sym sym = (Sym) c;
						float p = sym.symLike(row.get(i), pc);
						classProb*=p;
					}
				}
				j++;
			}
			classProb*=pc;
			//System.out.println("DEBUG: Prob. for "+s+": "+classProb);
			if(classProb > maxProb) {
				maxProb = classProb;
				classified = s;
			}
			ps.add(classProb);
		}
		
		if(classified == "") {
			classified = maxFreqClass;
		}
		
		if(classified == "")
		System.out.println(row+" "+classified+" "+ps);
		return classified;
	}
}
