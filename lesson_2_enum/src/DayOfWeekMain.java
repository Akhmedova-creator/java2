public class DayOfWeekMain {
    public  enum DayOfWeek {
         SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    public static String getWorkingHours(DayOfWeek s) {
        String result = "";
        switch (s.name()) {
            case ("MONDAY"):
                result = "40";
                break;
            case ("WEDNESDAY"):
                result = "32";
                break;
            case ("TUESDAY"):
                result = "24";
                break;
            case ("FRIDAY"):
                result = "16";
                break;
            case ("THURSDAY"):
                result = "8";
                break;
            case ("SATURDAY"):
                result = "Сегодня выходной!";
                break;
            case ("SUNDAY"):
                result = "Сегодня выходной!";
                break;
            default:
                result = "Неизвестный день недели";
                break;
        }
        return result;
    }
        public static void main ( final String[] args){
            System.out.println(getWorkingHours(DayOfWeek.SATURDAY));
        }

    }

