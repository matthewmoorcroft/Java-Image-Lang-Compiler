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
        String response;
        response = ordinaria("ord2015.xml","c1");
        System.out.println(response);
    }
}
