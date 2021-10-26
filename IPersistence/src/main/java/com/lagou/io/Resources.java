package com.lagou.io;

import java.io.InputStream;

public class Resources {


    /**
     * 返回一个输入流，保存在内存中
     * @param path
     * @return
     */
    public static InputStream getResourceAsSteam(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

}
