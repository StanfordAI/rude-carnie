import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by aime1 on 3/13/19.
 */
public class Accuracy {
    public static void main(String[] args) throws Exception {

        /*

        aime1s-iMac:~ aime1$ find . -name "inception_v3_*"
        ./IdeaProjects/PlayGround/out/production/PlayGround/inception_v3_age_model_without_bank.txt
        ./IdeaProjects/PlayGround/out/production/PlayGround/inception_v3_gender_model_without_bank.txt
        ./IdeaProjects/PlayGround/resources/inception_v3_age_model_without_bank.txt
        ./IdeaProjects/PlayGround/resources/inception_v3_gender_model_without_bank.txt

         */

        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
        System.out.println("inception gender model");
        final String resourcesFolder = "./resources/";

//        File folder = new File(".");
//        File[] listOfFiles = folder.listFiles();
//        for(File file : listOfFiles){
//            System.out.println(file);
//        }


        final String inceptionV3GenderModelWithBankFileName = "8363.txt";
        final HashMap<String, Integer> inceptionV3GenderModelWithoutBank = new HashMap<>();
        int cnt = getGenderCount(resourcesFolder, inceptionV3GenderModelWithBankFileName, inceptionV3GenderModelWithoutBank, ",");
        System.out.println();
        System.out.println(inceptionV3GenderModelWithBankFileName + " has \t" + cnt + " lines.");
        System.out.println("hence, the mapping will have the size of..... \t" + inceptionV3GenderModelWithoutBank.size());

        final String manuallyLabelledBankGenderDataFileName = "manually_labelled_bank_gender_data.txt";
        final HashMap<String, Integer> manuallyLabelledBankGenderData = new HashMap<>();
        cnt = getGenderCount(resourcesFolder, manuallyLabelledBankGenderDataFileName, manuallyLabelledBankGenderData, " ");
        System.out.println();
        System.out.println(manuallyLabelledBankGenderDataFileName + " has \t" + cnt + " lines.");
        System.out.println("hence, the mapping will have the size of \t" + manuallyLabelledBankGenderData.size());

        int totalPredict = 0;
        int rightPredict = 0;

        int tp = 0;
        int fp = 0;
        int fn = 0;
        int tn = 0;

        cnt = 0;
        for(String mKey : manuallyLabelledBankGenderData.keySet()){
            boolean isFound = false;
            String cur_iKey = "";
            String fileName = mKey.substring(mKey.lastIndexOf("/")+1);
            for(String iKey : inceptionV3GenderModelWithoutBank.keySet()){
//                if(fileName.equals("image22-M.jpg")){
//                    int a = 1;
//                    int b = a+1;
//                }
//                if(iKey.contains("22-M")){
//                    int a = 1;
//                    int b = a+1;
//                }
                cur_iKey = iKey;
                if(iKey.contains(fileName)){
                    //System.out.println((++cnt)+ "\t" + fileName);
                    totalPredict++;
                    isFound = true;

                    int predicted = inceptionV3GenderModelWithoutBank.get(iKey);
                    int actual    = manuallyLabelledBankGenderData.get(mKey);
                    if(predicted == actual){
                        rightPredict++;
                    }else{
                        System.out.println("file:"+fileName+",predicted:"+predicted+",actual:"+actual);
                    }

                    if(predicted == 1 && predicted == actual){
                        tp++;
                    } else if(predicted == 1 && predicted != actual){
                        fp++;
                    } else if(predicted == 0 && predicted == actual){
                        tn++;
                    } else if(predicted == 0 && predicted != actual){
                        fn++;
                    } else {
                        throw new Exception();
                    }

                    break;
                }
            }
            if(!isFound){
                throw new Exception();
            }
        }



        System.out.println();
        System.out.println("tp:"+tp);
        System.out.println("fp:"+fp);
        System.out.println("fn:"+fn);
        System.out.println("tn:"+tn);
        System.out.println("# of right predicts: " + rightPredict);
        System.out.println("total # of predicts: " + totalPredict);
        System.out.println("accuracy is " + (rightPredict*1.0/totalPredict));

        //---------------------------- now, age ...

        final String inceptionV3AgeModelWithBankFileName = "10841.txt";
        final HashMap<String, Integer> inceptionV3AgeModelWithoutBank = new HashMap<>();
        cnt = getAgeCount(resourcesFolder, inceptionV3AgeModelWithBankFileName, inceptionV3AgeModelWithoutBank, ",");
        System.out.println();
        System.out.println(inceptionV3AgeModelWithBankFileName + " has \t" + cnt + " lines.");
        System.out.println("hence, the mapping will have the size of..... \t" + inceptionV3AgeModelWithoutBank.size());

        final String manuallyLabelledBankAgeDataFileName = "manually_labelled_bank_age_data.txt";
        final HashMap<String, Integer> manuallyLabelledBankAgeData = new HashMap<>();
        cnt = getAgeCount(resourcesFolder, manuallyLabelledBankAgeDataFileName, manuallyLabelledBankAgeData, " ");
        System.out.println();
        System.out.println(manuallyLabelledBankAgeDataFileName + " has \t" + cnt + " lines.");
        System.out.println("hence, the mapping will have the size of \t" + manuallyLabelledBankAgeData.size());


        totalPredict = 0;
        rightPredict = 0;



        cnt = 0;
        for(String mKey : manuallyLabelledBankAgeData.keySet()){
            boolean isFound = false;
            String cur_iKey = "";
            String fileName = mKey.substring(mKey.lastIndexOf("/")+1);
            for(String iKey : inceptionV3AgeModelWithoutBank.keySet()){
//                if(fileName.equals("image22-M.jpg")){
//                    int a = 1;
//                    int b = a+1;
//                }
//                if(iKey.contains("22-M")){
//                    int a = 1;
//                    int b = a+1;
//                }
                cur_iKey = iKey;
                if(iKey.contains(fileName)){
                    //System.out.println((++cnt)+ "\t" + fileName);
                    totalPredict++;
                    isFound = true;

                    int predicted = inceptionV3AgeModelWithoutBank.get(iKey);
                    int actual    = manuallyLabelledBankAgeData.get(mKey);
                    if(plusMinus(predicted, actual, 2)){// try 1, 2, 3, ...
                        rightPredict++;
                    }else{
                        System.out.println("file:"+fileName+",predicted:"+predicted+",actual:"+actual);
                    }


                    break;
                }
            }
            if(!isFound){
                throw new Exception();
            }
        }

        System.out.println();
        System.out.println("# of right predicts: " + rightPredict);
        System.out.println("total # of predicts: " + totalPredict);
        System.out.println("accuracy is " + (rightPredict*1.0/totalPredict));
    }

