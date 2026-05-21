package com.mycompany.condominio_vistaverde;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BDXML {

    private static final String RUTA = "residencial.xml";

    public static Document obtenerDocumento() {
        try {
            File archivo = new File(RUTA);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            if (!archivo.exists()) {
                Document doc = builder.newDocument();

                Element raiz = doc.createElement("residencial");
                doc.appendChild(raiz);

                Element casas = doc.createElement("casas");
                raiz.appendChild(casas);

                Element pagos = doc.createElement("pagos");
                raiz.appendChild(pagos);
                Element configuracion = doc.createElement("configuracion");

Element cuotaActual = doc.createElement("cuotaActual");
cuotaActual.setTextContent("1500.00");

configuracion.appendChild(cuotaActual);

raiz.appendChild(configuracion);

                guardarDocumento(doc);
                return doc;
            }

            return builder.parse(archivo);

        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener XML: " + e.getMessage());
            return null;
        }
    }

    public static void guardarDocumento(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(RUTA));

            transformer.transform(source, result);

        } catch (IllegalArgumentException | TransformerException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar XML: " + e.getMessage());
        }
    }

public static boolean existePago(String casa, String mes, String año) {

    Document doc = obtenerDocumento();

    NodeList listaPagos =
            doc.getElementsByTagName("pago");

    for (int i = 0; i < listaPagos.getLength(); i++) {

        Element pago =
                (Element) listaPagos.item(i);

        String casaXML =
                pago.getElementsByTagName("casa")
                        .item(0).getTextContent();

        String mesXML =
                pago.getElementsByTagName("mes")
                        .item(0).getTextContent();

        String añoXML =
                pago.getElementsByTagName("año")
                        .item(0).getTextContent();

        if (casaXML.equals(casa)
                && mesXML.equals(mes)
                && añoXML.equals(año)) {

            return true;
        }
    }

    return false;
}
    public static void registrarPago(String casa, String mes, String año, String cuota) {
        Document doc = obtenerDocumento();

        Element raiz = doc.getDocumentElement();
        Element pagos = (Element) doc.getElementsByTagName("pagos").item(0);

        Element pago = doc.createElement("pago");

        Element casaElemento = doc.createElement("casa");
        casaElemento.setTextContent(casa);
        pago.appendChild(casaElemento);

        Element mesElemento = doc.createElement("mes");
        mesElemento.setTextContent(mes);
        pago.appendChild(mesElemento);

        Element añoElemento = doc.createElement("año");
        añoElemento.setTextContent(año);
        pago.appendChild(añoElemento);

        Element cuotaElemento = doc.createElement("cuota");
        cuotaElemento.setTextContent(cuota);
        pago.appendChild(cuotaElemento);

        pagos.appendChild(pago);

        guardarDocumento(doc);
    }
public static String obtenerCuotaActual() {

    try {

        Document doc = obtenerDocumento();

        NodeList lista =
                doc.getElementsByTagName("cuotaActual");

        if (lista.getLength() > 0) {

            return lista.item(0).getTextContent();
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,
                "Error obteniendo cuota: "
                + e.getMessage());
    }

    return "1500.00";
}


public static void actualizarCuota(String nuevaCuota) {

     try {

        Document doc = obtenerDocumento();

        NodeList lista =
                doc.getElementsByTagName("cuotaActual");

        // SI EXISTE -> ACTUALIZA
        if (lista.getLength() > 0) {

            lista.item(0).setTextContent(nuevaCuota);

        } else {

            // CREAR CONFIGURACION
            Element configuracion =
                    doc.createElement("configuracion");

            Element cuotaActual =
                    doc.createElement("cuotaActual");

            cuotaActual.setTextContent(nuevaCuota);

            configuracion.appendChild(cuotaActual);

            doc.getDocumentElement()
                    .appendChild(configuracion);
        }

        guardarDocumento(doc);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null,
                "Error actualizando cuota: "
                + e.getMessage());
    }
}


    public static void asignarDuenio(String casa, String duenio) {
        Document doc = obtenerDocumento();

        Element casas = (Element) doc.getElementsByTagName("casas").item(0);
        NodeList listaCasas = doc.getElementsByTagName("casa");

        boolean encontrada = false;

        for (int i = 0; i < listaCasas.getLength(); i++) {
            Element casaElemento = (Element) listaCasas.item(i);

            if (casaElemento.getAttribute("numero").equals(casa)) {
                NodeList duenios = casaElemento.getElementsByTagName("duenio");

                if (duenios.getLength() > 0) {
                    duenios.item(0).setTextContent(duenio);
                } else {
                    Element duenioElemento = doc.createElement("duenio");
                    duenioElemento.setTextContent(duenio);
                    casaElemento.appendChild(duenioElemento);
                }

                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            Element nuevaCasa = doc.createElement("casa");
            nuevaCasa.setAttribute("numero", casa);

            Element duenioElemento = doc.createElement("duenio");
            duenioElemento.setTextContent(duenio);

            nuevaCasa.appendChild(duenioElemento);
            casas.appendChild(nuevaCasa);
        }

        guardarDocumento(doc);
    }
}