/** The color processor class cycles through all of the generated minute files to find the top four color of each minute
 *  The top four colours are added into a final data file
 * @Author Elizabeth Norman, @spicedboi
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ColorProcessor
{

    ArrayList<String> fileNames;
    private File file;
    private PrintWriter writer;

    public ColorProcessor() throws FileNotFoundException
    {
        String finalFileName = "favourites.txt";
        file = new File(finalFileName);
        writer = new PrintWriter(file);

        fileNames = new ArrayList<String>();
        getFileNames();

    }

    public void getFileNames() throws FileNotFoundException
    {

        String[] pathNames;
        File f = new File("G:/Place Project/Hour Test");
        pathNames = f.list();

        for (String pathname : pathNames)
        {
            writer.print(pathname);
            addColors("G:/Place Project/Hour Test/" + pathname);
        }


    }

    public void closeWriter()
    {
        writer.close();
    }

    /*
     */
    public void addColors(String fileName) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(fileName));
        ArrayList<String> colors = new ArrayList<String>();

        while(sc.hasNextLine())
        {
            String data = sc.nextLine();
            String[] splitdata = data.split(" ");
            colors.add(splitdata[2]);
        }

        Collections.sort(colors);

        String temp = "";

        /* I know there are cleverer ways of doing this. My solution to find the top four was
        *  to find the top colour, remove it, and call the findFavourite method again
        */
        for (int i = 0; i < 4; i ++)
        {
            temp = findFavourite(colors);
            colors.removeAll(Collections.singleton(temp));
            writer.print(" " + temp);
        }
        writer.println();

    }

    /* Simply returns the most popular colour after it is found
     */
    public String findFavourite(ArrayList<String> colors)
    {

        int highestCount = 0;
        int tempCount = 1;
        String highestColor = "";
        String tempColor;

        for (int i = 1; i < colors.size() - 1 ; i++)
        {
            tempColor = colors.get(i);

            if(tempColor.equals("000000")) //ignoring black for more interesting results
                continue;

            if (colors.get(i) == colors.get(i-1))
            {
               tempCount++;
            }
            else
            {
                tempCount = 1;
            }
            if (tempCount > highestCount)
            {
                highestColor = tempColor;
                highestCount = tempCount;
            }
        }

        return highestColor;
    }

}
