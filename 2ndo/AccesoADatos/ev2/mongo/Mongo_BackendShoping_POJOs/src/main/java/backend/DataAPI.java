package backend;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import backend.entity.Address;
import backend.entity.Article;
import backend.entity.Comment;
import backend.entity.User;

public class DataAPI {
	private static MongoClient client;
	private static MongoDatabase db;

	public static void init() {
		client =MongoClients.create();
		db = client.getDatabase("act5_3");
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),                
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		db = db.withCodecRegistry(pojoCodecRegistry);

	}
	public static  void close() {
		client.close();
	}
	
	public static void insertArticle(Article art) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		articles.insertOne(art);
	}
	public static void insertUser(User us) {
		MongoCollection<User> users = db.getCollection("users", User.class);
		users.insertOne(us);
	}
	public static Article findArticle(ObjectId id) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		Bson filterDoc = Filters.eq("_id", id);
		return articles.find(filterDoc).first();
	}
	public static FindIterable<Article> findArticleByCategory(String cat){
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		Bson filterDoc = Filters.eq("categories", cat);
		return articles.find(filterDoc);
	}
	
	public static FindIterable<Article> findArticleByName(String name) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		Bson filterDoc = Filters.eq("name", name);
		return articles.find(filterDoc);
		
	}
	public static FindIterable<Article> findArticleInPriceRange(double low, double high) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		Bson filterDoc = Filters.and(Filters.gte("price",low), Filters.lte("price", high));
		return articles.find(filterDoc);
		
	}
	public static User findUser(ObjectId id) {
		MongoCollection<User> users = db.getCollection("users", User.class);
		Bson filterDoc = Filters.eq("_id", id);
		return users.find(filterDoc).first();
		
	}
	public static FindIterable<User> findUserByCountry(String country) {
		MongoCollection<User> users = db.getCollection("users", User.class);
		Bson filterDoc = Filters.eq("address.country", country);
		return users.find(filterDoc);
		
	}
	public static FindIterable<Article> orderByPrice(FindIterable<Article> arts, boolean asc) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		Document sortDoc = new Document().append("price", 1);
		return articles.find(Filters.empty()).sort(sortDoc);
		
	}
	public static void updateAdress(User us, Address ad) {
		MongoCollection<User> users = db.getCollection("users", User.class);
		us.setAddress(ad);
		users.replaceOne(Filters.eq("_id",us.getId()), us);
	}
	public static void updateEmail(User us, String email) {
		MongoCollection<User> users = db.getCollection("users", User.class);
		us.setEmail(email);
		users.replaceOne(Filters.eq("_id",us.getId()), us);
	}
	public static void addComment(Article art, Comment newCom) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		art.getComments().add(newCom);
		articles.replaceOne(Filters.eq("_id", art.getId()), art);
	}
	public static void deleteArticle(Article art) {
		MongoCollection<Article> articles = db.getCollection("articles", Article.class);
		articles.deleteOne(Filters.eq("_id",art.getId()));
		}
	public static void deleteUser(User us) {
		MongoCollection<User> users = db.getCollection("users", User.class);
		users.deleteOne(Filters.eq("_id",us.getId()));
	}
}
