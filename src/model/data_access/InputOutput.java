package model.data_access;

import model.domain.Outfit;
import model.domain.User;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class InputOutput {

    private static FileWriter file;

    public void outputOutfits(List<Outfit> outfits){
        List<JSONObject> allOutfitsJSON = new ArrayList<>();
        JSONObject outfitsJsonList = new JSONObject();
        for(Outfit outfit:outfits){
            allOutfitsJSON.add(outfit.toJSON());
        }
        outfitsJsonList.put("Outfits",allOutfitsJSON);
        try {
            file = new FileWriter("outfits.json");
            file.write(outfitsJsonList.toString());

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void inputOutfits() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("outfits.json")) {
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONObject outfits = (org.json.simple.JSONObject) obj;
            org.json.simple.JSONArray entries = (org.json.simple.JSONArray) outfits.get("Outfits");

            entries.forEach(entry -> Outfit.parseJson((org.json.simple.JSONObject) entry));

            String x = "";
//			List<Product> allProducts = new ArrayList<>();
//			for(Product product:products){
//				if(product instanceof Part)
//					allProducts.add(product);
//				if(product instanceof Assembly)
//					allProducts.addAll(((Assembly) product).getAllProductsSeperatly());
//			}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




    public void outputUsers(List<User> users){
        List<JSONObject> allUsersJSON = new ArrayList<>();
        for(User user:users){
            allUsersJSON.add(user.toJSON());
        }
        try {
            file = new FileWriter("users.json");
            file.write(allUsersJSON.toString());

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void xmlOutput(List<User> users) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement =
                    doc.createElementNS("https://www.journaldev.com/employee", "Employees");
            //append root element to document
            doc.appendChild(rootElement);

            //append first child element to root element
            for(User user:users)
                rootElement.appendChild(user.toXMLNode(doc));

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("users.xml"));

            //write data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Node userNode(Document doc, User user,String id, String name, String age, String role,
                                    String gender) {
        Element employee = doc.createElement("User");

        //set id attribute
        employee.setAttribute("id", String.valueOf(user.getId()));

        //create name element
        employee.appendChild(getUserElements(doc, employee, "Username", user.getUsername()));

        //create age element
        employee.appendChild(getUserElements(doc, employee, "Password", user.getPassword()));

        //create role element
        employee.appendChild(getUserElements(doc, employee, "role", role));

        //create gender element
        employee.appendChild(getUserElements(doc, employee, "gender", gender));

        return employee;
    }

    private static Node getUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }








}
