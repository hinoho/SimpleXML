package ru.skillbench.tasks.javax.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class SimpleXMLImpl implements SimpleXML {
    @Override
    public String createXML(String tagName, String textNode)  {
        String result = null;
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element node = document.createElement(tagName);
            node.appendChild(document.createTextNode(textNode));
            document.appendChild(node);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StringWriter writer = new StringWriter();
            DOMSource source = new DOMSource(document);

            transformer.transform(source, new StreamResult(writer));
            result = writer.toString();
        }catch (Exception e) {
        }
        return result;
    }

    @Override
    public String parseRootElement(InputStream xmlStream) throws SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setValidating(true);
        try {
            SAXParser saxParser = factory.newSAXParser();
            MyHandler myHandler = new MyHandler();
            saxParser.parse(new InputSource(xmlStream), myHandler);
            return myHandler.getRoot();
        }
        catch (ParserConfigurationException | IOException e){
        }
        return null;
    }

    public class MyHandler extends DefaultHandler{
        private List<String> tagNames = new ArrayList<>();
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            tagNames.add(qName);
        }

        public String getRoot(){
            return !tagNames.isEmpty() ? tagNames.get(0) : null;
        }
    }

}
