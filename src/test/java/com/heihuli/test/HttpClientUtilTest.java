package com.heihuli.test;

import com.heihuli.util.HttpClientUtil;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author heihuli
 */
public class HttpClientUtilTest {

    /**
     * HttpClientUtil.doPost
     */
    @Test
    public void test() {
        String s = HttpClientUtil.doPost("http://127.0.0.1:8080/post");
        System.out.println(s);
        // post请求
    }

    /**
     * HttpClientUtil.doPostParam
     */
    @Test
    public void test2() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "heihuli");
        System.out.println(HttpClientUtil.doPostParam("http://127.0.0.1:8080/postParam", map));
        // id: 1, name: heihuli
    }

    /**
     * HttpClientUtil.doPostHeader
     */
    @Test
    public void test3() {
        Map<String, String> header = new HashMap<>();
        header.put("header1", "1");
        header.put("header2", "2");
        System.out.println(HttpClientUtil.doPostHeader("http://127.0.0.1:8080/postHeader", header));
        // header1: 1, header2: 2
    }

    /**
     * HttpClientUtil.doPostParamHeader
     */
    @Test
    public void test4() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "heihuli");
        Map<String, String> header = new HashMap<>();
        header.put("header1", "1");
        header.put("header2", "2");
        System.out.println(HttpClientUtil.doPostParamHeader("http://127.0.0.1:8080/postWithParamHeader", map, header));
        // {"header2":"2","header1":"1","name":"heihuli","id":"1"}
    }

    @Test
    public void test5() {
        String json = "{\"header2\":\"2\",\"header1\":\"1\",\"name\":\"heihuli\",\"id\":\"1\"}";
        System.out.println(HttpClientUtil.doPostJson("http://127.0.0.1:8080/postJson", json));
        // {"header2":"2","header1":"1","name":"heihuli","id":"1"}
    }

    /**
     * HttpClientUtil.doPostJson
     */
    @Test
    public void test6() {
        String json = "{\"header2\":\"2\",\"header1\":\"1\",\"name\":\"heihuli\",\"id\":\"1\"}";
        System.out.println(HttpClientUtil.doPostJson("http://127.0.0.1:8080/postJsonBody", json));
        // {"id":"1","name":"heihuli","header1":"1","header2":"2"}
    }

    /**
     * HttpClientUtil.doPostJsonHeader
     */
    @Test
    public void test7() {
        String json = "{\"header2\":\"2\",\"header1\":\"1\",\"name\":\"heihuli\",\"id\":\"1\"}";
        Map<String, String> header = new HashMap<>();
        header.put("header1", "1");
        header.put("header2", "2");
        System.out.println(HttpClientUtil.doPostJsonHeader("127.0.0.1:8080/postJsonBodyHeader", json, header));
        // {"header2":"2","header1":"1","body":{"id":"1","name":"heihuli","header1":"1","header2":"2"}}
    }

    /**
     * HttpClientUtil.doPostForm
     */
    @Test
    public void test8() {
        Map<String, String> form = new HashMap<>();
        form.put("id", "1");
        form.put("name", "heihuli");
        System.out.println(HttpClientUtil.doPostForm("127.0.0.1:8080/postParam", form));
        // id: 1, name: heihuli
    }

    /**
     * HttpClientUtil.doPostFormHeader
     */
    @Test
    public void test9() {
        Map<String, String> form = new HashMap<>();
        form.put("id", "1");
        form.put("name", "heihuli");
        Map<String, String> header = new HashMap<>();
        header.put("header1", "1");
        header.put("header2", "2");
        System.out.println(HttpClientUtil.doPostFormHeader("http://127.0.0.1:8080/postWithParamHeader", form, header));
        // {"header2":"2","header1":"1","name":"heihuli","id":"1"}
    }

    /**
     * HttpClientUtil.doPostFile
     */
    @Test
    public void test10() {
        File file = new File("src/test/java/com/heihuli/file/heihuli/test1.txt");
        File file2 = new File("src/test/java/com/heihuli/file/heihuli/test2.txt");
        System.out.println(HttpClientUtil.doPostFile("http://127.0.0.1:8080/doPostFile", "heihuliFile", file, file2));
        // 文件信息：
        //	文件名：test1.txt	文件大小：0.0048828125KB	ContentType:application/octet-stream
        //
        // 文件信息：
        //	文件名：test2.txt	文件大小：0.0048828125KB	ContentType:application/octet-stream
    }

    /**
     * HttpClientUtil.doPostInputStream
     */
    @Test
    public void test11() {
        InputStream is = new ByteArrayInputStream("黑狐狸一号".getBytes());
        System.out.println(HttpClientUtil.doPostInputStream("http://127.0.0.1:8080/doPostInputStream", is));
        // 黑狐狸一号
    }

    /**
     * HttpClientUtil.doGet
     */
    @Test
    public void test12() {
        System.out.println(HttpClientUtil.doGet("http://127.0.0.1:8080/doGet"));
        // get请求
    }

    /**
     * HttpClientUtil.doGet
     */
    @Test
    public void test13() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "heihuli");
        System.out.println(HttpClientUtil.doGet("http://127.0.0.1:8080/doGetParma", map));
        // id: 1, name: heihuli
    }

    // 服务端controller代码
