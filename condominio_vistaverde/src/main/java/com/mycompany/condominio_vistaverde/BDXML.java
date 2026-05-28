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

                raiz.appendChild(doc.createElement("casas"));
                raiz.appendChild(doc.createElement("pagos"));

                Element configuracion = doc.createElement("configuracion");
                Element cuotaActual = doc.createElement("cuotaActual");
                cuotaActual.setTextContent("1500.00");
                configuracion.appendChild(cuotaActual);
                raiz.appendChild(configuracion);

                guardarDocumento(doc);
                return doc;
            }
            return builder.parse(archivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener XML: " + e.getMessage());
            e.printStackTrace();
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== REGISTRAR PROPIETARIO (ÚNICO MÉTODO) ====================
    public static void registrarPropietario(String numCasa, String nombre, String telefono, String correo) throws Exception {
        Document doc = obtenerDocumento();
        if (doc == null) throw new Exception("No se pudo cargar el documento XML");

        // Obtener o crear <casas>
        Element casasRaiz;
        NodeList listaNodosCasas = doc.getElementsByTagName("casas");
        if (listaNodosCasas.getLength() > 0) {
            casasRaiz = (Element) listaNodosCasas.item(0);
        } else {
            casasRaiz = doc.createElement("casas");
            doc.getDocumentElement().appendChild(casasRaiz);
        }

        // Normalizar nombre de casa
        String casaFormateada = numCasa.trim().startsWith("CASA") ? numCasa.trim() : "CASA " + numCasa.trim();

        // Verificar si ya existe propietario
        NodeList listaCasas = doc.getElementsByTagName("casa");
        for (int i = 0; i < listaCasas.getLength(); i++) {
            Element c = (Element) listaCasas.item(i);
            if (c.getAttribute("numero").equals(casaFormateada)) {
                if (c.getElementsByTagName("propietario").getLength() > 0) {
                    throw new Exception("La casa " + casaFormateada + " ya tiene un propietario asignado.");
                }
                casasRaiz.removeChild(c);
                break;
            }
        }

        // Crear nueva casa con propietario
        Element nuevaCasa = doc.createElement("casa");
        nuevaCasa.setAttribute("numero", casaFormateada);

        Element prop = doc.createElement("propietario");

        Element elNombre = doc.createElement("nombre");
        elNombre.setTextContent(nombre);

        Element elTel = doc.createElement("telefono");
        elTel.setTextContent(telefono);

        Element elCorreo = doc.createElement("correo");
        elCorreo.setTextContent(correo);

        prop.appendChild(elNombre);
        prop.appendChild(elTel);
        prop.appendChild(elCorreo);

        nuevaCasa.appendChild(prop);
        casasRaiz.appendChild(nuevaCasa);

        guardarDocumento(doc);
    }

    public static boolean existePago(String casa, String mes, String año) {
        try {
            Document doc = obtenerDocumento();
            if (doc == null) return false;

            NodeList listaPagos = doc.getElementsByTagName("pago");
            for (int i = 0; i < listaPagos.getLength(); i++) {
                Element pago = (Element) listaPagos.item(i);

                String casaXML = pago.getElementsByTagName("casa").item(0).getTextContent().trim();
                String mesXML = pago.getElementsByTagName("mes").item(0).getTextContent().trim();
                String añoXML = pago.getElementsByTagName("año").item(0).getTextContent().trim();

                if (casaXML.equals(casa) && mesXML.equals(mes) && añoXML.equals(año)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String obtenerCuotaActual() {
        try {
            Document doc = obtenerDocumento();
            if (doc == null) return "1500.00";

            NodeList lista = doc.getElementsByTagName("cuotaActual");
            if (lista.getLength() > 0) {
                return lista.item(0).getTextContent();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error obteniendo cuota: " + e.getMessage());
        }
        return "1500.00";
    }

    public static void actualizarCuota(String nuevaCuota) {
        try {
            Document doc = obtenerDocumento();
            if (doc == null) return;

            NodeList lista = doc.getElementsByTagName("cuotaActual");
            if (lista.getLength() > 0) {
                lista.item(0).setTextContent(nuevaCuota);
            } else {
                Element configuracion = doc.createElement("configuracion");
                Element cuotaActual = doc.createElement("cuotaActual");
                cuotaActual.setTextContent(nuevaCuota);
                configuracion.appendChild(cuotaActual);
                doc.getDocumentElement().appendChild(configuracion);
            }
            guardarDocumento(doc);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error actualizando cuota: " + e.getMessage());
        }
    }

    public static String[] obtenerDatosPropietario(String casaBuscada) {
        try {
            Document doc = obtenerDocumento();
            if (doc == null) return null;

            NodeList listaCasas = doc.getElementsByTagName("casa");

            // Normalizar
            if (!casaBuscada.startsWith("CASA ")) {
                casaBuscada = "CASA " + casaBuscada.trim();
            }

            for (int i = 0; i < listaCasas.getLength(); i++) {
                Element casaElem = (Element) listaCasas.item(i);
                String numero = casaElem.getAttribute("numero").trim();

                if (numero.equals(casaBuscada)) {
                    NodeList listaProp = casaElem.getElementsByTagName("propietario");
                    if (listaProp.getLength() > 0) {
                        Element prop = (Element) listaProp.item(0);
                        String nombre = prop.getElementsByTagName("nombre").item(0).getTextContent().trim();
                        String telefono = prop.getElementsByTagName("telefono").item(0).getTextContent().trim();
                        String correo = prop.getElementsByTagName("correo").item(0).getTextContent().trim();

                        return new String[]{nombre, telefono, correo};
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener datos del propietario: " + e.getMessage());
        }
        return null;
    }
    
    
  public static void registrarPago(String casa, String mes, String año, String cuota) {
    try {
        Document doc = obtenerDocumento();
        if (doc == null) {
            throw new Exception("No se pudo cargar el documento XML");
        }

        // Obtener o crear la sección <pagos>
        NodeList listaPagosRaiz = doc.getElementsByTagName("pagos");
        Element pagosRaiz;
        
        if (listaPagosRaiz.getLength() > 0) {
            pagosRaiz = (Element) listaPagosRaiz.item(0);
        } else {
            pagosRaiz = doc.createElement("pagos");
            doc.getDocumentElement().appendChild(pagosRaiz);
        }

        // Normalizar el nombre de la casa
        String casaFormateada = casa.trim().startsWith("CASA") ? casa.trim() : "CASA " + casa.trim();

        // Crear el elemento pago
        Element nuevoPago = doc.createElement("pago");

        Element elemCasa = doc.createElement("casa");
        elemCasa.setTextContent(casaFormateada);

        Element elemMes = doc.createElement("mes");
        elemMes.setTextContent(mes);

        Element elemAño = doc.createElement("año");
        elemAño.setTextContent(año);

        Element elemCuota = doc.createElement("cuota");
        elemCuota.setTextContent(cuota);

        // Agregar todo al pago
        nuevoPago.appendChild(elemCasa);
        nuevoPago.appendChild(elemMes);
        nuevoPago.appendChild(elemAño);
        nuevoPago.appendChild(elemCuota);

        // Agregar el pago a la lista
        pagosRaiz.appendChild(nuevoPago);

        // Guardar cambios
        guardarDocumento(doc);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al registrar el pago: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}  
    
}