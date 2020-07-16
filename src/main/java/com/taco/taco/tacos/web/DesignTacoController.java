package com.taco.taco.tacos.web;

import com.taco.taco.tacos.data.Ingredient;
import com.taco.taco.tacos.Taco;
import com.taco.taco.tacos.data.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import com.taco.taco.tacos.data.Ingredient.Type;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @GetMapping("/design")
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        EnumSet<Type> types = EnumSet.allOf(Type.class);
        for(Type type : types)
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));

        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping("/design")
    public String processDesign(@Valid Taco design, Errors errors){
        if(errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}

