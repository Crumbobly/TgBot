package ru.lab1.tg.domain;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListedProducts {

    protected final LinkedHashMap<Integer, MutablePair<Product, Integer>> products;
    private int lastId = 0;

    public ListedProducts() {
        this.products = new LinkedHashMap<>();
    }

    public LinkedHashMap<Integer, MutablePair<Product, Integer>> getProducts() {
        return products;
    }

    public void addProduct(Product product){

        for(var pc: products.values()){
            if (pc.getLeft().equals(product)){
                pc.setValue(pc.getRight() + 1);
                return;
            }
        }

        products.put(lastId, MutablePair.of(product, 1));
        lastId++;
    }

    public MutablePair<Product, Integer> getProductAndCountById(int id) {
        return products.get(id);
    }

    public void clear(){
        products.clear();
    }

    public Product popProduct(int id){

        MutablePair<Product, Integer> pc =  products.get(id);

        if (pc == null){
            return null;
        }

        if (pc.getRight() > 1){
            pc.setValue(pc.getRight() - 1);
        }
        else {
            products.remove(id);
        }

        return pc.getLeft();
    }

    public Double getSum(){
        double sum = 0;
        for (MutablePair<Product, Integer> pc: products.values()){
            sum += pc.getLeft().price();
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Список товаров:\n");

        for (Map.Entry<Integer, MutablePair<Product, Integer>> entry: products.entrySet()) {
            out
                    .append("id=")
                    .append(entry.getKey())
                    .append(", ")
                    .append(entry.getValue().getLeft().name())
                    .append(", ")
                    .append(entry.getValue().getLeft().price())
                    .append(" руб, количество:  ")
                    .append(entry.getValue().getRight())
                    .append("\n");
        }

        return out.toString();
    }

}
