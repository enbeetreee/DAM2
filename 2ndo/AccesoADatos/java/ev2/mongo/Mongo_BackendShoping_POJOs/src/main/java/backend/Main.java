package backend;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;

import backend.entity.Address;
import backend.entity.Article;
import backend.entity.Comment;
import backend.entity.User;

public class Main {
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		DataAPI.init();
		DataAPI.deleteUser(DataAPI.findUser(new ObjectId("6798905d25752d6767f50b55")));
		//findUserById();
		DataAPI.close();
	}

	

	public static int menu() {
		String s = "8. Update Adress\n9. Update Email\n10. Add Comment\n11. Delete Article\n12. Delete User\n";
		return intEx(s);
	}

	public static void insertArticle() {
		//String name, aux = "a";
		//double price;
		List<String> cat = new ArrayList<String>();
		/*System.out.print("Name: ");
		name = in.nextLine();
		price = doubleEx("Price: ");
		while (!aux.equals("")) {
			System.out.println("Add categories (empty input to stop): ");
			aux = in.nextLine();
			if (!aux.equals("")) {
				cat.add(aux);
			}
		}*/
		DataAPI.insertArticle(new Article("name", 2, cat));

	}

	public static void insertUser() {
		String name, email;
		Address address;
		System.out.print("Name: ");
		name = in.nextLine();
		System.out.print("Email: ");
		email = in.nextLine();
		System.out.println("Address: ");
		address = createAddress();
		DataAPI.insertUser(new User(name, email, address));

	}

	public static Address createAddress() {
		String street, city, country;
		int number;
		System.out.print("Street: ");
		street = in.nextLine();
		number = intEx("Number: ");
		System.out.print("City: ");
		city = in.nextLine();
		System.out.print("Country: ");
		country = in.nextLine();
		return new Address(street, number, city, country);
	}

	public static void findArtById() {
		Article art;
		ObjectId id = idEx("Article ID:");
		art = DataAPI.findArticle(id);
		if (art == null) {
			System.out.println("Article couldn't be found");
		} else
			System.out.println(art);
	}
	
	public static void printArtIterable(FindIterable<Article> art) {
		art = orderByPrice(art);
		for (Article article : art) {
			System.out.println(article);
		}	
	}
	public static void printUserIterable(FindIterable<User> users) {
		for (User user : users) {
			System.out.println(user);
		}	
	}
	
	
	public static void findArtByCat() {
		System.out.print("Category: ");
		String cat = in.nextLine();
		FindIterable<Article> art = DataAPI.findArticleByCategory(cat);
		printArtIterable(art);
	}
	public static void findArtByName() {
		System.out.print("Name: ");
		String name = in.nextLine();
		FindIterable<Article> art = DataAPI.findArticleByName(name);
		printArtIterable(art);
	}
	public static void findArtInRange() {
		double l = doubleEx("Min price: "), h = doubleEx("Max price: ");
		FindIterable<Article> art = DataAPI.findArticleInPriceRange(l, h);
		printArtIterable(art);
	}
	private static void findUserById() {
		User user;
		ObjectId id = idEx("User ID:");
		user = DataAPI.findUser(id);
		if (user == null) {
			System.out.println("User couldn't be found");
		} else
			System.out.println(user);
	
	}
	public static void findUserByCountry() {
		System.out.print("Country: ");
		String country = in.nextLine();
		FindIterable<User> user = DataAPI.findUserByCountry(country);
		printUserIterable(user);
		
	}
	public static FindIterable<Article> orderByPrice(FindIterable<Article> art) {
		
		System.out.println("Order Ascendent (asc) or Descendent (dsc):");
		if (in.nextLine().equals("dsc")) {
			return DataAPI.orderByPrice(art, false);
		}else {
			return DataAPI.orderByPrice(art, true);
		}
		
	}
	
	public static void updateAdress() {
		User user = DataAPI.findUser(idEx("id: "));

		DataAPI.updateAdress(user, createAddress());
	}
	public static void updateEmail() {
		User user = DataAPI.findUser(idEx("id: "));

		DataAPI.updateEmail(user, in.nextLine());
	}
	
	public static void addComment() {
		Article art = DataAPI.findArticle(new ObjectId("6798a36e98bf09398609f4e1"));

		DataAPI.addComment(art, new Comment(5, new ObjectId("6798905d25752d6767f50b55"), "comentariu"));
	}

	public static int intEx(String s) {
		while (true) {
			try {
				System.out.print(s);
				int n = in.nextInt();
				in.nextLine();
				return n;
			} catch (InputMismatchException e) {
				System.out.println("Error: " + e.getMessage());
				in.nextLine();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				in.nextLine();
			}
		}
	}

	public static ObjectId idEx(String s) {
		while (true) {
			try {
				System.out.print(s);
				return new ObjectId(in.nextLine());

			} catch (InputMismatchException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	public static double doubleEx(String s) {
		String aux;
		while (true) {
			try {
				System.out.print(s);
				aux = in.nextLine();
				return Double.parseDouble(aux);
			} catch (InputMismatchException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
