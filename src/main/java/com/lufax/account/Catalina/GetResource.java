package com.lufax.account.Catalina;

import com.squareup.okhttp.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GetResource {

    private static final String BASE_URL = "http://www.111avtb.com";
    protected static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    protected static OkHttpClient client = new OkHttpClient();
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue());


    private static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("X-Real-IP", "192.168.30.83").get().build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static void getResource(String result) throws IOException {
        String[] recentPage = result.split("\n");
        for (String s : recentPage) {
            if (s.contains("href") && s.contains("title") && s.contains("thumbnail")) {
                String mp4Url = s.split("\"")[1];
                String mp4Page = get(BASE_URL + mp4Url);
                String[] mp4PageContent = mp4Page.split("\n");
                for (String s1 : mp4PageContent) {
                    if (s1.contains("video/mp4") && s1.contains("360p")) {
                        System.out.println(s1.split("\"")[1]);
                    }
                }
            }
        }
    }

    private static void getMp4Resource(String mp4Url) throws IOException {
        String mp4Page = get(mp4Url);
        String[] mp4PageContent = mp4Page.split("\n");
        for (String s1 : mp4PageContent) {
            if (s1.contains("video/mp4") && s1.contains("360p")) {
                System.out.println(s1.split("\"")[1]);
            }
        }
    }

    public static void downMp4() {
        String remotePath = "http://media66.avtb02.com/media/videos/mp4/23155.mp4?st=4vmKoZe_HDRqx_p4vwHdiQ&e" +
                "=1517163874";
        try {
            URL url = new URL(remotePath);
            URLConnection conn = url.openConnection();
            int fileLength = conn.getContentLength();
            BufferedInputStream zipInputBuffer = new BufferedInputStream(conn.getInputStream());
            BufferedOutputStream fileOutBuffer = new BufferedOutputStream(new FileOutputStream(new File("C:\\test" +
                    ".temp")));
            byte[] buffer = new byte[1024];
            int downLength = 0;
            int read = 0;
            while ((read = zipInputBuffer.read(buffer)) != -1) {
                fileOutBuffer.write(buffer, 0, read);
                downLength += read;
                System.out.println("总共" + (fileLength) / 1024);
                System.out.println("-------剩余----------");
                System.out.println((fileLength - downLength) / 1024);
            }
            System.out.println("下载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载异常");
        }
    }

    public static void downloadNet() throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL("http://www.avtb009.com/media/videos/tmb/000/023/160/2.jpg");

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("c:/abc.jpg");

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
//                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

//        RequestBody body = RequestBody.create(JSON_TYPE, "{\"UserId\":123}");
//        downMp4();
        int j = 1000;
        for (int i = j + 30; i > j; i--) {
//            String pageUrl = "http://www.222avtb.com/recent/" + (i == 0 ? "" : i);
            final int finalI = i;
            EXECUTOR_SERVICE.submit(new Runnable() {
                public void run() {
                    String mp4Url = "http://www.222avtb.com/" + (finalI == 0 ? "" : finalI);
                    try {
                        getMp4Resource(mp4Url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

//            System.out.println(System.currentTimeMillis());
//            System.out.println(pageUrl);
//            String result = get(pageUrl);
//            getResource(result);
        }

EXECUTOR_SERVICE.shutdown();
//        Document doc = Jsoup.connect("http://www.avtb009.com/recent").get();
//        System.out.println(123);
    }
}
