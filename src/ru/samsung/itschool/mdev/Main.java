package ru.samsung.itschool.mdev;

import com.google.gson.Gson;
import ru.samsung.itschool.mdev.model.Answer;
import ru.samsung.itschool.mdev.model.Value;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        // https://api.icndb.com/jokes/random

        String url = "https://api.icndb.com/jokes/random";
        HttpsURLConnection connection;
        URL u = new URL(url);
        connection = (HttpsURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.connect();

        // HTTP серверов - коды ответы
        int code = connection.getResponseCode();

        ArrayList<String> lines = new ArrayList<>();
        if(code == 200) {
            Scanner scan = new Scanner(connection.getInputStream());
            while(scan.hasNext()) {
                lines.add(scan.nextLine());
            }
        }
        //for(String s: lines) {
          //  System.out.println(s);
        //}
        Path pathsave = Path.of("result.txt");
      //  Files.createFile(pathsave); // проверка на существование
        Files.write(pathsave,lines);

        List<String> readlines = Files.readAllLines(pathsave);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: readlines) {
            stringBuilder.append(s);
        }
        String res = stringBuilder.toString();
        Gson gson = new Gson();

        Answer answer = gson.fromJson(res,Answer.class);
        Value value = answer.getValue();
        System.out.println(value.getJoke());
    }




}
