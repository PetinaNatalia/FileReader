import java.io.*;
import java.util.ArrayList;

class Document {

    ArrayList<String> pages;
    private File file;
    int currentPage = 1;

    Document(File f) throws IOException {
        this.pages = splitFile(f);
        this.file = f;
    }

    private ArrayList<String> splitFile(File f) throws IOException {
        int PageSize = 1024;       // предполагаю, что отобразить мы можем 1 КB
        ArrayList<String> buf = new ArrayList<>();
        byte[] buffer = new byte[PageSize];

        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            while ((bis.read(buffer)) > 0) {
                buf.add(new String(buffer));
            }
        }
        return buf;
    }

    static void showPage(Document document, int userPage){

        if (userPage < 1) {
            userPage = document.pages.size();
        }
        if (userPage > document.pages.size()){
            userPage = 1;
        }

            document.currentPage = userPage - 1;
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("ДОКУМЕНТ: " + document.file.getName()                                     );
            System.out.println("СТРАНИЦА " + (userPage) + " ИЗ " + document.pages.size()                  );
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("\n" + document.pages.get(document.currentPage) + "\n");
    }

    static void writeChangedDocument(Catalog catalog, Document document, String searchWord, String word, String fileName){

        try {
            FileWriter fw = new FileWriter(new File(catalog.folder, fileName));
            for (int i = 0; i < document.pages.size(); i++) {
                if (document.pages.get(i).contains(searchWord)) {
                    String change = document.pages.get(i).replace(searchWord, word);
                    fw.write(change + "\n");
                }else{
                   fw.write(document.pages.get(i) + "\n");
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
