package com.taco.taco.tacos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Taco {
    private String name;
    private List<String> ingredients;
}
