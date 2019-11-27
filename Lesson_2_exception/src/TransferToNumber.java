public class TransferToNumber {
    public static class MyArraySizeException extends Exception {
        MyArraySizeException(String msg) {
            super(msg);
        }
    }

    public static class MyArrayDataException extends Exception {
     public MyArrayDataException(String msg){
         super(msg);
     }
    }
    static int transfer(String number[][]) throws MyArraySizeException, MyArrayDataException {
        if ((number.length > 4) || (number[0].length > 4)) {
            throw new MyArraySizeException("Выход за границы");
        }
        int mas[][] = new int[number.length][number[0].length];
        int result = 0;
        int i = 0;
        int j = 0;
        try {
            for (i = 0; i < number.length; i++)
                for (j = 0; j < number[0].length; j++) {
                    mas[i][j] = Integer.parseInt(number[i][j]);
                    result = result + mas[i][j];
                }
        } catch (NumberFormatException e) {
            throw new MyArrayDataException("Элемент " + "["+i + "," + j+"]" + " не является числом");
        }





        return result;
    }


    public static void main(String args[]) throws Exception {
        String[][] exaple = {{"2", "a", "4", "5"}, {"6", "7", "8", "9"}, {"4", "5", "6", "6"}, {"1", "2", "3", "4"}};
        System.out.print(transfer(exaple));
    }
}
