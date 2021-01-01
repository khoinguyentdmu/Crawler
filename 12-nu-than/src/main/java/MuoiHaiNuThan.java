import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;

public class MuoiHaiNuThan {
    public static void main(String []args){
        try {
            for (int id = 1; id <= 330; id++)
            getTitleOfUrl(id);
        }
        catch (IOException exception){

        }
    }

    public static void getTitleOfUrl(int id) throws IOException {
        Document doc = Jsoup.connect("https://truyenfull.vn/12-nu-than/chuong-" + id).get();

        String content = doc.getElementById("chapter-c").text();
        String title = doc.getElementsByClass("chapter-title").text();

        try {
            FileWriter myWriter = new FileWriter("Chuong " + id + ".txt");
            myWriter.write(title + "\r\n\r\n");
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
