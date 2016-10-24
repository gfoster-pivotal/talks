package com.example.catalog;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class ContractRule implements MethodRule {

    private final String uri;
    private final String fileName;
    private final HttpMethod httpMethod;
    private final RestTemplate restTemplate;

    public ContractRule(String uri, String fileName, HttpMethod httpMethod) {
        this.uri = uri;
        this.fileName = fileName;
        this.httpMethod = httpMethod;
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod method, Object target) {
        System.out.println("statement = " + statement);
        System.out.println("method = " + method);
        System.out.println("target = " + target);
        return statement;
    }
}
