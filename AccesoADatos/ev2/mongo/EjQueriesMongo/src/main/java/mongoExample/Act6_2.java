package mongoExample;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.Updates;

public class Act6_2 {
	public static void main(String[] args) {
		MongoClient client = MongoClients.create();
		MongoDatabase db = client.getDatabase("rrss2");
		
		MongoCollection<Document> user = db.getCollection("user");
		Document doc;
		Bson filterDoc, updateDoc, projDoc;
		
		doc = new Document().append("_id", 5l)
				.append("name", "Juan")
				.append("surname", "García Castellano")
				.append("age", 23)
				.append("gender", "m")
				.append("registration date", new Date());
//		user.insertOne(doc);
		
		doc = new Document().append("_id", 6l)
				.append("name", "Beatriz")
				.append("surname", "Perez Solaz")
				.append("age", 27)
				.append("gender", "f")
				.append("registration date", new Date());
	//	user.insertOne(doc);
	
		filterDoc = Filters.empty();
		FindIterable<Document> res= user.find(filterDoc);
		res.forEach(r -> System.out.println(r.toJson()));
		
		doc = new Document().append("_id", 7l)
				.append("name", "Jorge")
				.append("surname", "Lopez Sevilla")
				.append("gender", "m")
				.append("registration date", new Date())
				.append("groups", Arrays.asList("basketball", "kitchen", "historical novel"));
//		user.insertOne(doc);
		
		filterDoc = Filters.eq("_id", 5);
		//user.deleteOne(filterDoc);
		
		updateDoc= Updates.set("groups", Arrays.asList("historical novel", "dance"));
		filterDoc = Filters.eq("_id", 6);
		//user.updateOne(filterDoc, updateDoc);
		
		MongoCollection<Document> company = db.getCollection("company");
		
		doc = new Document().append("_id",10l)
				.append("name", "Gardening Gardenia");
		//company.insertOne(doc);
		
		
		filterDoc = Filters.eq("_id", 10);
		updateDoc = Updates.combine(Updates.set(
				"adress", new Document().append("street", "Palmeras")
				.append("number", 8)
				.append("town", "Torrente")), 
				Updates.set("sector", "services"),
				Updates.set("web", "http://www.gardeninggardenia.com"));
		//company.updateOne(filterDoc, updateDoc);
		
		updateDoc = Updates.set("followers", 5);
		//company.updateOne(filterDoc, updateDoc);
		updateDoc = Updates.inc("followers", 2);
		//company.updateOne(filterDoc, updateDoc);
		updateDoc = Updates.inc("followers", -1);
		//company.updateOne(filterDoc, updateDoc);
		
		updateDoc = Updates.set("postal_code", 46009);
		//company.updateOne(filterDoc, updateDoc);
		
		updateDoc = Updates.unset("sector");
		//company.updateOne(filterDoc, updateDoc);
		
		filterDoc= Filters.eq("_id", 6);
		updateDoc = Updates.push("groups", "theatre");
//		user.updateOne(filterDoc, updateDoc);
		
		filterDoc= Filters.eq("_id", 6);
		updateDoc = Updates.pull("groups", "dance");
	//	user.updateOne(filterDoc, updateDoc);
		
		
		updateDoc = Updates.combine(Updates.set("comments", Arrays.asList("")), Updates.set("total_comments", 0));
		//user.updateMany(Filters.empty(), updateDoc);
		//user.updateMany(Filters.empty(), Updates.pull("comments", ""));
		
		
		
		filterDoc = Filters.eq("_id", 7);
		updateDoc = Updates.combine(Updates.push("comments", 
				new Document().append("title", "")
				.append("text", "")
				.append("group", "historical novel")
				.append("date", new Date())),
				Updates.inc("total_comments", 1));
		//user.updateOne(filterDoc, updateDoc);
		
		filterDoc = Filters.eq("_id", 7);
		updateDoc = Updates.combine(Updates.push("comments", 
				new Document().append("title", "")
				.append("text", "")
				.append("group", "basketball")
				.append("date", new Date())),
				Updates.inc("total_comments", 1));
		//user.updateOne(filterDoc, updateDoc);
		
		
		filterDoc = Filters.and(Filters.eq("groups", "historical novel"), Filters.gt("age", 25));
		projDoc = new Document()
				.append("name", 1)
				.append("surname", 1);
		res = user.find(filterDoc).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));
		
		filterDoc = Filters.nor(Filters.size("groups", 0), Filters.size("groups", 1));
		projDoc = new Document()
				.append("name", 1)
				.append("surname", 1)
				.append("_id", 0);
		res = user.find(filterDoc).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));
		
		filterDoc = Filters.all("groups", Arrays.asList("theatre", "historical novel"));
		projDoc = new Document()
				.append("name", 1)
				.append("surname", 1)
				.append("_id", 0)
				.append("groups", 1);
		res = user.find(filterDoc).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));
		
		filterDoc = Filters.exists("comments");
		projDoc = new Document()
				.append("name", 1)
				.append("surname", 1)
				.append("_id", 0);
		res = user.find(filterDoc).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));

		filterDoc = Filters.and( Filters.eq("adress.town", "Torrente"), Filters.eq("followers", 0));
		projDoc = new Document()
				.append("name", 1)
				.append("_id", 0);
		res = company.find(filterDoc).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));
		
		filterDoc = Filters.and( Filters.eq("adress.town", "Torrente"), Filters.gt("followers", 5));
		projDoc = new Document()
				.append("name", 1)
				.append("_id", 0);
		res = company.find(filterDoc).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));
	}
	
	
}
