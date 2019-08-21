import java.io.File;

class Catalog {
    File folder;
    File[] files;

    Catalog(String pathname) {
        folder = new File(pathname);
        if (!folder.exists()) {
            folder.mkdir();
        }
        this.files = folder.listFiles();
    }

    void showCatalog (){
        System.out.println("------------------------------------------------------------------------- ");
        System.out.println("СТРУКТУРА КОЛЛЕКЦИИ:"                                                      );
        System.out.println("--------------------------------------------------------------------------");
        if(files.length != 0) {
            for (File file : this.files) System.out.println(file.getName());
        } else {
            System.out.println("Пустая папка");
        }
        System.out.println("------------------------------------------------------------------------\n");
    }
}
