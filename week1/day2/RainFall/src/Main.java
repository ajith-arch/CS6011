//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.* ;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class RainData {
    private String city ;
    private List<RainfallRecord> records ;

    public RainData(String filename) throws FileNotFoundException {
        records = new ArrayList<>();

        readfile(filename) ;
    }

    private void readfile(String filename) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(filename));
        if(fileReader.hasNextLine()) {
            city = fileReader.nextLine();
        }
        while(fileReader.hasNext()) {
            String month = fileReader.next();
            int year = fileReader.nextInt();
            double rainfall = fileReader.nextDouble();
            records.add(new RainfallRecord(month, year, rainfall));
        }
        fileReader.close() ;
    }

    public double getAverageRainfall() {
        double totalRainfall = 0 ;
        for(RainfallRecord record : records)
        {
            totalRainfall += record.getRainfall() ;
        }
        return totalRainfall/records.size() ;
    }

    public Map<String , Double> getMonthAverageRainfall() {
        List<String> months = Arrays.asList("January" , "February" , "March" ,"April" ,"May" ,
                "June" , "July" , "August" , "September" , "October" , "November" , "December") ;
        Map<String , List<Double>> monthAverageRainfall = new HashMap<>();
        for(RainfallRecord record :records){
            monthAverageRainfall
                    .computeIfAbsent(record.getMonth(),k -> new ArrayList<>())
                    .add(record.getRainfall()) ;
        }

        Map<String , Double> averageRainfall = new LinkedHashMap<>() ;
        for(String month : months){
            List<Double> rainfalls = monthAverageRainfall.get(month) ;
            if(rainfalls != null) {
                double totalRainfall = 0;
                for (double rainfall : rainfalls) {
                    totalRainfall += rainfall;
                }
                averageRainfall.put(month, totalRainfall / rainfalls.size());
            }
        }
        return averageRainfall ;
    }

    public void writeToFile(String filename) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));

        Map<String , Double> monthAverageRainfall = getMonthAverageRainfall() ;
        for(Map.Entry<String , Double> entry : monthAverageRainfall.entrySet()){
            pw.printf("The average rainfall amount for %s is %.2f inches.%n", entry.getKey() , entry.getValue());
        }

        double overallRainfall = getAverageRainfall() ;
        pw.printf("The overall average rainfall amount is %.2f inches.%n", overallRainfall);


        pw.close();
    }
}

class RainfallRecord {
    private String month ;
    private int year ;
    private double rainfall ;

    public RainfallRecord(String month, int year, double rainfall) {
        this.month = month ;
        this.year = year ;
        this.rainfall = rainfall ;
    }

    public String getMonth() {
        return month ;
    }

    public int getYear() {
        return year ;
    }

    public double getRainfall() {
        return rainfall ;
    }
}

public class Main {
    public static void main(String[] args) {
        try{

            RainData rainData = new RainData("/Users/ajithalphonse/MSD/CS6011/week1/day2/RainFall/src/Atlanta.txt") ;

            rainData.writeToFile("/Users/ajithalphonse/MSD/CS6011/week1/day2/RainFall/src/rainfall_result") ;

        } catch(FileNotFoundException e) {
            System.out.println("Error : File not found.");
        }
    }
}