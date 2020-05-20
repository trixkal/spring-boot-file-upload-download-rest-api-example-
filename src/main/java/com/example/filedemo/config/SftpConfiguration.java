package com.example.filedemo.config;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;

@Configuration
public class SftpConfiguration {

    @Value("${files.sftp.host}")
    private String sftpHost;

    @Value("#{new Integer('${files.sftp.port}')}")
    private int sftpPort;

    @Value("${files.sftp.user}")
    private String sftpUser;

    @Value("${files.sftp.password}")
    private String sftpPassword;

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpHost);
        factory.setPort(sftpPort);
        factory.setUser(sftpUser);
        factory.setPassword(sftpPassword);
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<>(factory);
    }


    @Bean
    public SftpRemoteFileTemplate sftpRemoteFileTemplate() {
        SftpRemoteFileTemplate template = new SftpRemoteFileTemplate(sftpSessionFactory());
        template.setRemoteDirectoryExpression(new SpelExpressionParser().parseExpression("''"));
        return template;
    }

}