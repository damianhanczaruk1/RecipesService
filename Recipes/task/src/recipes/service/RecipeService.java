package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.config.UserDetailsImpl;
import recipes.exception.AuthenticationException;
import recipes.exception.CustomErrorHandler;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe newRecipe, UserDetailsImpl userDetailsImpl) {
        newRecipe.setDate(LocalDateTime.now());
        newRecipe.setAuthorEmail(userDetailsImpl.getUsername());
        return recipeRepository.save(newRecipe);
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Recipe not found for id = " + id));
    }

    public void deleteRecipeById(Long id, UserDetailsImpl userDetailsImpl) {
        Recipe recipe = findRecipeById(id);
        if (!userDetailsImpl.getUsername().equals(recipe.getAuthorEmail()))
            throw new AuthenticationException("You cannot delete other user recipe!");
        recipeRepository.delete(recipe);
    }

    public void updateRecipeById(Long id, Recipe recipeUpdate, UserDetailsImpl userDetailsImpl) {
        Optional<Recipe> optionalRecipe = Optional.ofNullable(findRecipeById(id));
        if (optionalRecipe.isPresent()) {
            Recipe oldRecipe = optionalRecipe.get();
            if (!oldRecipe.getAuthorEmail().equals(userDetailsImpl.getUsername()))
                throw new AuthenticationException("You cannot update other user recipe!");
            oldRecipe.copyOf(recipeUpdate);
            oldRecipe.setDate(LocalDateTime.now());
            recipeRepository.save(oldRecipe);
        }
    }

    public List<Recipe> findRecipeByNameOrCategory(String name, String category) {
        if (name == null && category == null) {
            throw new CustomErrorHandler.NoParamsException("No params found");
        } else if (name == null) {
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        } else return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);

    }

}
