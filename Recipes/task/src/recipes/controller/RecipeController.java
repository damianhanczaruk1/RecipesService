package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.service.RecipeService;
import recipes.config.UserDetailsImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe/new")
    public Object postRecipe(@Valid @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.saveRecipe(recipe, userDetails);
        return String.format("{\"id\": %d}", newRecipe.getId());
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return recipeService.findRecipeById(id);
    }

    @DeleteMapping("/recipe/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable long id) {
        recipeService.deleteRecipeById(id, userDetails);
    }

    @PutMapping("/recipe/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void putRecipe(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable long id, @Valid @RequestBody Recipe recipe) {
        recipeService.updateRecipeById(id, recipe, userDetails);
    }

    @GetMapping("/recipe/search/")
    public List<Recipe> getRecipesByCategoryOrByName(@RequestParam(required = false, name = "name") String name, @RequestParam(required = false, name = "category") String category) {
        return recipeService.findRecipeByNameOrCategory(name, category);
    }
}
