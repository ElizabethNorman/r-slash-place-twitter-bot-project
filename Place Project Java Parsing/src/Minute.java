/** The minute class is responsible for writing to each of the individual minute slot's file
 * @Author Elizabeth Norman
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Minute
{

    private String date;
    private String minute;
    private String fileName;
    private File file;
    private PrintWriter writer;

    public Minute(String date, String minute) throws FileNotFoundException
    {
        this.date = date;
        this.minute= minute;
        fileName = "G:/Place Project/Hour Test/"+date+"-"+minute+".txt";
        file = new File(fileName);
        writer = new PrintWriter(fileName);
    }

    public void writeToFile(String e)
    {
        writer.println(e);
    }

    public void closeWriter()
    {
        writer.close();
    }

    public String getDate()
    {
        return date;
    }

    public String getMinute()
    {
        return minute;
    }


}