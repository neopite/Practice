import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ModifTranslater {
    final static ArrayList<ArrayList<String>> LIST = new ArrayList<>();
    final static HashMap<String, Integer> LIST_OF_LANGUAGES = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        PUT_VOCABULARS();
        String firstLanguage = "";
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Eng");
        lines.add("Rus");
        lines.add("Ua");
        Scanner write = new Scanner(System.in);
        System.out.println("Enter sentence: ");
        String sentence = write.nextLine();
        if (languageDetection(sentence, putAListOFLanguages(), LIST_OF_LANGUAGES).equalsIgnoreCase("Rus")) {
            firstLanguage = languageDetection(sentence, putAListOFLanguages(), LIST_OF_LANGUAGES);
        } else if (languageDetection(sentence, putAListOFLanguages(), LIST_OF_LANGUAGES).equalsIgnoreCase("Ua")) {
            firstLanguage = languageDetection(sentence, putAListOFLanguages(), LIST_OF_LANGUAGES);
        } else if (languageDetection(sentence, putAListOFLanguages(), LIST_OF_LANGUAGES).equalsIgnoreCase("Eng")) {
            firstLanguage = languageDetection(sentence, putAListOFLanguages(), LIST_OF_LANGUAGES);
        }
        System.out.println("Detected language : " + firstLanguage);
        System.out.println("To which Language translate");
        String secondLanguage = write.nextLine();
        if (!lines.contains(firstLanguage)) {
            System.out.println("Sorry,it can`t be translated ");
            System.exit(0);
        }
        while (true) {
            if (firstLanguage.equalsIgnoreCase("Eng")) {
                translateFromEnglish(sentence, secondLanguage);
                break;
            }
            if (secondLanguage.equalsIgnoreCase("Eng")) {
                translateToEnglish(secondLanguage, sentence);
                break;
            }
            if (secondLanguage.equalsIgnoreCase("Rus") || secondLanguage.equalsIgnoreCase("Ua")) {
                translateFromEnglish(translateToEnglish(firstLanguage, sentence), secondLanguage);
                break;
            }
        }

    }

    public static HashMap<String, String> loadDictinory(String file) throws FileNotFoundException {
        HashMap<String, String> vocabular = new HashMap<>();
        Scanner read = new Scanner(new FileReader(file));
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String[] lineArray = line.split("-");
            vocabular.put(lineArray[0], lineArray[1]);
        }
        return vocabular;
    }

    public static HashMap<String, String> loadDictinoryTo(String file) throws FileNotFoundException {
        HashMap<String, String> vocabular = new HashMap<>();
        Scanner read = new Scanner(new FileReader(file));
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String[] lineArray = line.split("-");
            vocabular.put(lineArray[1], lineArray[0]);
        }
        return vocabular;
    }

    public static String translateToEnglish(String lang, String sentence) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        switch (lang) {
            case "Eng":
                System.out.println("Which dictionary to use?");
                String dict = scanner.nextLine();
                for (String words : sentence.split(" ")) {
                    str += loadDictinory(dict).get(words) + " ";
                }
                System.out.println(str);
                break;
            default:
                for (String words : sentence.split(" ")) {
                    str += loadDictinory(lang).get(words) + " ";
                }
        }
        return str;
    }

    public static void translateFromEnglish(String str, String lang) throws FileNotFoundException {
        for (String line : str.split(" ")) {
            System.out.print(loadDictinoryTo(lang).get(line) + " ");
        }
    }

    public static ArrayList<String> listOfWords(String language) throws FileNotFoundException {
        ArrayList<String> listOFword = new ArrayList<>();
        Scanner read = new Scanner(new FileReader(language));
        if (language.equalsIgnoreCase("Eng")) {
            while (read.hasNextLine()) {
                String line = read.nextLine();
                listOFword.add(line);
            }
        } else if (language.equalsIgnoreCase("Ua")) {
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] array = line.split("-");
                listOFword.add(array[0]);
            }
        } else if (language.equalsIgnoreCase("Rus")) {
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] array = line.split("-");
                listOFword.add(array[0]);
            }
        }
        return listOFword;
    }

    public static String languageDetection(String sentence, HashMap<Integer, String> numbersOfLanguages,
                                           HashMap<String, Integer> list) throws FileNotFoundException {
        for (String word : sentence.split(" ")) {
            for (int x = 0; x < LIST.size(); x++) {
                for (int z = 0; z < findSizeOfVocab(numbersOfLanguages.get(x)); z++) {
                    if (word.equalsIgnoreCase((LIST.get(x).get(z)))) {
                        findLanguageForTranslation(putAListOFLanguages(), x, "Rus", LIST_OF_LANGUAGES);
                        findLanguageForTranslation(putAListOFLanguages(), x, "Eng", LIST_OF_LANGUAGES);
                        findLanguageForTranslation(putAListOFLanguages(), x, "Ua", LIST_OF_LANGUAGES);
                    }
                }

            }
        }
        String language = "";
        int maxValueInMap = (Collections.max(LIST_OF_LANGUAGES.values()));
        for (Map.Entry<String, Integer> entry : LIST_OF_LANGUAGES.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                language = entry.getKey();
            }
        }

        return language;

    }

    public static int findSizeOfVocab(String language) throws FileNotFoundException {
        int count = 0;
        File file = new File(language);
        Scanner read = new Scanner(new FileReader(file));
        while (read.hasNext()) {
            String line = read.nextLine();
            count++;
        }
        return count;
    }

    public final static void PUT_VOCABULARS() throws FileNotFoundException {
        final ArrayList<String> ENG = listOfWords("Eng");
        final ArrayList<String> RUS = listOfWords("Rus");
        final ArrayList<String> UA = listOfWords("Ua");
        LIST.add(0, ENG);
        LIST.add(1, RUS);
        LIST.add(2, UA);
        LIST_OF_LANGUAGES.put("Rus", 0);
        LIST_OF_LANGUAGES.put("Eng", 0);
        LIST_OF_LANGUAGES.put("Ua", 0);

    }

    public static HashMap<Integer, String> putAListOFLanguages() {
        HashMap<Integer, String> numbersOfLanguages = new HashMap<>();
        numbersOfLanguages.put(0, "Eng");
        numbersOfLanguages.put(1, "Rus");
        numbersOfLanguages.put(2, "Ua");
        return numbersOfLanguages;
    }

    public static HashMap<String, Integer> findLanguageForTranslation(HashMap<Integer, String> map, int number,   //Не знаю,но думаю что я немного наблизился к тому что ты говорил(но это не точно)
                                                                      String comparableLanguage, HashMap<String, Integer> list) {
        if (map.get(number).contains(comparableLanguage)) {
            int count = list.get(comparableLanguage);
            list.put(comparableLanguage, count + 1);
        }
        return list;
    }
}
