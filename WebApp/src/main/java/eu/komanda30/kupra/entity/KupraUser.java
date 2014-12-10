package eu.komanda30.kupra.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableList;

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
    private List<Menu> menuListas;

    @OneToMany(mappedBy = "author")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "source")
    private List<Friendship> friendships;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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
        this.menuListas.add(menuListas);
    }

}
