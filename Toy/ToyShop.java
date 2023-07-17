package Toy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToyShop {
    private List<Toy> toys;

    public ToyShop() {
        toys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateWeight(int toyId, int weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public Toy choosePrizeToy() {
        Random random = new Random();
        int totalWeight = getTotalWeight();
        int randomNumber = random.nextInt(totalWeight);

        int cumulativeWeight = 0;
        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (randomNumber < cumulativeWeight) {
                toys.remove(toy);
                return toy;
            }
        }

        return null;
    }

    private int getTotalWeight() {

        int totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }
        return totalWeight;
    }

    public void savePrizeToyToFile(Toy toy) {
        try {
            FileWriter writer = new FileWriter("prize_toys.txt", true);
            writer.write(toy.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyShop toyShop = new ToyShop();

        Toy toy1 = new Toy(1, "Кукла", 10, 20);
        Toy toy2 = new Toy(2, "Мяч", 15, 30);
        Toy toy3 = new Toy(3, "Машинка", 20, 25);
        toyShop.addToy(toy1);
        toyShop.addToy(toy2);
        toyShop.addToy(toy3);

        toyShop.updateWeight(2, 40);

        Toy prizeToy = toyShop.choosePrizeToy();
        if (prizeToy != null) {
            toyShop.savePrizeToyToFile(prizeToy);
        }

        prizeToy.decreaseQuantity();
    }
}
