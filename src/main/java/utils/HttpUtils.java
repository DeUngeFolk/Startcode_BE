package utils;

import com.google.gson.Gson;
import dtos.CatFactDTO;
import dtos.RenameMeDTO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUtils {

    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    private static Gson gson = new Gson();

    // this Method acts as a switch for the different fetches we want to make, it can return a list containing any type of DTO.

    public static List<?> FetchSwitch(String DTO) throws ExecutionException, InterruptedException {

        if (DTO != null) {
            switch (DTO) {

                // use the first case in the switch as a template for additional switches you may want to add.

                case "catFactDTO":
                    List<CatFactDTO> catFactDTOArrayList = new ArrayList<>();
                    catFactDTOArrayList.add(catFactDTOFetch());
                    return catFactDTOArrayList;


                case "RenameMeDTO":
                    List<RenameMeDTO> list1 = null;
                    list1.add(new RenameMeDTO("dummy", "dummy"));
                    return list1;

            }
        }

        return null;
    }

    // down here you will make the methods for the individual fetch methods that will be called in the switch.

    public static CatFactDTO catFactDTOFetch() throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        // Create parallel fetches here. by following the template below

        Future<CatFactDTO> catFactDTOFuture = es.submit(
                () -> gson.fromJson(HttpUtils.fetchData("https://meowfacts.herokuapp.com/"), CatFactDTO.class)

        );


        CatFactDTO catFactDTO = new CatFactDTO(catFactDTOFuture.get());

        return catFactDTO;
    }


}