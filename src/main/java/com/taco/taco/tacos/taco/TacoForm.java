package com.taco.taco.tacos.taco;

import com.taco.taco.tacos.data.Ingredient;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TacoForm {
    @NotNull
    @Size(min=5, message = "길이 5 이상")
    private String name;

    @NotNull(message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;
}
