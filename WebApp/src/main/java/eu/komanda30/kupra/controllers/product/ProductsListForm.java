package eu.komanda30.kupra.controllers.product;

import java.util.LinkedList;

/**
 * Created by Gintare on 2014-12-02.
 */
public class ProductsListForm {
    private LinkedList<ProductItem> items = new LinkedList<ProductItem>();

    public void addItem(ProductItem item){
        items.add(item);
    }

    public LinkedList<ProductItem> getItems(){
        return items;
    }
}
