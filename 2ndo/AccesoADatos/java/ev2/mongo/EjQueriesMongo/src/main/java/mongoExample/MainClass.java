package mongoExample;

import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Updates.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class MainClass {

	public static void main(String[] args) {
		MongoClient client = MongoClients.create();
		/*MongoIterable<String> names = client.listDatabaseNames();
		String res="Available Databases: ";
		for (String string : names) {
			res +=string+"; ";
		}
		System.out.println(res);*/
		
		
		MongoDatabase db = client.getDatabase("mflix");
		/*System.out.println("\nDatabase Name: "+db.getName());
		MongoIterable<String> col = db.listCollectionNames();
		for (String string : col) {
			System.out.println(string);
		}*/
		
		
		MongoCollection<Document> movies = db.getCollection("movies");
		
		/*Document newMovie = new Document()
				.append("title", "Lord of Rings")
				.append("genres", Arrays.asList("Fantasy", "Adventure"))
				.append("year", 2001)
				.append("director", "Xavi");
		
		System.out.println("toString: "+newMovie.toString());
		System.out.println("toJson: "+newMovie.toJson());
		System.out.println("Titulo: "+newMovie.getString("title"));
		System.out.println(newMovie.containsKey("year")?"Contains year":"Doesn't contain year");
		
		Document sortDoc = new Document("year",1);
		FindIterable<Document> resIterable = movies.find().sort(sortDoc);
		
		Document projectDoc = new Document()
				.append("year", 1)
				.append("title", 1)
				.append("directors", 1)
				.append("_id", 0);
		
		resIterable = resIterable.skip(1).limit(5).projection(projectDoc);
		for (Document document : resIterable) {
			System.out.println(document.toJson());
		}*/
		
		//1
		System.out.println("___1___");
		Bson filterDoc = Filters.eq("title", "Jurassic World");
		FindIterable<Document> res = movies.find(filterDoc);
		System.out.println(res.first().get("year"));
		
		//2
		System.out.println("___2___");
		filterDoc = Filters.eq("year",2016);
		Bson projDoc = new Document()
				.append("year", 1)
				.append("title", 1)
				.append("_id", 0);
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//3
		System.out.println("___3___");
		filterDoc = Filters.in("year",Arrays.asList(2015,2016));
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//4
		System.out.println("___4___");
		filterDoc = Filters.type("year","string");
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//5
		System.out.println("___5___");
		filterDoc = Filters.exists("plot",false);
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//6
		System.out.println("___6___");
		filterDoc = Filters.eq("countries", "Spain");
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//7
		System.out.println("___7___");
		filterDoc = Filters.and(Filters.size("cast", 1), Filters.eq("genres", "Biography"));
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//8
		System.out.println("___8___");
		filterDoc = Filters.in("languages", Arrays.asList("English", "Spanish"));
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//9
		System.out.println("___9___");
		filterDoc = Filters.regex("directors","Spielberg");
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//10
		System.out.println("___10___");
		filterDoc = Filters.or(Filters.regex("directors", "Spielberg"), Filters.regex("directors", "Kubrick"));
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//11
		System.out.println("___11___");
		filterDoc = Filters.and(Filters.gt("awards.wins", 7), Filters.gte("imdb.rating", 9));
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		
		
		//UPDATES
		System.out.println("___Up1___");
		filterDoc = Filters.eq("title", "Titanic");
		Bson updateDoc = Updates.combine(Updates.set("mustsee2", true), Updates.push("countries", "Spain"));
		movies.updateMany(filterDoc, updateDoc);
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//2
		System.out.println("___Up2___");
		filterDoc = Filters.and( Filters.regex("title", "Rings"), Filters.exists("num_mflix_comments"));
		updateDoc = Updates.inc("num_mflix_comments", 50);
		movies.updateMany(filterDoc, updateDoc);
		res = movies.find(filterDoc).projection(projDoc).limit(5);
		res.forEach(r -> System.out.println(r.toJson()));
		//3
		System.out.println("___Up3___");
		filterDoc = Filters.and(Filters.regex("title", "Matrix"), Filters.eq("year",2003));
		updateDoc = Updates.combine(Updates.unset("poster"), Updates.rename("shortplot", "plot"));
		movies.updateOne(filterDoc, updateDoc);
		res = movies.find(filterDoc).limit(1).projection(projDoc);
		res.forEach(r -> System.out.println(r.toJson()));
		
		client.close();


	}

}
