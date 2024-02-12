package org.example.mp3playerfx.model.playlist;
import org.example.mp3playerfx.model.song.SongIterator;
import org.example.mp3playerfx.model.song.Song;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class XMLPlaylistAdapter extends Playlist {

    private XMLPlaylist xmlPlaylist;
    private int currentIndex;

    public XMLPlaylistAdapter(XMLPlaylist xmlPlaylist) {
        this.xmlPlaylist = xmlPlaylist;
    }

    @Override
    public JSONArray readFromFile(File file) {
        JSONArray jsonArray = new JSONArray();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            for(int i=0;i<doc.getElementsByTagName("song").getLength();i++) {
                JSONObject jsonSong = new JSONObject();
                jsonSong.put("path",doc.getElementsByTagName("song").item(i).getFirstChild().getTextContent());
                jsonArray.add(jsonSong);

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Error reading xml");
        }
        return jsonArray;
    }

    @Override
    public void saveToFile(String path) {
       xmlPlaylist.saveToXML(path);
    }

    @Override
    public List<Song> getSongs() {
        return xmlPlaylist.getSongs();
    }

    @Override
    public void setSongs(List<Song> songs) {
        xmlPlaylist.setSongs(songs);
    }

    @Override
    public void addSong(Song song) {
        xmlPlaylist.getSongs().add(song);
    }

    @Override
    public SongIterator iterator() {
        return this;
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public void setCurrentIndex(int index) {
        currentIndex = index;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Song next() {
        currentIndex = (currentIndex+1)%xmlPlaylist.getSongs().size();
        return xmlPlaylist.getSongs().get(currentIndex);
    }

    @Override
    public Song previous() {
        if(currentIndex> 0) {
            currentIndex--;
        }
        else {
            currentIndex= xmlPlaylist.getSongs().size()-1;
        }
        return xmlPlaylist.getSongs().get(currentIndex);
    }
}
