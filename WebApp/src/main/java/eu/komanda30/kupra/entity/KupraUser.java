package eu.komanda30.kupra.entity;

import com.google.common.collect.ImmutableList;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Table(name="`user`")
@Entity
@Indexed
public class KupraUser {
    @Id
    @Field(name = "username", boost = @Boost(1.5f))
    private String userId;

    @IndexedEmbedded
    @Embedded
    private UserProfile profile;

    private boolean isAdmin;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FridgeItem> fridgeContent;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList;

    @OneToMany(mappedBy = "author")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "source")
    private List<Friendship> friendships;

    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private UsernamePasswordAuth usernamePasswordAuth;

    //for hibernate
    protected KupraUser() {

    }

    public KupraUser(String userId, UserProfile profile) {
        this.userId = userId;
        this.profile = profile;
        this.isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public String getUserId() {
        return userId;
    }

    public UsernamePasswordAuth getUsernamePasswordAuth() {
        return usernamePasswordAuth;
    }

    public void setLoginDetails(String username, String password, PasswordEncoder encoder) {
        final String encodedNewPassword = encoder.encode(password);

        if (usernamePasswordAuth == null) {
            usernamePasswordAuth = new UsernamePasswordAuth(this, username, encodedNewPassword);
        } else {
            setPassword(password, encoder);
        }
    }

    public void setPassword(String password, PasswordEncoder encoder) {
        Assert.notNull(usernamePasswordAuth);
        usernamePasswordAuth.setPassword(encoder.encode(password));
    }

    public List<FridgeItem> getFridgeItems() {
        return ImmutableList.copyOf(fridgeContent);
    }

    public void addFridgeItem(Product product, BigDecimal amount) {
        final FridgeItem fridgeItem = new FridgeItem(product, amount);

        final Optional<FridgeItem> item = fridgeContent.stream()
                .filter(input -> input.getProduct() == fridgeItem.getProduct())
                .findFirst();

        if (item.isPresent()) {
            item.get().increaseAmount(fridgeItem.getAmount());
        } else {
            fridgeContent.add(fridgeItem);
        }
    }

    public void removeFromFridgeByProduct(int productId) {
        final List<FridgeItem> badItems = fridgeContent.parallelStream()
                .filter(item -> item.getProduct().getId() == productId)
                .collect(Collectors.toList());
        fridgeContent.removeAll(badItems);
    }


    public void addMeniuItem(Menu menuListas) {
        this.menuList.add(menuListas);
    }

    public void removeMenuItem(Menu menuItem){
        menuList.remove(menuItem);
    }

    public Boolean consumeItemsFromFridge(Menu menu){
        Recipe recipe = menu.getRecipe();
        Boolean enoughProducts = true;
        List<RecipeProduct> recipeProducts = recipe.getRecipeProductList();
        BigDecimal servingsDivisor = new BigDecimal(recipe.getServings());
        BigDecimal servingsMultiplier = new BigDecimal(menu.getServings());

        for(RecipeProduct recipeProduct : recipeProducts){
            Boolean enoughProduct = false;
            for(FridgeItem fridgeItem : fridgeContent){

                BigDecimal amountPresent = fridgeItem.getAmount();
                BigDecimal amountNeeded = recipeProduct.getQuantity().multiply(servingsMultiplier).
                                                                        divide(servingsDivisor, 2, RoundingMode.HALF_UP);

                 if (fridgeItem.getProduct().equals(recipeProduct.getProduct())
                         && amountPresent.compareTo(amountNeeded) >= 0) {

                     fridgeItem.consumeAmount(amountNeeded);
                     enoughProduct = true;
                     break;
                 }

            }

            // If there is lack of at least one product, recipe cannot be Prepared, items cannot be removed
            if (!enoughProduct){
                enoughProducts = false;
                break;
            }
        }

        return enoughProducts;
    }

    public ArrayList<RecipeProduct> getLackingProducts(List<RecipeProduct> productsNeeded, BigDecimal servingsNeeded){
        ArrayList<RecipeProduct> lackingProducts = new ArrayList<RecipeProduct>();


        // Paklaust maxo kaip aprasyt tokias nesamones pagal best practices
        for(RecipeProduct productInNeed : productsNeeded){
            BigDecimal quantityNeeded = productInNeed.getQuantity();

            for(FridgeItem fridgeItem : fridgeContent){
                if (fridgeItem.getProduct().equals(productInNeed.getProduct())){
                    if (fridgeItem.getAmount().compareTo(quantityNeeded) < 0){
                        quantityNeeded = quantityNeeded.subtract(fridgeItem.getAmount());
                    } else {
                        quantityNeeded = BigDecimal.ZERO;
                    }
                    break;
                }

            }
            if (quantityNeeded.compareTo(BigDecimal.ZERO) > 0){
                // Fake RecipeProduct Object without owner or Recipe To store product/quantity
                RecipeProduct lackingProduct = new RecipeProduct();
                lackingProduct.setProduct(productInNeed.getProduct());
                lackingProduct.setQuantity(quantityNeeded);
                lackingProducts.add(lackingProduct);
            }

        }
        return lackingProducts;
    }

    public ArrayList<RecipeProduct> getLackingProducts(List<RecipeProduct> productsNeeded){
        ArrayList<RecipeProduct> lackingProducts = new ArrayList<RecipeProduct>();


        // Paklaust maxo kaip aprasyt tokias nesamones pagal best practices
        for(RecipeProduct productInNeed : productsNeeded){
            BigDecimal quantityNeeded = productInNeed.getQuantity();

            for(FridgeItem fridgeItem : fridgeContent){
                if (fridgeItem.getProduct().equals(productInNeed.getProduct())){
                    if (fridgeItem.getAmount().compareTo(quantityNeeded) < 0){
                        quantityNeeded = quantityNeeded.subtract(fridgeItem.getAmount());
                    } else {
                        quantityNeeded = BigDecimal.ZERO;
                    }
                    break;
                }

            }
            if (quantityNeeded.compareTo(BigDecimal.ZERO) > 0){
                // Fake RecipeProduct Object without owner or Recipe To store product/quantity
                RecipeProduct lackingProduct = new RecipeProduct();
                lackingProduct.setProduct(productInNeed.getProduct());
                lackingProduct.setQuantity(quantityNeeded);
                lackingProducts.add(lackingProduct);
            }

        }
        return lackingProducts;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public ArrayList<RecipeProduct> getProductsNeededForMenu(){
        ArrayList<RecipeProduct> productsNeeded = new ArrayList<>();
        for(Menu menu : menuList){
            if (!menu.isCompleted()){
                Recipe recipe = menu.getRecipe();
                BigDecimal recipeServings = new BigDecimal(recipe.getServings());
                BigDecimal menuServings = new BigDecimal(menu.getServings());

                for(RecipeProduct recipeProduct : recipe.getRecipeProductList()){
                    BigDecimal amountNeeded = recipeProduct.getQuantity().divide(recipeServings, 2).multiply(menuServings);
                    Boolean productSet = false;
                    for(RecipeProduct neededProduct : productsNeeded){
                        if (recipeProduct.getProduct().equals(neededProduct.getProduct())){
                            neededProduct.addQuantity(amountNeeded);
                            productSet = true;
                            break;
                        }
                    }

                    if (!productSet){
                        RecipeProduct newProduct = new RecipeProduct();
                        newProduct.setRecipe(recipe);
                        newProduct.setProduct(recipeProduct.getProduct());
                        newProduct.setQuantity(amountNeeded);
                        productsNeeded.add(newProduct);
                    }
                }
            }
        }

        return productsNeeded;
    }
}
