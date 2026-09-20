package mongoExample;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongoExample.entity.Awards;
import mongoExample.entity.Movie;

public class Main {
	public static void main(String[] args) {
		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("mflix");
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),                
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		db = db.withCodecRegistry(pojoCodecRegistry);
		
		MongoCollection<Movie> movies = db.getCollection("movies", Movie.class);
	
		Movie newMovie = new Movie("titulo", 2025, Arrays.asList("ASDF"), Arrays.asList("genre"), new Awards(5,10, "text"));
		
		//movies.insertOne(newMovie);
		Movie m = movies.find().first();
		System.out.println("Pelicula: "+m);
		
		cliente.close();
	}
}
