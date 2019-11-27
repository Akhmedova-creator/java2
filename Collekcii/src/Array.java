import java.util.*;



public class Array {
    public static void main(String[] args) {
        Directory der = new Directory();
        //---------------- телефонный справочник
        der.add("Jenya", "+7912991233"); //добавление номера
        der.add("Fara", "+7925456654");
        der.add("Fara", "526060");
        System.out.println(der.getValue("Fara")); //вывод всех номеров
        //----------------------------------------

        //--Вывод не повторяющих городов и подсчет повторений
        ArrayList<String> al = new ArrayList<>();
        Map<String, Integer> hm = new HashMap<>();
        int res = 0;
        String s;
        al.add("Германия");
        al.add("Германия");
        al.add("Франция");
        al.add("Италия");
        al.add("Испания");
        al.add("Щвецария");
        al.add("Австрия");
        al.add("Россия");
        for (int i = 0; i < al.size(); i++) {
            s = al.get(i);
            hm.put(s, hm.get(s) == null ? 1 : hm.get(s) + 1); //подсчет дубликатов
        }
        System.out.println(hm);
    }
}