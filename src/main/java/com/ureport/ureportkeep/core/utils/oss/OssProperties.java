package com.ureport.ureportkeep.core.utils.oss;

/**
 * @Author: summer
 * @Date: 2022/4/3 10:51
 * @Description: oss配置
 **/
public class OssProperties {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String region;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * 最大线程数，默认： 100
     */
    private Integer maxConnections = 100;

    /**
     * 上传路径
     */
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
