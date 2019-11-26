package existsprobarproxecto;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

/**
 *
 * @author Alba
 */
public class ExistsProbarProxecto {
//ruta librerias: /home/oracle/eXist-db/lib

    public static String driver = "org.exist.xmldb.DatabaseImpl";
    public static Collection col = null;

    private Collection conexion() {
        try {
            Class<?> cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
            String coleccion = "/db/cousas";
//        Collection col;
            String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
            col = DatabaseManager.getCollection(uri + coleccion, "admin", "oracle");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return col;
    }

    //inserir una nova <persona>  chamada 'alexia' no documento proba.xml
    public void inserirPersoaProbaXML(String nome) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update insert <PERSONA>" + nome + "</PERSONA> into /PLAY/PERSONAE";
            ResourceSet result = servicio.queryResource("proba.xml", cons);
        }

    }

    //inserir una nova <persona>  chamada 'sara' en todos os documentos da coleción 'cousas'
    public void inserirPersoaColeccion(String nome) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update insert <PERSONA>" + nome + "</PERSONA> into /PLAY/PERSONAE";
            ResourceSet result = servicio.query(cons);
        }
    }

    //modificar o <APELLIDO> do empregado que ten por <EMP_NO> o valor 7521 para que pase a apelidarse 'BIEITEZ'
    public void modificarApelidoNumEmp(int nEmp, String apelido) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update value /EMPLEADOS/EMP_ROW[EMP_NO=" + nEmp + "]/APELLIDO with '" + apelido + "'";
            ResourceSet result = servicio.queryResource("empleados.xml", cons);
        }
    }

    //eliminar o empregado que ten por <EMP_NO> o valor 7698
    public void eliminarEmpNum(int nEmp) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update delete /EMPLEADOS/EMP_ROW[EMP_NO=" + nEmp + "]";
            ResourceSet result = servicio.queryResource("empleados.xml", cons);
        }
    }

    //modificar a <persona> chamada 'pedro' para que pase a chamarse 'xoan' no documento proba2.xml
    public void modificarNomePersoaProba2(String nomInicial, String nomFinal) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update value /PLAY/PERSONAE/PERSONA[text()='" + nomInicial + "'] with '" + nomFinal + "'";
            ResourceSet result = servicio.queryResource("proba2.xml", cons);
        }

    }

    //modificar a <persona> chamada 'luis' para que pase a chamarse 'xulio' no documento proba.xml
    public void modificarNomePersoaProba(String nomInicial, String nomFinal) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update value /PLAY/PERSONAE/PERSONA[text()='" + nomInicial + "'] with '" + nomFinal + "'";
            ResourceSet result = servicio.queryResource("proba.xml", cons);
        }
    }

    //modificar a <persona> chamada 'xoan' para que pase a chamarse 'sara' en todos os documentos da coleción 'cousas'
    public void modificarNomePersoaColeccion(String nomInicial, String nomFinal) throws XMLDBException {
        try (Collection col = this.conexion()) {
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String cons = "update value /PLAY/PERSONAE/PERSONA[text()='" + nomInicial + "'] with '" + nomFinal + "'";
            ResourceSet result = servicio.query(cons);
        }
    }

    //modificar o <nome> 'ana' do <p id="2"> para que pase a chamarse 'xulia' no documento proba.xml
    public void modificarNomePersoaIdProba(int id, String nomInicial, String nomFinal) throws XMLDBException {
        try(Collection col = this.conexion()) {
        XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        String cons = "update value /PLAY/fm/p[@id='" + id + "']/nome[text()='" + nomInicial + "'] with '" + nomFinal + "'";
        ResourceSet result = servicio.queryResource("proba.xml", cons);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {

        ExistsProbarProxecto obx = new ExistsProbarProxecto();

        obx.inserirPersoaProbaXML("alexia");
        obx.inserirPersoaColeccion("sara");
        obx.modificarApelidoNumEmp(7521, "BIEITEZ");
        obx.eliminarEmpNum(7698);
        obx.modificarNomePersoaProba2("pedro", "xoan");
        obx.modificarNomePersoaProba("luis", "xulio");
        obx.modificarNomePersoaColeccion("xoan", "sara");
        obx.modificarNomePersoaIdProba(2, "ana", "xulia");
        

        /* //TODO HECHO EN EL MAIN
         Class<?> cl = Class.forName(driver);
         Database database = (Database) cl.newInstance();
         DatabaseManager.registerDatabase(database);
         String coleccion = "/db/cousas";
         Collection col;
         String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
         col = DatabaseManager.getCollection(uri + coleccion, "admin", "oracle");
         */
        
        /*
         //inserir una nova <persona>  chamada 'alexia' no documento proba.xml
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update insert <PERSONA>alexia</PERSONA> into /PLAY/PERSONAE";
         ResourceSet result = servicio.queryResource("proba.xml", cons);
         */
        
        /*
         //inserir una nova <persona>  chamada 'sara' en todos os documentos da coleción 'cousas'
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update insert <PERSONA>sara</PERSONA> into /PLAY/PERSONAE";
         ResourceSet result = servicio.query(cons);
         */
        
        /*
         //modificar o <APELLIDO> do empregado que ten por <EMP_NO> o valor 7521 para que pase a apelidarse 'BIEITEZ'
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update value /EMPLEADOS/EMP_ROW[EMP_NO=7521]/APELLIDO with 'BIEITEZ'";
         ResourceSet result = servicio.queryResource("empleados.xml", cons);
         */
        
        /*
         //eliminar o empregado que ten por <EMP_NO> o valor 7698
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update delete /EMPLEADOS/EMP_ROW[EMP_NO=7698]";
         ResourceSet result = servicio.queryResource("empleados.xml", cons);
         */
        
        /*
         //modificar a <persona> chamada 'pedro' para que pase a chamarse 'xoan' no documento proba2.xml
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update value /PLAY/PERSONAE/PERSONA[text()='pedro'] with 'xoan'";
         ResourceSet result = servicio.queryResource("proba2.xml", cons);
         */
        
        /*
         //modificar a <persona> chamada 'luis' para que pase a chamarse 'xulio' no documento proba.xml
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update value /PLAY/PERSONAE/PERSONA[text()='luis'] with 'xulio'";
         ResourceSet result = servicio.queryResource("proba.xml", cons);
         */
        
        /*
         //modificar a <persona> chamada 'xoan' para que pase a chamarse 'sara' en todos oo documentos da coleción 'cousas'
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update value /PLAY/PERSONAE/PERSONA[text()='xoan'] with 'sara'";
         ResourceSet result = servicio.query(cons);
         */
        
        /*
         //modificar o <nome> 'ana' do <p id="2"> para que pase a chamarse 'xulia' no documento proba.xml
         XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
         String cons = "update value /PLAY/fm/p[@id='2']/nome[text()='ana'] with 'xulia'";
         ResourceSet result = servicio.queryResource("proba.xml", cons);
         */
        //  col.close();
    }

}
