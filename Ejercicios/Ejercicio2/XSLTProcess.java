import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class XSLTProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			printHelp();
		}
		Source xslt = new StreamSource(new File(args[0]));
		Source xml = new StreamSource(new File(args[1]));
		Result result = new StreamResult(System.out);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer(xslt);
			transformer.transform(xml, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	private static void printHelp() {
		System.err.println("Error: wrong command-line arguments");
		System.err.println("java XSLTProcess xsltFile xmlFile");
	}
}
