import java.util.*;
public class InvertedIndex {
	private ArrayList<Review> reviewList;
	private ArrayList<String> termList;
	private ArrayList<ArrayList<Integer>> docLists;

	public InvertedIndex(ArrayList<Review> r) {
		reviewList = r;
		termList = new ArrayList<String>();
		docLists = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> docList;
		
		for(int i=0; i< reviewList.size();i++) {
			String [] tokens = reviewList.get(i).getReviewDesc().split(" ");
			Integer docId = new Integer(i);
			for(String token:tokens) {
				if(!termList.contains(token)) {
					termList.add(token);
					docList = new ArrayList<Integer>();
					docList.add(docId);
					docLists.add(docList);
				}
				else {
					int index = termList.indexOf(token);
					docList = docLists.get(index);
					
					if(!docList.contains(index)) {
						docList.add(docId);
						docLists.set(index, docList);
					}
					
				}
			}
		}
		
	}
	public String toString() {
		String matrixString = new String();
		ArrayList<Integer> docList;
		Collections.sort(termList, String.CASE_INSENSITIVE_ORDER);
		for(int i = 0; i< termList.size(); i++) {
			matrixString += String.format("%-20s", termList.get(i));
			docList = docLists.get(i);
			for(int j =0; j<docList.size();j++) {
				//System.out.println(docList.size());
				//docList.size().parseInt()
				matrixString += Integer.toString(docList.size()) + " "+ docList.get(j) + "\n";
				matrixString += "\n";
			}
			
		}
		return matrixString;
	}

	public void getReviewSize() {
		double avg = 0;
		int sum =0;
		for(int i=0;i<reviewList.size();i++) {
			sum +=reviewList.get(i).getReviewDesc().length();
			System.out.println();
		}
		avg = (sum) / termList.size();
		System.out.println(avg);
	}
	public void commonTerms() {
		for(int i=0; i<termList.size();i++) {}
	}
	
	public ArrayList<String> getTermList(){
		return termList;
	}
	
}
