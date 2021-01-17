import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import org.jsoup.nodes.Document;

public class MuoiHaiNuThan {
    public static void main(String []args){
        try {
            for (int id = 316; id <= 785; id++)
            getTitleOfUrl(id);
        }
        catch (IOException exception){

        }
    }

    public static void getTitleOfUrl(int id) throws IOException {
        Document doc = Jsoup.connect("https://truyenfull.vn/100-cach-cung-vo-010620/chuong-" + id).get();

        if (doc.getElementById("chapter-c") == null) return;
        String content = doc.getElementById("chapter-c").text();
        String title = doc.getElementsByClass("chapter-title").text();

        try {
            //FileWriter myWriter = new FileWriter("H:\\ebook\\12-nu-than\\" + "Chuong " + id + ".txt", Charset.forName("UTF-8"));
            FileWriter myWriter = new FileWriter("Chuong " + id + ".txt", Charset.forName("UTF-8"));

            myWriter.write('\ufeff');
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
