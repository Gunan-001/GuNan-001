package com.small.fruitandvagetablemallspringboot.service;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JwtService {

    String createToken(String userName);

    boolean checkToken(String token);

    Map<String, Object> getChaimData();
}
