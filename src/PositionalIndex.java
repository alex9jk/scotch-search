import java.util.*;
public class PositionalIndex {

	private ArrayList<String> termList;
	private ArrayList<ArrayList<DocId>> docLists;
	private ArrayList<Review> reviewList;
	
	public PositionalIndex(ArrayList<Review> list) {
		reviewList = list;
		docLists = new ArrayList<ArrayList<DocId>>();
		termList = new ArrayList<String>();
		ArrayList<DocId> docList;
		for(int i =0; i <reviewList.size();i++) {
			String[] tokens = reviewList.get(i).getReviewDesc().split(" ");
			String token;
			for(int j=0; j<tokens.length;j++) {
				token = tokens[j];
				if(!termList.contains(token)) {
					termList.add(token);
					DocId did = new DocId(i,j);
					docList = new ArrayList<DocId>();
					docList.add(did);
					docLists.add(docList);
				}
				else {
					int index = termList.indexOf(token);
					docList = docLists.get(index);
					int k=0;
					boolean match = false;
					
					for(DocId did : docList) {
						if(did.docId == i) {
							did.insertPosition(j);
							docList.set(k, did);
							match = true;
							break;
						}
						k++;						
					}
					if(!match) {
						DocId did = new DocId(i,j);
						docList.add(did);
					}
				}			
			}
		}
	}
}
class DocId {
	int docId;
	ArrayList<Integer> positionList;
	
	//first time you see this term in document
	//initial position
	public DocId(int did, int position) {
		docId = did;
		positionList = new ArrayList<Integer>();
		positionList.add(new Integer(position));
	}
	public void insertPosition(int position) {
		positionList.add(new Integer(position));
	}
	public String toString() {
		String docIdString = "" + docId +":<";
		for(Integer pos: positionList) {
			docIdString += pos + ",";
		}
		docIdString = docIdString.substring(0,docIdString.length()-1) + ">";
		return docIdString;
	}
	
}