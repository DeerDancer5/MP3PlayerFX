package org.example.mp3playerfx.model.playlist;

import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.Song;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class XMLPlaylist {

   private List <Song> songs;

   public XMLPlaylist() {
       this.songs = new ArrayList<>();
   }

    public void saveToXML() {
        System.out.println("tak");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("playlist");

            for(Song song: songs){
                Element songXMl= doc.createElement("song");
                rootElement.appendChild(songXMl);
                Element path= doc.createElement("path");
                path.appendChild(doc.createTextNode(song.getFile().getName()));
                songXMl.appendChild(path);
                System.out.println(song.getTitle());

            }
            doc.appendChild(rootElement);
            System.out.println(doc);
            try (FileOutputStream output =
                         new FileOutputStream("/home/student/Music/playlist.xml")) {
                writeXml(doc, output);
            } catch (IOException  | TransformerException e) {
                e.printStackTrace();
            }


        } catch (ParserConfigurationException e) {
            System.out.println("Error parsing xml");
            e.printStackTrace();
        }
    }

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);

    }


}
