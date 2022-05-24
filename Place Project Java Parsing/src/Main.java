/** This program allowed me to process the /r/place 2022 data, by first letting me create individual .txt files
 * divided up per minute of /r/place's uptime, then creating a .txt file for later use with a bot to determine most
 * popular colour per minute of /r/place
 * @author Elizabeth Norman
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class Main
{

    static ArrayList<Minute> minutes = new ArrayList<Minute>();

    public static void main (String [] args) throws FileNotFoundException
    {


        //generateFiles();

        //
        /* I don't like that I am calling close on each Minute object's writer by myself, but
         * such is Java, I suppose.
         */
        //for (int i = 0; i < minutes.size(); i ++)
        {
          //  minutes.get(i).closeWriter();
        }

        ColorProcessor colorProcessor = new ColorProcessor();
        colorProcessor.closeWriter();

    }

    /* I didn't know at the time there was an easier way to get the filenames, haha. You'll see it in the
     * ColorProcessor class. A learning experience.
     * generateFiles() acquires all of the compressed CSV files to then be processed
     */
    public static void generateFiles() throws FileNotFoundException {
        int maxFileCount = 78;

        String pathName = "G:/Place Project/Place Data/2022_place_canvas_history-0000000000";
        for (int i = 0; i <= maxFileCount; i++)
        {
            if (i < 10)
                pathName = pathName + "0";
            pathName = pathName + i;
            System.out.println(pathName);


            String pathNameTarget = pathName + ".csv";
            String pathNameSource = pathName + ".csv.gzip";
            System.out.println(pathName + "\n" + pathNameSource + "\n" + pathNameTarget);
            Path source = Paths.get(pathNameSource);
            Path target = Paths.get(pathNameTarget);

            if (Files.notExists(source))
            {
                System.err.printf("Path no exist");
                return;
            }

            try
            {
                decompressGzip(source, target);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            readCSVFile(pathNameTarget);

            pathName = "G:/Place Project/Place Data/2022_place_canvas_history-0000000000";

        }

    }

    /*  Decompress the files for reading
        Code from: https://mkyong.com/java/how-to-decompress-file-from-gzip-file/
     */
    public static void decompressGzip(Path source, Path target) throws IOException
    {
        try (GZIPInputStream gis = new GZIPInputStream(
                                    new FileInputStream(source.toFile()));
             FileOutputStream fos = new FileOutputStream(target.toFile()))
             {
                 byte[] buffer = new byte[1024];
                 int len;
                 while ((len = gis.read(buffer)) > 0)
                 {
                     fos.write(buffer, 0, len);
                 }
             }
    }

    /* This is the meat of the main class. Here we create a minute object for each of /r/place's uptime.
     */
    public static void readCSVFile(String fileName) throws FileNotFoundException
    {

        Scanner sc = new Scanner(new File(fileName));

        sc.next(); //skip first line

        String date, time, data;

        String minute = "";
        String color = "";
        while(sc.hasNext())
        {
            date = sc.next();
            time = sc.next();
            data = sc.next();
            minute = time.substring(0,5);
            minute = minute.replace(':','-');
            color = data.substring(94,100);
            boolean hrExists = false;
            int index = -1;

            for (int j = 0; j < minutes.size(); j ++)
            {
                if (minute.equals(minutes.get(j).getMinute()) && date.equals((minutes.get(j).getDate())))
                {
                    hrExists = true;
                    index = j;
                    break;
                }
            }
            if (hrExists)
            {
                minutes.get(index).writeToFile(date+" " + time + " " + color);
            }
            else
            {
                Minute minTemp = new Minute(date, minute);
                minutes.add(minTemp);
                minutes.get(minutes.size()-1).writeToFile(date + " " + time + " " + color);
            }

        }

        sc.close();


    }



}
