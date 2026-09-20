package mongoExample;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class NewMflix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient client = MongoClients.create();
		MongoDatabase db = client.getDatabase("newMflix");
		
		MongoCollection<Document> movies = db.getCollection("movies");
		
		Document newMovie = new Document()
				.append("title", "Lord of Rings")
				.append("genres", Arrays.asList("Fantasy", "Adventure"))
				.append("type", "Movie")
				.append("rated", "+13")
				.append("year", 2001)
				.append("director", "Peter Jackson")
				.append("cast", Arrays.asList("Elijah Wood, Ian McKellen, Liv Tyler, Viggo Mortesen"));
		
		movies.insertOne(newMovie);
		
		newMovie = new Document()
				.append("title", "Matrix")
				.append("genres", Arrays.asList("Sci-fi", "Cyberpunk"))
				.append("type", "Movie")
				.append("rated", "+18")
				.append("year", 1999)
				.append("director", "The Wachowskis")
				.append("cast", Arrays.asList("Keanu reeves, Laurence Fishburne, Carrie-Anne Moss"));
		movies.insertOne(newMovie);
		
		newMovie = new Document()
				.append("title", "Game of Thrones")
				.append("genres", Arrays.asList("Fantasy", "Intrigue"))
				.append("type", "Series")
				.append("rated", "+18")
				.append("year", 2011)
				.append("cast", Arrays.asList("Peter Dinklage, Lena Headey, Kit Harrington, Emilia Clarke"));
		movies.insertOne(newMovie);
		
		newMovie = new Document()
				.append("title", "Data Access: the Movie")
				.append("genres", Arrays.asList("Drama", "Horror"))
				.append("type", "Movie")
				.append("rated", "+18")
				.append("year", 2021)
				.append("director", "Xavier Ibáñez");
		movies.insertOne(newMovie);

}
}