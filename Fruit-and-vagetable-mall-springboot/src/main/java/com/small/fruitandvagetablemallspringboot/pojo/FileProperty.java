package com.small.fruitandvagetablemallspringboot.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author rest
 * @ProjectName springboot-upload
 * @date
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperty {

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
