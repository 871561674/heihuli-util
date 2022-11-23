package com.heihuli.test;

import com.heihuli.util.GzipUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author heihuli
 */
public class GzipUtilTest {

    @Test
    public void test() throws IOException {
        String str = "dafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciowdafavasdfawefjsoidhvgoiwheriohqwoifhiusdhfiojqwpojdqwafoihfouidhsioufhankshfiluhweiuqfhqioehfjoisdhfoihsdogfhweoihfwoeihfciow";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        System.out.println("original bytes: " + bytes.length);
        // original bytes: 1250
        System.out.println("original str: " + str.length());
        // original str: 1250

        // compress
        byte[] compress = GzipUtil.compress(bytes);
        System.out.println(compress + " " + compress.length);
        // [B@53e25b76 118
        byte[] compress1 = GzipUtil.compress(str, "");
        System.out.println(compress1 + " " + compress.length);
        // [B@73a8dfcc 118

        // 压缩后非常小, 1250-->160
        String compress2 = GzipUtil.compressBase64Encode(bytes, "");
        System.out.println(compress2 + " " + compress2.length());
        // H4sIAAAAAAAAAO3NMQ7DMAxD0bMKlRnRCcq6gqPr1zlENy9a9InnBrstHVYNPUWP+xAr2peKUSKCMz1A9VEfdR9lEAOaK05qIux95iquGdU4B2JQLdDFZ7nidB1Yz2dXauu+qPKNb3zjG/8n/gMZyqEF4gQAAA== 160
        String compress3 = GzipUtil.compressBase64Encode(str, "");
        System.out.println(compress3 + " " + compress3.length());
        // H4sIAAAAAAAAAO3NMQ7DMAxD0bMKlRnRCcq6gqPr1zlENy9a9InnBrstHVYNPUWP+xAr2peKUSKCMz1A9VEfdR9lEAOaK05qIux95iquGdU4B2JQLdDFZ7nidB1Yz2dXauu+qPKNb3zjG/8n/gMZyqEF4gQAAA== 160

        // decompress
        byte[] decompress = GzipUtil.decompress(compress);
        System.out.println(decompress + " " + decompress.length);
        // [B@ea30797 1250
        String decompress1 = GzipUtil.decompress(compress1, "");
        System.out.println(decompress1 + " " + decompress1.length());
        // ... 1250
        String decompress2 = GzipUtil.base64DecodeDecompress(compress2, "");
        System.out.println(decompress2 + " " + decompress2.length());
        // ... 1250
        byte[] decompress3 = GzipUtil.base64DecodeDecompress(compress3);
        System.out.println(decompress3 + " " + decompress3.length);
        // [B@7e774085 118



    }
}
