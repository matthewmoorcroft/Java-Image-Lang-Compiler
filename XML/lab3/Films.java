import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import java.io.*;

/**
 *
 * @author jaf@it.uc3m.es
 * @author luiss@it.uc3m.es
 *
 */
public class Films {

    /* Este m�todo lee un fichero XML y devuelve el objeto DOM
     Document que lo representa. */

    private Document loadDocument(String fileName)
   				throws IOException, DOMException, LSException, java.lang.ClassNotFoundException,
   				java.lang.InstantiationException,java.lang.IllegalAccessException {

		DOMImplementationRegistry registry;
		DOMImplementation domImp;
		DOMImplementationLS domImpLS;
        LSParser parser;
        LSInput lsInp;

		registry=DOMImplementationRegistry.newInstance();
		domImp= registry.getDOMImplementation("LS 3.0");

        if (domImp==null) {
		    System.out.println("No se encuentra el m�dulo LS v3.0");
		    return null;
		}

        domImpLS= (DOMImplementationLS) domImp;
        parser=domImpLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, "http://www.w3.org/TR/REC-xml");

		lsInp= domImpLS.createLSInput();
		lsInp.setByteStream(new FileInputStream(fileName));
		return parser.parse(lsInp);
	}

    private String readInput(String prompt) throws IOException {
		System.out.print(prompt);
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		return stdin.readLine();
    }


    /* M�todo para volcar a un fichero el documento XML representado
       por un objeto Document */

    private void serialize(Document doc, File file) throws IOException, java.lang.ClassNotFoundException,
    			java.lang.InstantiationException, java.lang.IllegalAccessException {

		DOMImplementationRegistry registry;
		DOMImplementation domImp;
		DOMImplementationLS domls;

		registry=DOMImplementationRegistry.newInstance();
		domImp= registry.getDOMImplementation("LS 3.0");

        if (domImp==null) {
		    System.out.println("No se encuentra el m�dulo LS v3.0");
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

    public static void main(String[] args) throws Exception {

    	/** Definiciones:*/
    	Element raiz;
    	NodeList listaPeliculas;
    	NamedNodeMap listaAttr;
    	Node current, currentAttr;
		Attr name_att; //El nodo atributo que contiene el nombre
		String name_value; //l valor de dicho atributo


    	/** Fin definiciones*/

		Films films = new Films();
		if (args.length != 2) {
		    printHelp();
		    System.exit(1);
		}

		String file=args[1]; //guardamos el nombre del documento xml
		Document doc =films.loadDocument(file); //lo abrimos

		raiz=doc.getDocumentElement();
		listaPeliculas=raiz.getChildNodes();

		if ("-l".equals(args[0])) {

			for (int i=0; i<listaPeliculas.getLength();i++){
				current=listaPeliculas.item(i);
				if (current instanceof Element){
					name_att=((Element)current).getAttributeNode("Titulo");
					name_value=name_att.getValue();

					System.out.println(name_value);
				}
			}
		}
		else if ("-L".equals(args[0])) {

			String attr_name, attr_value;

			for (int i=0; i<listaPeliculas.getLength();i++){

				current=listaPeliculas.item(i);

				if (current instanceof Element){
					name_att=((Element)current).getAttributeNode("Titulo");
					name_value=name_att.getValue();

					System.out.println(name_value);

					listaAttr=((Element)current).getAttributes();
					for (int j=0; j<listaAttr.getLength();j++){



						//System.out.println("Hola estoy en la peli "+i+ "atributo" +j);


						currentAttr=listaAttr.item(j);
						if (currentAttr instanceof Attr){
							attr_name=((Attr)currentAttr).getName();
							attr_value=(String)((Attr)currentAttr).getValue();
							System.out.println("\t" + attr_name+": "+attr_value);

						}

					}

				}
		    }
		}
		else if ("-a".equals(args[0])) {

			current=raiz.cloneNode(false);
			//falta borrarle los attrs a este clonado y settearle el tipo a Elemento?

			//COMO SE DICE QUE ES UN ELEMENTO PELICULA?

			String titulo=null;
			String anyo=null;
			String duracion=null;

			while(titulo==null){
				titulo=films.readInput("Escriba el t�tulo de la pel�cula que desea guardar: ");
			}
			((Element)current).setAttribute("Titulo", titulo);
			anyo=films.readInput("Escriba el a�o de la pel�cula. Puede no escribirlo: ");
			if (anyo != null)
				((Element)current).setAttribute("Anyo", anyo);
			duracion=films.readInput("Escriba la duraci�n de la pel�cula. Puede no escribirlo: ");
			if (duracion != null)
				((Element)current).setAttribute("Duracion", duracion);
			((Node)raiz).appendChild(current);

			//A�adir ahora los opcionales, estos son: Director(es) y Actor(es), Genero y Nacionalidad.
			addTextNodes(current, raiz, "director", films, doc);
			addTextNodes(current, raiz, "actor", films, doc);
			addTextNodes(current, raiz, "genero", films, doc);
			addTextNodes(current, raiz, "nacionalidad", films, doc);


			films.serialize(doc, file) ;

		}
		else {
			printHelp();
		}
		System.exit(1);
    }

    private static void printHelp() {
		System.err.println("Error: wrong command-line arguments");
		System.err.println("java Films [-l|-L|-a] file");
    }

    private static void addTextNodes(Node parent, Node raiz, String tipo, Films films, Document doc) throws Exception{
    	String value=null;
    	Text aux;
    	do{
				value=null;
				value=films.readInput("Escriba un "+tipo+" de la pel�cula. Puede no escribirlo: ");


				if (value!=null){
				  //Y aqui peta:
					aux=doc.createTextNode(value);
					((Node)parent).appendChild(aux);

				}
				//else break;

			}while(value.equals("\n")); //supuestamente a�aden directores hasta que el usuario meta uno vac�o. pero no va wiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
    }
}
