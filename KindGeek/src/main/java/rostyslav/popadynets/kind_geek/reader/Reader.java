package rostyslav.popadynets.kind_geek.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    public static List<String> info = new ArrayList<>();

    public static void read() throws Exception {
        File data = new File("data.txt");
        if (data != null) {
            FileReader fileReaderData = new FileReader(data);
            BufferedReader buffereReaderData = new BufferedReader(fileReaderData);
            while (buffereReaderData.ready()) {
                String line = buffereReaderData.readLine();
                info.add(line);
            }

        }
    }

}
