package tps.tp4.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tps.tp4.modelo.Contribuinte;
import tps.tp4.modelo.Declaracao;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Locale;

public class ExportadorXML {
    public static void exportarNotaLiquidacao(Contribuinte c, Declaracao d, double resultado) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("notaLiquidacao");
            doc.appendChild(root);

            Element nif = doc.createElement("nif");
            nif.appendChild(doc.createTextNode(c.getNif()));
            root.appendChild(nif);

            Element nome = doc.createElement("nome");
            nome.appendChild(doc.createTextNode(c.getNome()));
            root.appendChild(nome);

            Element ano = doc.createElement("anoFiscal");
            ano.appendChild(doc.createTextNode(String.valueOf(d.getAnoFiscal())));
            root.appendChild(ano);

            Element resNode = doc.createElement("resultado");
            resNode.setAttribute("tipo", resultado > 0 ? "PAGAR" : "REEMBOLSO");
            resNode.appendChild(doc.createTextNode(String.format(Locale.US, "%.2f", Math.abs(resultado))));
            root.appendChild(resNode);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            String filename = "liquidacao_" + c.getNif() + "_" + d.getAnoFiscal() + ".xml";
            DOMSource source = new DOMSource(doc);
            StreamResult resultFile = new StreamResult(new File(filename));

            transformer.transform(source, resultFile);
            System.out.println("Sucesso: Nota de liquidação exportada para -> " + filename);

        } catch (Exception e) {
            System.out.println("Erro ao exportar o XML da liquidação: " + e.getMessage());
        }
    }
}