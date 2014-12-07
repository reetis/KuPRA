package eu.komanda30.kupra.controllers.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/search")
public class InlineSearchController {
    @RequestMapping(method = RequestMethod.GET)
    public String showForm(@ModelAttribute("results") final InlineSearchResultForm form) {
        form.addPersonRow(new InlineSearchResultForm.PersonRow() {{
            setImageUrl("");
            setName("Zmogus");
        }});
        form.addPersonRow(new InlineSearchResultForm.PersonRow() {{
            setImageUrl("");
            setName("Zmogus2");
        }});

        form.addRecipeRow(new InlineSearchResultForm.RecipeRow() {{
            setImageUrl("");
            setName("Receptas");
            setDescription("Aprasymas");
        }});
        form.addRecipeRow(new InlineSearchResultForm.RecipeRow() {{
            setImageUrl("");
            setName("Receptas2");
            setDescription("Aprasymas2");
        }});
        return "inline-search::searchResults";
    }
}
