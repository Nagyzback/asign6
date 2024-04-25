package pack1;

interface Pizza {
    double getPrice();
}

abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public double getPrice() {
        return pizza.getPrice();
    }
}

class PepperoniTopping extends PizzaDecorator {
    public PepperoniTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 2.5;
    }
}

class MushroomTopping extends PizzaDecorator {
    public MushroomTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 1.8;
    }
}

public class Main1 {
    public static void main(String[] args) {
        Pizza pizza = new BasePizza();
        pizza = new PepperoniTopping(pizza);
        pizza = new MushroomTopping(pizza);
        System.out.println("Final Price: $" + pizza.getPrice());
    }
}

class BasePizza implements Pizza {
    @Override
    public double getPrice() {
        return 10.0;
    }
}
