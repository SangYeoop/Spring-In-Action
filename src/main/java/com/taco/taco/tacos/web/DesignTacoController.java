package com.taco.taco.tacos.web;

import com.taco.taco.tacos.Order.OrderForm;
import com.taco.taco.tacos.User.Account;
import com.taco.taco.tacos.User.CurrentUser;
import com.taco.taco.tacos.data.*;
import com.taco.taco.tacos.taco.Taco;
import com.taco.taco.tacos.taco.TacoForm;
import com.taco.taco.tacos.taco.TacoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import com.taco.taco.tacos.data.Ingredient.Type;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@SessionAttributes("orderForm")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final ModelMapper modelMapper;

    @ModelAttribute
    public OrderForm orderForm() {
        return new OrderForm();
    }

    @GetMapping("/design")
    public String showDesignForm(@CurrentUser Account account, Model model){
        List<Ingredient> ingredients = ingredientRepository.findAll();
        EnumSet<Type> types = EnumSet.allOf(Type.class);
        for(Type type : types)
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));

        model.addAttribute(new TacoForm());
        return "design";
    }

    @PostMapping("/design")
    public String processDesign(@Valid @ModelAttribute("tacoForm") TacoForm design, Errors errors , @ModelAttribute("orderForm") OrderForm orderForm, Model model){
        if(errors.hasErrors()) {
            List<Ingredient> ingredients = ingredientRepository.findAll();
            EnumSet<Type> types = EnumSet.allOf(Type.class);
            for(Type type : types)
                model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
            return "design";
        }
        orderForm.addDesign(design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}

