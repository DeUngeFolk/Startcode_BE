package ApiFetcher;

import utils.HttpUtils;

import java.io.IOException;

public class CatFactFetch {

    public static void CatFactFetch() throws IOException {

        String dummyFetch = HttpUtils.fetchData("https://meowfacts.herokuapp.com/");
        System.out.println("Test fetch");
        System.out.println(dummyFetch);
    }
}
