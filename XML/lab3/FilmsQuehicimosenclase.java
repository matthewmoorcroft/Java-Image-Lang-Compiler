import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS; 
import org.w3c.dom.ls.LSParser; 
import org.w3c.dom.ls.LSInput; 
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer; 
import java.io.*;
// cosas que hay que importar 
/**
 *
 * @author jaf@it.uc3m.es
 * @author luiss@it.uc3m.es
 *
 */
public class Films {

    /* Este método lee un fichero XML y devuelve el objeto DOM
     Document que lo representa. */

    static private Document loadDocument(String fileName) 
	throws IOException, DOMException, LSException, 
	       java.lang.ClassNotFoundException, 
	       java.lang.InstantiationException, 
	       java.lang.IllegalAccessException {

	DOMImplementationRegistry registry;
	DOMImplementation domImp;
	DOMImplementationLS domImpLS;
        LSParser parser;
        LSInput lsInp;

	registry=DOMImplementationRegistry.newInstance();
	domImp= registry.getDOMImplementation("LS 3.0");

    if (domImp==null) {
		System.out.println("No se encuentra el módulo LS v3.0");
	    return null;
	}

        domImpLS= (DOMImplementationLS) domImp;
        parser= 
	    domImpLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, 
				    "http://www.w3.org/TR/REC-xml");
					
		lsInp= domImpLS.createLSInput();
		lsInp.setByteStream(new FileInputStream(fileName));
		return parser.parse(lsInp);
    }

    private String readInput(String prompt) throws IOException {
		System.out.print(prompt);
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		return stdin.readLine();
    }


    /* Método para volcar a un fichero el documento XML representado
       por un objeto Document */

    private void serialize(Document doc, File file) 
	throws IOException, java.lang.ClassNotFoundException, 
	       java.lang.InstantiationException, 
	       java.lang.IllegalAccessException {

		DOMImplementationRegistry registry;
		DOMImplementation domImp;
		DOMImplementationLS domls;

		registry=DOMImplementationRegistry.newInstance();
		domImp= registry.getDOMImplementation("LS 3.0");

		if (domImp==null) {
			System.out.println("No se encuentra el módulo LS v3.0");
			return;
		}

		domls= (DOMImplementationLS) domImp;

		LSOutput output = domls.createLSOutput();
		FileOutputStream outStream = new FileOutputStream(file);
		output.setByteStream(outStream);
		output.setEncoding("UTF-8");
		LSSerializer serializer = domls.createLSSerializer();
		serializer.write(doc, output);
		outStream.close();
    }
/* Método es el main, depende del parametro que metamos junto al nombre del fichero va a 
		-l fichero.xml lista los titulos contenidos
		-L fichero.xml lista una informacion mas detallada de los titulos contenidos
		-a fichero.xml añade una nueva pelicula*/
    public static void main(String[] args)throws Exception {
		//primero creamos un objeto de tipo Pelicula
		Films films = new Films();
		Node nodo;
		NodeList hijos;
		NodeList hijospeli;
		String attrvalue;
		if (args.length != 2) {
			printHelp();
			System.exit(1);
		}
		String fileName = args[1]; //guardamos el nombre del fichero
		Document doc = loadDocument(fileName);//creamos un nuevo document con el fichero
		Element elementRaiz = doc.getDocumentElement(); //guardamos el elemento raiz
		//en este caso hacemos el metodo que liste los titulos de todas las peliculas contenidas
		if ("-l".equals(args[0])) {
		
			// Iteramos sobre sus hijos 
			hijos = elementRaiz.getChildNodes(); 
			for(int i=0;i<hijos.getLength();i++){ 
				nodo = hijos.item(i); 
				if (nodo instanceof Element){
					System.out.println( ((Element)nodo).getAttribute("Titulo") ); 
				} 
			}	
			
		}
		
		//en este caso hacemos el metodo que liste informacion mas detallada acerca de los titulos de todas las peliculas contenidas
		if ("-L".equals(args[0])) {
		
			// Iteramos sobre los hijos del elemento raiz 
			hijos = elementRaiz.getChildNodes(); 
			for(int i=0;i<hijos.getLength();i++){ 
				nodo = hijos.item(i);
				if (nodo instanceof Element){
					//titulo tienen OBLIGATORIAMENTE si el xml esta bien formado y cumple el DTD
					System.out.println( ((Element)nodo).getAttribute("Titulo") );
					//printeamos, si los hay, los otros atributos
					attrvalue=((Element)nodo).getAttribute("Duracion");
					if (attrvalue!=null)	System.out.println("\tDuracion: "+attrvalue);
					attrvalue=((Element)nodo).getAttribute("Año");
					if (attrvalue!=null)	System.out.println("\tAño: "+attrvalue);
					//nodos hijos. Puede haber mas de uno del mismo tipo
					hijospeli = ((Element)nodo).getChildNodes();
					//Iteramos sobre los hijos de this Pelicula
					for(int j=0;j<hijos.getLength();j++){
						
					}
				}
				//Ahora hay que imprimir los demas atributos de la peli, si es que los tiene, y sus hijos, si los tiene
			}
		}
		
		//en este caso hacemos el metodo que añade una nueva pelicula
		if ("-a".equals(args[0])) {//metodo append child!
			
		}
    }

    private static void printHelp() {
		System.err.println("Error: wrong command-line arguments");
		System.err.println("java Films [-l|-L|-a] file");
    }
}
