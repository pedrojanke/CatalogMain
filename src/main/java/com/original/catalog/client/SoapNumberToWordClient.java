package com.original.catalog.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SoapNumberToWordClient {

    public String execute(Float number) {
        try {
            URL url = new URL("https://www.dataaccess.com/webservicesserver/numberconversion.wso?op=NumberToWords");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conn.setDoOutput(true);

            String xmlInput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                    + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                    + "  <soap:Body>"
                    + "    <NumberToWords xmlns=\"http://www.dataaccess.com/webservicesserver/\">"
                    + "      <ubiNum>"+ number +"</ubiNum>"
                    + "    </NumberToWords>"
                    + "  </soap:Body>"
                    + "</soap:Envelope>";

            OutputStream os = conn.getOutputStream();
            os.write(xmlInput.getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(response.toString().getBytes()));

            NodeList nodeList = doc.getElementsByTagName("m:NumberToWordsResult");
            if (nodeList.getLength() > 0) {
                String result = nodeList.item(0).getTextContent();
                System.out.println("PriceInWords: " + result);
                
                return result;
            } else {
                System.out.println("Tag NumberToWordsResult não encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
