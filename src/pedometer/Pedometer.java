package pedometer;

import LRClass.LRclass;
import data.bean.State;
import data.bean.Step;
import tools.RandCreate;
import tools.TimeUtil;
import Kmeans.Kmeans;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Pedometer {

    int fileIndex=0;


    private static int count = 0;

    int IndexEnd = 90 * 2 + 2;

    private double valueArray[] = new double[IndexEnd + (IndexEnd / 2)];

    private CountData countData;


    private int index = 0;

    private String TAG = "in the PedometerService";


    //add some var  help cal

    private static final double pinghRate = 0.45;




    double xDirec[] = new double[avgNumber];

    boolean isFull = false;

    int DirecIndex = 0;

    double sumXDirec = 0.0;


    private Step Totalstep;

    double calcuArray[] = new double[IndexEnd];

    private State state;


    private SimpleDateFormat df;
    private static final int avgNumber = 8;

    public static boolean isRunning = false;

    public static boolean isFileEnd=false;
//    static String  fileName = "zhangqing.txt";
//static String  fileName = "shijie-chenkun.txt";
//static String  fileName = "chenkun.txt";

//    static String  fileName = "zhang-440.txt";
//static String  fileName = "zhang-900.txt";
//static String  fileName = "zhao-380.txt";
//    static String fileName="zhou.txt";
//static String  fileName = "440.txt";

    static String  fileName = "DataNow633-656.txt";

    public static void main(String[]args){

        isRunning=true;

        Pedometer pd=new Pedometer();
        pd.onCreate();
        try{

            Scanner sc=new Scanner(new File(fileName));
            while(sc.hasNext()) {
                double[] valueArray = pd.readOneRowData(sc, 5);
                pd.onSensorChanged(valueArray);

            }

            sc.close();

            System.out.println(pd.getCount());

        }catch (Exception e){
            e.printStackTrace();
        }

pd.onDestroy();
    }


    ExecutorService countPool = Executors.newFixedThreadPool(4);



    /**
     * smooth data with specific rate
     */




    private double pinghua(double valueBefore, double valueNow) {

        return valueBefore * (1 - pinghRate) + valueNow * pinghRate;


    }

//    private static final int avgNumber = 4;
//    double xDirec[] = new double[avgNumber];
//    double sumXDirec = 0.0;
//    int DirecIndex = 0;

    /**
     * add element to array 平滑前的累加
     */
    private void addOfDirec(double x) {

        if (isFull) {
            sumXDirec -= xDirec[DirecIndex];
        }
        xDirec[DirecIndex] = x;

        sumXDirec += xDirec[DirecIndex];

        DirecIndex++;

        if (DirecIndex == avgNumber) isFull = true;

        DirecIndex = DirecIndex % avgNumber;
    }
//    if (!isFull) return;

    //获取平滑后数据

//    gravity = sumXDirec / avgNumber;


    /**
     * add the count to plus 1
     */

    public synchronized int addCount(int length, int type) {

        if (type == 1 || type == 2) {
            Totalstep.setStep_in_hand(Totalstep.getStep_in_hand() + length);
        } else if (type == 3) {

            if (Totalstep.getStep_in_hand() >= Totalstep.getStep_pocket())
                Totalstep.setStep_in_hand(Totalstep.getStep_in_hand() + length);
            else
                Totalstep.setStep_pocket(Totalstep.getStep_pocket() + length);

        } else if (type == 4) {

            Totalstep.setStep_pocket(Totalstep.getStep_pocket() + length);

        } else if (type == 5)
            Totalstep.setStep_in_run(Totalstep.getStep_in_run() + length);

        Totalstep.setTotal_step(Totalstep.getTotal_step() + length);

        count += length;
        return count;

    }

    public synchronized int getCount() {

        return Totalstep.getTotal_step();


    }


    public void onCreate() {


        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Totalstep = new Step();

        Step step = new Step();
        Calendar calendar = TimeUtil.getNowCalendar();
        step.setDate(TimeUtil.formatCalendar(calendar));
        state = new State(calendar, step);
        countData=new CountData(IndexEnd*3,0,0);


    }


    public void onDestroy() {


        countPool.shutdown();
//            cleanTotalStep();
//            cleanState();

    }

    public double[] readOneRowData(Scanner sc, int number) {



        double[] value = new double[number];

        try {
            for(int i=0;i<number;++i){

                    if(sc.hasNext()){

                    }
                value[i]=sc.nextDouble();

//                System.out.print( value[i]+"::::");
            }
//            System.out.println( "::1::"+value[0]+":::"+value[1]+":::"+value[2]+":::"+value[3]+":::"+value[4]);


        } catch (Exception e) {

            e.printStackTrace();

        }
        return value;

    }

    public void onSensorChanged(double values[]) {


        if (!isRunning) return;

        String message = "";
//        String message1 = "";

        double x = values[0];
        double y = values[1];
        double z = values[2];

        //求取和加速度
        double gravity = Math.sqrt(x * x + y * y + z * z);

        message += x + "  ";
        message += y + "  ";
        message += z + "  ";
        message += gravity + "  ";
//        message1 += gravity + " ";


        //进行数据平滑

        addOfDirec(gravity);

        if (!isFull) return;

        //获取平滑后数据

        gravity = sumXDirec / avgNumber;

        message += gravity + "  ";
        message += df.format(new Date());
        synchronized (this) {
            valueArray[index++] = gravity;
        }

        if (index == IndexEnd) {


            synchronized (this) {
                System.arraycopy(valueArray, 0, calcuArray, 0, IndexEnd);

//                System.arraycopy(valueArray, IndexEnd - 2, valueArray, 0, index - (IndexEnd - 2));
//
//                index = index - (IndexEnd - 2);

                index=index-IndexEnd;

            }

//            CalculateStep calc = new CalculateStep(calcuArray);
////                calc.start();
//            countPool.execute(calc);



            stepCount(calcuArray);


        }

//        System.out.println("Service", df.format(new Date())+"\n");

        index = index % (IndexEnd * 2);
//
//                Thread t = new WriteMessage(message + " \n", "DataNow1.txt");
//                countPool.execute(t);
//            t.start();

//        Thread t1 = new WriteMessage(message1 + " \n", "Data1.txt");
//
//        t1.start();


    }


    public void stepCount(double[] data) {



        String fileName=(fileIndex)+".txt";
        for(int i=0;i<data.length;++i){
        writeSD(data[i]+"\n",fileName);
        }

        int tempCount=getCount();

        double[] centerData = Kmeans.getKmeanCenter(data, 3, 500);
        Arrays.sort(centerData);
        double temp1 = centerData[2] - centerData[1];

        double topLevel = 0.0;


        int betweenMin = 4;
        int beteenMax = 40;

        int type = LRclass.getClass(centerData);

        System.out.println(type+"  "+centerData[0]+"  "+centerData[1]+"  "+centerData[2]);

        if (type == 1) {
            if (temp1 < 1.0) return;
            topLevel = centerData[1] + temp1 / 2;
        } else if (type == 2) {

            topLevel = centerData[1] + temp1 / 2;

        } else if (type == 3 || type == 4) {

            topLevel = centerData[1] + temp1 / 4;

            betweenMin = 6;

        } else if (type == 5) {

            topLevel = centerData[1];

            betweenMin = 8;
            beteenMax = 32;

        }

        double topIndex = 0;

//        System.out.println(fileName+" "+topLevel);

//        System.out.println((fileIndex++)+"  "+topLevel);
//        writeSD(topLevel+"\n","totalLevel.txt");
        countWithMean(data,topLevel,betweenMin,beteenMax,type);
//        writeSD(topLevel+"\n","totalLevel.txt");

//
//        for (int ite = 1; ite <= data.length - 2; ++ite) {
//
//
//            if ((data[ite] < topLevel) || (data[ite] <= data[ite + 1]) || (data[ite] <= data[ite - 1]))
//                continue;
//
//
//            if ((topIndex == 0) || ((ite -topIndex >= betweenMin) && (ite - topIndex < beteenMax))) {
//                addCount(1, type);
//                topIndex = ite;
//
//            } else {
//
//                if (ite - topIndex >= beteenMax)
//                    topIndex = ite;
//
//            }
//
//
//        }


//        writeSD((fileIndex)+"  "+(getCount()-tempCount)+"\n","totalCount.txt");

        fileIndex++;


        System.out.println("the total count is"+getCount());

    }

    class CountData{

        public double []data;
        public  int index;
        public  int totalLength;
        public CountData(int n,int index,int totalLength){

            data=new double[n];
            this.index=index;
            this.totalLength=totalLength;
        }

        public void  copyData(double[] srcData,int start,int length){
            synchronized (this){
            System.arraycopy(srcData,start,data,totalLength,length);
            totalLength=totalLength+length;
            }

        }

        public void  moveData(){

            synchronized (this){
                System.arraycopy(data,index,data,0,totalLength-index);
                totalLength=totalLength-index;
                index=0;
            }

        }


    }


    public void countWithMean(double[] srcData,double topLevel,int betweenMin,int beteenMax,int type){


        countData.copyData(srcData,0,srcData.length);

        int count=0;
//        int ite=countData.index;
        double means=0.0;
        for(int i=0;i<countData.totalLength;++i){
            means+=countData.data[i];
        }
        means=means/countData.totalLength;


        while(countData.index<=countData.totalLength-2){

            int tempIndex=countData.index;

//            find smaller than mean

            while(countData.index<(countData.totalLength-2)&&countData.data[countData.index]<means){
                ++countData.index;
            }
            if(countData.index>=(countData.totalLength-2)){

                countData.index=tempIndex;
                break;
            }

            int bigIndex=countData.index;

//            find bigger than mean

            double maxOne=-1.0;
            while(countData.index<(countData.totalLength-2)&&countData.data[countData.index]>means){
                if(countData.data[countData.index]>maxOne){
                    maxOne=countData.data[countData.index];
                }
                ++countData.index;
            }

//            between tempIndex and ite is the peak of the wave

            if(countData.index>=(countData.totalLength-2)&&(countData.data[countData.index]>means)){
                countData.index=tempIndex;
                break;
            }

            if((maxOne>=topLevel)&&(countData.data[countData.index-1]>means)) {
                addCount(1,type);
                ++count;

            }



        }


        countData.moveData();


    }

    public void countWithMean1(double[] data,double topLevel,int betweenMin,int beteenMax,int type){

        double topIndex = 0;

        int count=0;
        int ite=1;
        double means=0.0;
        for(int i=0;i<data.length;++i){
            means+=data[i];
        }
        means=means/data.length;


        while(ite<=data.length-2){

            int tempIndex=ite;

//            find smaller than mean

                while(ite<(data.length-2)&&data[ite]<means){
                    ++ite;
                }
            if(ite>=(data.length-1)){

                ite=tempIndex;
                break;
            }

            int bigIndex=ite;

//            find bigger than mean

            double maxOne=-1.0;
            while(ite<(data.length-2)&&data[ite]>means){
                if(data[ite]>maxOne){
                    maxOne=data[ite];
                }
                ++ite;
            }

//            between tempIndex and ite is the peak of the wave

            if((maxOne>=topLevel)&&(data[ite-1]>means)) {
                addCount(1,type);
                ++ite;
            }


        }



    }


    class CalculateStep extends Thread {

        private double[] data;

        public CalculateStep(double[] data) {
            this.data = data;

        }

        public void run() {

//            System.out.println("Service————>in the run", data.length + "");
            stepCount(data);


        }


    }

    //
    class WriteMessage extends Thread {

        String message = "";
        String fileName = "";

        public WriteMessage(String message, String fileName) {

            this.message += message;
            this.fileName += fileName;
        }

        public void run() {

            writeSD(message, fileName);


        }
    }


    /**
     * here is about write data into file
     */


    public static boolean writeSD(String data, String fileName) {

        try {


            FileOutputStream fos = new FileOutputStream(new File(fileName), true);
            fos.write(data.getBytes());
            fos.close();

            return true;


        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

            return false;

        }

    }


}


