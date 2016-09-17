package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Andrei on 31/03/2016.
 */
public class Drink {
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleLongProperty qty;
    private SimpleLongProperty vol;
    private SimpleDoubleProperty alcPer;

    public Drink(String name, double price, long qty, int vol, double alcPer){

        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.qty = new SimpleLongProperty(qty);
        this.vol = new SimpleLongProperty(vol);
        this.alcPer = new SimpleDoubleProperty(alcPer);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public long getQty() {
        return qty.get();
    }

    public SimpleLongProperty qtyProperty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty.set(qty);
    }

    public double getAlcPer() {
        return alcPer.get();
    }

    public SimpleDoubleProperty alcPerProperty() {
        return alcPer;
    }

    public void setAlcPer(double alcPer) {
        this.alcPer.set(alcPer);
    }

    public long getVol() {
        return vol.get();
    }

    public SimpleLongProperty volProperty() {
        return vol;
    }

    public void setVol(Long vol) {
        this.vol.set(vol);
    }
}
