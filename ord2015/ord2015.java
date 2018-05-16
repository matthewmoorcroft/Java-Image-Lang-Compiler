import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class ord2015 {

  private static String ordinaria(Document doc, String referencia){
    Element el1,el2, docE1;
    NodeList nL, nL2;
    int i, j, s1, s2;
    Node n1, n2;
    docEl= doc.getDocumentElement();
    nL= docEl.getChildNodes();
    s1= nL.getLength();
    if (s1>0) {
     for (i=0; i<s1; i++) {
      n1= nL.item(i);
      if (n1.getNodeType() == Node.ELEMENT_NODE) {
       el1= (Element) n1;
       if (el1.getAttribute("id").equals(referencia)) {
        nL2= el1.getChildNodes();
        s2=nL2.getLength();
        for(j=0; j<s2; j++) {
         n2= nL2.item(j);
         if (n2.getNodeType() == Node.ELEMENT_NODE) {
          el2= (Element) n2;
          if (el2.getNodeName().equals("nombre")) {
           System.out.println("Encontrado");
           return el2.getFirstChild().getNodeValue();
          }
         }
        }
       }
      }
       }
    }
    return "";
  }

    public static void main(String[] args) {

      boolean valid = false;
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
          factory.setAttribute(
            "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");
          factory.setNamespaceAware(true);

      DocumentBuilder builder;
      try {
        builder = factory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
        e.printStackTrace();
      }
      try {
        ValidationErrorHandler handler = new ValidationErrorHandler();
        builder.setErrorHandler(handler);
        Document document = builder.parse(new InputSource(fileName));
        valid = !handler.errors;
      } catch (SAXException e) {
        valid = false;
      } catch (IOException e) {
        valid = false;
        e.printStackTrace();
      }


        String response;
        Document document = builder.parse(new InputSource("ord2015.xml"));
        response = ordinaria(document,"c1");
        System.out.println(response);
    }
}
