import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean showMenu = true;
        String command;
        Scanner cs = new Scanner(System.in);
        Catalog cat = new Catalog("catalog");
        cat.showCatalog();
        while (showMenu) {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("ГЛАВНОЕ МЕНЮ: Введите команду [dir/sort/properties/watch/exit]");
            System.out.println("-------------------------------------------------------------------------");
            command = cs.next();
            switch (command) {
                case "dir":
                    System.out.println("Введите имя новой дирректории: ");
                    String pathName = cs.next();
                    cat = new Catalog(pathName);
                    cat.showCatalog();
                    break;
                case "sort":
                    sort(cat.files);
                    break;
                case "properties":
                    showProperties(cat.files);
                    break;
                case "watch":
                    watchDocument(cat);
                    break;
                case "exit":
                    showMenu = false;
                    System.out.println("До свидания!");
                    break;
            }
        }
    }

    private static void showProperties(File [] files){
        for (File f : files) {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("File: " + f.getName() + " Размер: " + f.length() + " БАЙТ" + " Дата изменения: " + new Date (f.lastModified()));
        }
        System.out.println("-------------------------------------------------------------------------\n");
    }

    private static void watchDocument(Catalog catalog) throws IOException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите имя документа для просмотра:");
            String fileName = scanner.next();
            try {
                File f = new File(catalog.folder, fileName);
                Document document = new Document(f);
                documentNavigation(catalog, document);
            } catch (FileNotFoundException e) {
                System.out.println("Некорректно введено имя файла");

            }
    }

    private static void sort(File [] files) {
        ArrayList<File> arrayList;
        arrayList = new ArrayList<>();
        Collections.addAll(arrayList, files);
        String command;
        Scanner cs = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Выберите тип сортивки [size/date]");
        System.out.println("-------------------------------------------------------------------------");
        command = cs.next();
        switch (command) {
            case "size":
                arrayList.sort((f1, f2) -> Long.compare(f1.length(), f2.length()));
                break;
            case "date":
                arrayList.sort((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
                break;
            }
        for (int i = 0; i < arrayList.size() ; i++) {
            files [i] = arrayList.get(i);
        }
        showProperties(files);
    }

    private static void documentNavigation (Catalog catalog, Document document){
        boolean showMenu = true;
        String command;
        Scanner cs = new Scanner(System.in);
        int count = 1;
        Document.showPage(document, count);
        while (showMenu) {
            boolean noCorrectPage = true;
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("МЕНЮ НАВИГАЦИИ ПО ДОКУМЕНТУ: Введите команду [back/next/page/search/exit]");
            System.out.println("-------------------------------------------------------------------------");
            command = cs.next();
            switch (command) {
                case "back":
                    count = document.currentPage;
                    Document.showPage(document, count);
                    break;
                case "next":
                    count = document.currentPage + 2;
                    Document.showPage(document, count);
                    break;
                case "page":
                    while (noCorrectPage) {
                        System.out.println("Введите номер страницы");
                        count = cs.nextInt();
                        Document.showPage(document, count);
                        noCorrectPage = false;
                    }
                    break;
                case "search":
                    System.out.println("Введите слово для поиска");
                    String searchWord = cs.next();
                    ArrayList<Integer> searchPages = searchWord(document, searchWord);
                    searchNavigation(catalog, document, searchPages, searchWord);
                    break;
                case "exit":
                    showMenu = false;
                    break;
            }
        }
    }

    private static ArrayList<Integer> searchWord(Document document, String searchWord){
        ArrayList<Integer> pagesWithWord = new ArrayList<>();
        for (int j = 0; j < document.pages.size(); j++) {
            if(document.pages.get(j).contains(searchWord)){
                pagesWithWord.add(j + 1);
            }
        }
        System.out.println("Найдено " + pagesWithWord.size() + " страниц");
        if(pagesWithWord.size() != 0) {
            return pagesWithWord;
        }else {
            pagesWithWord.add(1);
            return pagesWithWord;
        }
    }

     static private void searchNavigation (Catalog catalog, Document document, ArrayList<Integer> pagesWithWord, String searchWord ){
        String command;
        String changeWord;
        Scanner cs = new Scanner(System.in);
        boolean showMenu = true;
        int count = 0;
        Document.showPage(document, pagesWithWord.get(count));
        while (showMenu) {
            System.out.println("----------------------------------------------------");
            System.out.println("МЕНЮ ПОИСКА: Введите команду [back/next/change/exit]");
            System.out.println("----------------------------------------------------");
            command = cs.next();
            switch (command) {
                case "back":
                    --count;
                    if (count < 0){
                        count = pagesWithWord.size() - 1;
                    }
                    Document.showPage(document, pagesWithWord.get(count));
                    break;
                case "next":
                    ++count;
                    if (count > pagesWithWord.size()- 1){
                        count = 0;
                    }
                    Document.showPage(document, pagesWithWord.get(count));
                    break;
                case "change":
                    System.out.println("Введите слово для замены:");
                    changeWord = cs.next();
                    System.out.println("Введите название файла, в который нужно записать:");
                    String fileName = cs.next();
                    Document.writeChangedDocument(catalog, document, searchWord, changeWord, fileName);
                    break;
                case "exit":
                    showMenu = false;
                    break;
            }
        }
    }
}






