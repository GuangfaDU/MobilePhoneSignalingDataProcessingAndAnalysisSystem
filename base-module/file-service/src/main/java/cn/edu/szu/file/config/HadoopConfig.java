package cn.edu.szu.file.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.net.URI;


/**
 * @author GuangfaDU
 * @version 1.0
 */
@org.springframework.context.annotation.Configuration
public class HadoopConfig {

    @Value("hdfs://sandbox-hdp.hortonworks.com:8020")
    private String nameNode;

    private static final String DEFAULT_USER = "hdfs";

    @Bean
    public FileSystem createFileSystem() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", nameNode);
        conf.set("dfs.client.use.datanode.hostname", "true");
        URI uri = new URI(nameNode.trim());
        return FileSystem.get(uri, conf, DEFAULT_USER);
    }
}
