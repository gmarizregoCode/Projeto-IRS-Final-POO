import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportadorXML {

    public static List<Despesa> importarFaturas(String caminhoFicheiro) {
        List<Despesa> listaFaturas = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(caminhoFicheiro));
            doc.getDocumentElement().normalize(); // Limpa a formatação da árvore

            XPath xPath = XPathFactory.newInstance().newXPath();
            String expressao = "//fatura"; // Procura qualquer tag <fatura>
            NodeList nodeList = (NodeList) xPath.compile(expressao).evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) node;

                    String nif = elemento.getElementsByTagName("nif_comerciante").item(0).getTextContent();
                    String valorTexto = elemento.getElementsByTagName("valor").item(0).getTextContent();
                    String tipoTexto = elemento.getElementsByTagName("tipo").item(0).getTextContent();

                    double valorReal = Double.parseDouble(valorTexto);
                    TipoDespesa tipoReal = TipoDespesa.valueOf(tipoTexto);

                    Despesa novaDespesa = new Despesa(tipoReal, valorReal, nif);
                    listaFaturas.add(novaDespesa);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao tentar ler o ficheiro XML: " + e.getMessage());
        }

        return listaFaturas;
    }
}
