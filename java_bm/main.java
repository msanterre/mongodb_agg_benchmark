import java.net.UnknownHostException;
import java.util.HashMap;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class main {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(final String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		String[] fields = {"a","b", "c", "d", "e", "f"};
		MongoClient client = new MongoClient();
		DB db = client.getDB("benchmark");
		DBCollection coll = db.getCollection("doodaas");
		HashMap<String, Integer> aggregate = new HashMap<>();
		
		for(String field : fields){
			aggregate.put(field, 0);
		}
		
		System.out.println("Starting");

		long startTime = System.currentTimeMillis();

		DBCursor cursor = coll.find();
		
		System.out.println(String.format("Count: %d", coll.count()));
		try{
			while(cursor.hasNext()){
				DBObject doc = cursor.next();
				for(String field : fields){
					aggregate.put(field, aggregate.get(field) + (Integer)doc.get(field));
				}
			}
		}finally{
			cursor.close();
		}
		
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		
		System.out.println(String.format("%fs", (duration / 1000.0f)));
		System.out.println(aggregate);
		System.out.println("Done");
	}

}
