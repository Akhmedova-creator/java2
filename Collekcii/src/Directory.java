import java.util.*;

public class Directory {
    Map<String, ArrayList<String>> personMap = new HashMap<>();

    protected void add(String login, String tel) {
        ArrayList<String> ai = new ArrayList<>();
        if (personMap.get(login) == null) {
            ai.add(tel);
            personMap.put(login, ai);
        } else {
            ai = personMap.get(login);
            ai.add(tel);
            personMap.put(login, ai);
        }
    }
    protected  ArrayList<String> getValue(String login) {
        if (personMap.containsKey(login)) {
            return personMap.getOrDefault(login,personMap.get(login));//получение номеров
        }
        else {
            return null;
        }
    }
}