//    @PostMapping("/post")
//    public String post() {
//        return "post请求";
//    }
//
//    @PostMapping("/postParam")
//    public String postParam(String id, String name) {
//        return "id: " + id + ", name: " + name;
//    }
//
//    @PostMapping("/postHeader")
//    public String postHeader(@RequestHeader(name = "header1") String id,
//                             @RequestHeader(name = "header2") String name) {
//        return "header1: " + id + ", header2: " + name;
//    }
//
//    @PostMapping("/postWithParamHeader")
//    public Map postWithParamHeader(@RequestHeader(name = "header1") String header1,
//                                   @RequestHeader(name = "header2") String header2,
//                                   String id, String name) {
//        Map<String, String> map = new HashMap<>();
//        map.put("id", id);
//        map.put("name", name);
//        map.put("header1", header1);
//        map.put("header2", header2);
//        return map;
//    }
//
//    @PostMapping("/postJson")
//    public Map postJson(@RequestBody Map<String, String> map) {
//        System.out.println(map);
//        return map;
//    }
//
//    @PostMapping("/postJsonBody")
//    public JsonDomain postJson(@RequestBody JsonDomain jsonDomain) {
//        System.out.println(jsonDomain);
//        return jsonDomain;
//    }
//
//    @PostMapping("/postJsonBodyHeader")
//    public Map<String, Object> postJson(@RequestBody JsonDomain jsonDomain, @RequestHeader String header1, @RequestHeader String header2) {
//        System.out.println(jsonDomain);
//        System.out.println(header1);
//        System.out.println(header2);
//        Map<String, Object> map = new HashMap<>();
//        map.put("body", jsonDomain);
//        map.put("header1", header1);
//        map.put("header2", header2);
//        return map;
//    }
//
//    @PostMapping("/doPostFile")
//    public String doPostFile(@RequestParam("heihuliFile") List<MultipartFile> multipartFiles) throws UnsupportedEncodingException {
//        StringBuilder builder = new StringBuilder();
//        //防止中文乱码
//        String fileName;
//        for(MultipartFile file : multipartFiles){
//            builder.append("\n文件信息：\n");
//            fileName = file.getOriginalFilename();
//            if (fileName == null){
//                continue;
//            }
//            //防止中文乱码
//            fileName = URLDecoder.decode(fileName,"utf-8");
//            builder.append("\t文件名：").append(fileName)
//                    .append("\t文件大小：").append(file.getSize() * 1.0 / 1024).append("KB")
//                    .append("\tContentType:").append(file.getContentType())
//                    .append("\n");
//        }
//        return builder.toString();
//    }
//
//    @PostMapping("/doPostInputStream")
//    public String postJson(InputStream inputStream) throws IOException {
//        StringBuffer sb = new StringBuffer();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//        return sb.toString();
//    }
//
//    @GetMapping("/doGet")
//    public String doGet() {
//        return "get请求";
//    }
//
//    @GetMapping("/doGetParma")
//    public String doGetParma(String id, String name) {
//        return "id: " + id + ", name: " + name;
//    }
}

