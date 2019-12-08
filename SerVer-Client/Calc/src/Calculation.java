public class Calculation {
    final static int size=10;
    final static float [] a=new float[size];
    static final int n=3;
    static final int h = size / n;

    static long CalcInOnearr(int size,float arr []){ //метод для вычислений в одном массиве
        for(int i=0;i<size;i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for(int i=0;i<size;i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return System.currentTimeMillis() - a;
    }

    static long CalcInManyarr(int size,float arr []) throws InterruptedException { //метод для n подмассивов
        for(int i=0;i<size;i++) {
            arr[i] = 1; //заполнение главного массива
        }
        float[][] b = new float[n][]; //создание n подмассивов
        for(int i=0;i<n;i++) {
            if(i!=n-1) {
                b[i] = new float[h];
            }
            else {
                b[i]=new float[h+(size%n)]; //последний подмассив содержит элементы,которые не поместились
            }
        }
        long a = System.currentTimeMillis();
        for (int i=0;i<n;i++) {//заполнение подмассивов
            System.arraycopy(arr, i * h, b[i], 0, i != n - 1 ? h : h + (size % n));
        }
        for(int i=0;i<n;i++) { //создание n потоков
            int finalI = i;
            Thread t1=new Thread(new Runnable() {
                @Override
                public  void run() {
                    if(finalI!=n-1) {
                        for (int j = 0; j < h; j++) {
                            b[finalI][j] = (float) (b[finalI][j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                        }
                    }else {
                        for (int j = 0; j < h + size % n; j++) {
                            b[finalI][j] = (float) (b[finalI][j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                        }
                    }
                }
            });
            t1.start();
            t1.join();
        }
        for(int j=0;j<n;j++){
            System.arraycopy(b[j], 0, arr,j*h , j!=n-1? h : h+(size%n));// слияние подмассивов
        }
        return System.currentTimeMillis() - a;
    }
    public static void main (String []args){
        float [] res1=a;
        float [] res2=a;
      System.out.println(CalcInOnearr(size,res1));
        try {
            System.out.println(CalcInManyarr(size,res2));
        } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
}
