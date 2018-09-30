import java.util.*;
import java.io.*;

public class WhiskyParser {

	public static void main(String[] args) {
		WhiskyParser wp = new WhiskyParser();
		wp.processStopWords();
		wp.tokenReview();
		System.out.println(wp.toString());
	}

	private ArrayList<Review> columnList;
	private ArrayList<Review> stemColumnList;
	private ArrayList<String> stopList;

	public WhiskyParser() {
		stopList = new ArrayList<String>();
		columnList = new ArrayList<Review>();

		try {
			File dataFile = new File("C:\\Users\\alex9\\eclipse-workspace\\Whisky\\src\\data1.csv");
			FileReader fr = new FileReader(dataFile);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			String[] columnArr;
			while ((line = br.readLine()) != null) {
				columnArr = line.split(",");
				Review r = new Review(columnArr[0], columnArr[1], Integer.parseInt(columnArr[2]),
						Integer.parseInt(columnArr[3]), columnArr[4]);
				columnList.add(r);

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException f) {
			f.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
		}

	}

	public String toString() {
		String columnStr = new String();
		for (int i = 0; i < stemColumnList.size(); i++) {
			columnStr += "name: " + stemColumnList.get(i).getName() + "\nCategory: "
					+ stemColumnList.get(i).getCategory() + "\nReview Score: " + stemColumnList.get(i).getReviewPt()
					+ "\nPrice: " + stemColumnList.get(i).getPrice() + "\nReview: "
					+ stemColumnList.get(i).getReviewDesc() + "\n\n";
		}
		return columnStr;
	}

	public String[] parse(String review) {
		String[] tokens = null;
		String processedReview = review.toLowerCase();
		tokens = processedReview.split("[ .,?!:;$%&*+()\\-\\^\\\"]+");
		return tokens;
	}

	public void tokenReview() {
		String parsedDoc = "";
		Stemmer st = new Stemmer();
		stemColumnList = new ArrayList<Review>();
		for (int i = 0; i < columnList.size(); i++) {
			String[] tokens = parse(columnList.get(i).getReviewDesc());

			for (String token : tokens) {
				if (stopWordCheck(token) != -1) {
					continue;
				} else {
					st.add(token.toCharArray(), token.length());
					st.stem();
					parsedDoc += st + " ";
				}
			}
			Review r = new Review(columnList.get(i).getName(), columnList.get(i).getCategory(),columnList.get(i).getReviewPt(), columnList.get(i).getPrice(), parsedDoc);
			stemColumnList.add(r);
			parsedDoc = "";
		}
	}

	public int stopWordCheck(String key) {
		int low = 0;
		int high = stopList.size() - 1;

		while (low <= high) {
			int mid = low + (high - low) / 2;
			int result = key.compareTo(stopList.get(mid));
			if (result < 0) {
				high = mid - 1;
			} else if (result > 0) {
				low = mid + 1;
			} else {
				return mid;
			}
		}

		return -1;
	}

	public void processStopWords() {
		try {
			FileReader fr = new FileReader("C:\\Users\\alex9\\eclipse-workspace\\Whisky\\src\\stopwords.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String stopWord = line.toLowerCase();
				stopList.add(stopWord);
			}
			br.close();
			Collections.sort(stopList);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

class Review {
	private String name;
	private String category;
	private int reviewPt;
	private int price;
	private String description;

	public Review(String _name, String _category, int review, int _price, String _description) {
		name = _name;
		category = _category;
		reviewPt = review;
		price = _price;
		description = _description;
	}

	public String getName() {
		return this.name;
	}

	public String getCategory() {
		return this.category;
	}

	public int getReviewPt() {
		return this.reviewPt;
	}

	public int getPrice() {
		return this.price;
	}

	public String getReviewDesc() {
		return this.description;
	}
}