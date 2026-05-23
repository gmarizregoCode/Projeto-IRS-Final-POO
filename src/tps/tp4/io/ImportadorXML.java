package tps.tp4.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import tps.tp4.modelo.Despesa;
import tps.tp4.modelo.TipoDespesa;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportadorXML {
    public static List<Despesa> importarFaturas(String caminhoFicheiro) {
        List<Despesa> despesasLidas = new ArrayList<>();
        try {
            File ficheiroXML = new File(caminhoFicheiro);
            if (!ficheiroXML.exists()) {
                throw new IllegalArgumentException("O ficheiro '" + caminhoFicheiro + "' não foi encontrado.");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(ficheiroXML);
            doc.getDocumentElement().normalize();

            NodeList listaFaturas = doc.getElementsByTagName("fatura");
            for (int i = 0; i < listaFaturas.getLength(); i++) {
                Node noFatura = listaFaturas.item(i);
                if (noFatura.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) noFatura;
                    String tipoStr = elemento.getElementsByTagName("tipo").item(0).getTextContent().toUpperCase();
                    TipoDespesa tipo = TipoDespesa.valueOf(tipoStr);
                    double valor = Double.parseDouble(elemento.getElementsByTagName("valor").item(0).getTextContent());
                    String nifComerciante = elemento.getElementsByTagName("nifComerciante").item(0).getTextContent();
                    despesasLidas.add(new Despesa(tipo, valor, nifComerciante));
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro de leitura. Verifique as permissões do ficheiro.");
        } catch (SAXException e) {
            System.out.println("Erro: O ficheiro XML está mal formatado.");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao importar XML.");
        }
        return despesasLidas;
    }
}