    private static boolean plusMinus(int a, int b, int diff){
        return Math.abs(a - b) <= diff;
    }

    private static int getAgeCount(String resourcesFolder,
                                      String inceptionV3AgeModelWithBankFileName,
                                      HashMap<String, Integer> inceptionV3AgeModelWithoutBank,
                                      String delimit) throws Exception {
        String inceptionV3AgeModelWithoutBankTextFile = resourcesFolder + inceptionV3AgeModelWithBankFileName;
        File inceptionV3AgeModelWithoutBankFile = new File(inceptionV3AgeModelWithoutBankTextFile);
        BufferedReader br = new BufferedReader(new FileReader(inceptionV3AgeModelWithoutBankFile));
        String line;
        int cnt = 0;
        while((line = br.readLine()) != null){
            String[] lineItems = line.split(delimit);
//            for(String item : lineItems) {
//                System.out.print(item+"\t");
//            }
//            System.out.println(lineItems.length);
//            System.out.println(lineItems[0]);
//            System.out.println(lineItems[1]);
//            System.out.println(lineItems[2]);
            String ageRange = null;
            if(inceptionV3AgeModelWithBankFileName.startsWith("manually")){
                ageRange = lineItems[1];
            } else {
                ageRange = (lineItems[1] + "," + lineItems[2]);
            }
//            System.out.println(ageRange);
            int age = -1;
            if(ageRange.equals("(0, 2)")){
                age = 0;
            } else if(ageRange.equals("(4, 6)")){
                age = 1;
            } else if(ageRange.equals("(8, 12)")){
                age = 2;
            } else if(ageRange.equals("(15, 20)")){
                age = 3;
            } else if(ageRange.equals("(25, 32)")){
                age = 4;
            } else if(ageRange.equals("(38, 43)")){
                age = 5;
            } else if(ageRange.equals("(48, 53)")){
                age = 6;
            } else if(ageRange.equals("(60, 100)")){
                age = 7;
            } else if(ageRange.equals("0")) {
                age = 0;
            } else if(ageRange.equals("1")) {
                age = 1;
            } else if(ageRange.equals("2")) {
                age = 2;
            } else if(ageRange.equals("3")) {
                age = 3;
            } else if(ageRange.equals("4")) {
                age = 4;
            } else if(ageRange.equals("5")) {
                age = 5;
            } else if(ageRange.equals("6")) {
                age = 6;
            } else if(ageRange.equals("7")) {
                age = 7;
            } else {
                throw new Exception();
            }
            inceptionV3AgeModelWithoutBank.put(lineItems[0],age);

            cnt++;
        }
        return cnt;
    }

    private static int getGenderCount(String resourcesFolder,
                                      String inceptionV3GenderModelWithBankFileName,
                                      HashMap<String, Integer> inceptionV3GenderModelWithoutBank,
                                      String delimit) throws Exception {
        String inceptionV3GenderModelWithoutBankTextFile = resourcesFolder + inceptionV3GenderModelWithBankFileName;
        File inceptionV3GenderModelWithoutBankFile = new File(inceptionV3GenderModelWithoutBankTextFile);
        BufferedReader br = new BufferedReader(new FileReader(inceptionV3GenderModelWithoutBankFile));
        String line;
        int cnt = 0;
        while((line = br.readLine()) != null){
            String[] lineItems = line.split(delimit);
//            for(String item : lineItems) {
//                System.out.print(item+"\t");
//            }
//            System.out.println(lineItems.length);
//            System.out.println(lineItems[0]);
//            System.out.println(lineItems[1]);
//            System.out.println(lineItems[2]);
//            System.out.println();
            int gender = -1;
            if(lineItems[1].equals("F")){
                gender = 1;
            } else if(lineItems[1].equals("M")){
                gender = 0;
            } else if(lineItems[1].equals("1")) {
                gender = 1;
            } else if(lineItems[1].equals("0")) {
                gender = 0;
            } else {
                throw new Exception();
            }
            inceptionV3GenderModelWithoutBank.put(lineItems[0],gender);

            cnt++;
        }
        return cnt;
    }
}
