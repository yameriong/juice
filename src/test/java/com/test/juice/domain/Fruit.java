package com.test.juice.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// 과일의 상태
@Data
@Builder
public class Fruit{
    int gram;
    String name;
    LocalDateTime expirationDate;

    public boolean isCondition(){
        return expirationDate.isAfter(LocalDateTime.now());
    }

    public void grepFruit(){
        this.gram = gram - 200;
    }
}
