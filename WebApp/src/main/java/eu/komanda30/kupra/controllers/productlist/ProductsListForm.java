package eu.komanda30.kupra.controllers.productlist;

import java.util.LinkedList;

public class ProductsListForm {
    private LinkedList<ProductItem> items = new LinkedList<ProductItem>();

    public void addItem(ProductItem item){
        items.add(item);
    }

    public LinkedList<ProductItem> getItems(){
        return items;
    }
}
