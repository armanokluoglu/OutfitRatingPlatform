package model.data_access;

import model.domain.Outfit;
import model.domain.User;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class InputOutput {

    private List<User> users;
    private List<Outfit> outfits;

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

            entries.forEach(entry -> Outfit.parseJson((org.json.simple.JSONObject) entry,new UserRepository(this)));

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



    public void inputUsers() {
        List<User> users = new ArrayList<>();
        List<UserWithUsers> followersList = new ArrayList<>();
        List<UserWithUsers> followingsList = new ArrayList<>();
        List<List<UserWithCollection>> collectionsList = new ArrayList<>();
        try {
            File inputFile = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("User");

            Node nNode = nList.item(0);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            int length = nNode.getChildNodes().getLength();
            for(int i=0;i<length;i++){
                if (nNode!=null && nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int id = Integer.valueOf(eElement.getAttribute("id"));
                    String userName = eElement.getElementsByTagName("UserName").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("Password").item(0).getTextContent();

                    User user = new User(id,userName,password,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
                    Node followers = eElement.getElementsByTagName("Followers").item(0);
                    Node followings = eElement.getElementsByTagName("Followings").item(0);
                    Node collections = eElement.getElementsByTagName("Collections").item(0);

                    UserWithUsers followersObject = nodeToUserWithUsers(id,followers);
                    UserWithUsers followingsObject = nodeToUserWithUsers(id,followings);
                    List<UserWithCollection> userWithCollections = nodeToUserWithCollection(id,collections);

                    users.add(user);
                    followersList.add(followersObject);
                    followingsList.add(followingsObject);
                    collectionsList.add(userWithCollections);

                    nNode=nNode.getNextSibling().getNextSibling();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        matchUsersAndFollowers(users,followersList);
        matchUsersAndFollowings(users,followingsList);
        matchUsersAndCollections(users,collectionsList);
        this.users=users;
    }

    private UserWithUsers nodeToUserWithUsers(int userId,Node node){
        NodeList nList = node.getChildNodes();
        List<Integer> relatedUsers = new ArrayList<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.valueOf(eElement.getAttribute("id"));
                System.out.print(id + "  ");
                relatedUsers.add(id);
            }
        }
        return new UserWithUsers(userId,relatedUsers);

    }

    private List<UserWithCollection> nodeToUserWithCollection(int userId,Node node){
        NodeList nList = node.getChildNodes();
        List<UserWithCollection> userWithCollections = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            List<Integer> outfitIdsList = new ArrayList<>();
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.valueOf(eElement.getAttribute("id"));
                String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                String outfitIds = eElement.getElementsByTagName("OutfitIds").item(0).getTextContent();
                String[] values = outfitIds.split(" ");
                for(String s:values)
                    outfitIdsList.add(Integer.valueOf(s));
                UserWithCollection userWithCollection = new UserWithCollection(userId,id,outfitIdsList);
                userWithCollections.add(userWithCollection);
            }
        }
        return userWithCollections;

    }


    private class UserWithUsers{

        int userId;
        List<Integer> connectedUserIds;

        public UserWithUsers(int userId, List<Integer> connectedUserIds) {
            this.userId = userId;
            this.connectedUserIds = connectedUserIds;
        }
    }
    private class UserWithCollection{

        int userId;
        int collectionId;
        List<Integer> outfitIds;
        public UserWithCollection(int userId, int collectionId, List<Integer> outfitIds) {
            this.userId = userId;
            this.collectionId = collectionId;
            this.outfitIds = outfitIds;
        }
    }


    public void matchUsersAndFollowers( List<User> users, List<UserWithUsers> followersList){

    }
    public void matchUsersAndFollowings( List<User> users, List<UserWithUsers> followingsList){

    }
    public void matchUsersAndCollections( List<User> users, List<List<UserWithCollection>> lists){

    }
}