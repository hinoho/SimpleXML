import java.util.Scanner;

public class Main extends SimpleXMLImpl{
    public static void main(String[] args) {
        SimpleXML simpleXML = new SimpleXMLImpl();
        System.out.println(simpleXML.createXML("tag", "taggy"));
    }

}
