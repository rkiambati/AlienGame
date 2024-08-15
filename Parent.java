import java.io.*;
import java.util.HashMap;
import java.util.Random;

public class Parent {
    public String name;
    public int healthBar;
    public int numWeapons;
    public String weaponType;
    public int weaponDamage;
    public boolean isHit;
    public int numHits;
    public int totalDamage;

    String[] weapons = {"Knife", "Sword", "Gun", "Lasergun", "Blaster"};
    int[] weaponsDamage = {1, 2, 4, 6, 8};

    public Parent() {
        this.isHit = false;
        this.healthBar = 100;
        this.numWeapons = 0;
        this.weaponType = "";
        this.weaponDamage = 0;
        this.numHits = 0;
        this.totalDamage = 0;
    }

    public void DamageFinder() {
        System.out.println("Choosing your weapon");
        Random random = new Random();
        int randomNumber = random.nextInt(5);
        weaponType = weapons[randomNumber];
        weaponDamage = weaponsDamage[randomNumber];
        numWeapons++;
        totalDamage = weaponDamage * numWeapons;
        System.out.println("You have been given a " + weaponType + " that does " + weaponDamage + " damage per hit");
    }

    public void takeHit(Parent opponent, Parent home) {
        if (home.healthBar > 0) {
            home.isHit = true;
            home.healthBar -= opponent.totalDamage;
            System.out.println(home.name + " has been hit! The opponent has caused " + opponent.totalDamage + " damage! " + home.name + " health has been reduced to " + home.healthBar);
        } else {
            System.out.println("You do not have enough health, your opponent has won");
        }
    }

    public void Hit(Parent opponent, Parent home) {
        if (home.healthBar > 0) {
            opponent.healthBar -= home.totalDamage;
            System.out.println(home.name + " has successfully hit " + opponent.name + ". " + opponent.name + " has taken " + home.totalDamage + " damage");
            numHits++;
        }
    }

    public class Human extends Parent {
        public String[] snacks = {"Water", "Apple", "Burger", "Mushroom", "Enchanted Mushroom"};
        public int[] snackHealth = {1, 2, 3, 4, 6};
        int numberOfHands = 2;

        public Human() {
            super();
            this.name = "Human";
        }

        public void eatFood() {
            if (this.healthBar < 50 && this.numHits % 2 == 0) {
                Random rand = new Random();
                int randomNumber = rand.nextInt(5);
                System.out.println("You have eaten the following item: " + snacks[randomNumber] + " health increased by " + snackHealth[randomNumber]);
                this.healthBar += snackHealth[randomNumber];
            } else {
                System.out.println("Your health is above critical, you do not need to eat food");
            }
        }
    }

    public class Alien extends Parent {
        public String[] alienType = {"Martian", "Xenomorph", "Neomorph", "Trilobite"};
        public int[] alienHealth = {100, 120, 150, 200};
        public int[] damageMultiplier = {1, 1, 2, 3};

        public Alien() {
            super();
        }

        public void getAlienType() {
            Random rand = new Random();
            int randomNumber = rand.nextInt(4);
            this.name = alienType[randomNumber];
            System.out.println("You will be fighting the following alien: " + alienType[randomNumber] + " with a starting health of " + alienHealth[randomNumber] + " and has a damage multiplier of " + damageMultiplier[randomNumber]);
            this.healthBar = alienHealth[randomNumber];
            this.totalDamage = this.weaponDamage * damageMultiplier[randomNumber];
        }
    }

    public void Run() {
        Parent parent = new Parent();
        System.out.println("Welcome to Humans vs Aliens! You will be fighting an alien today so gear up. Sit back and relax while we fight the aliens for you, just watch");
        Parent.Human human = parent.new Human();
        Parent.Alien alien = parent.new Alien();
        System.out.println("Selecting your human presets");
        human.DamageFinder();
        System.out.println("Selecting your opponent presets");
        alien.getAlienType();
        alien.DamageFinder();

        Random rand = new Random();
        System.out.println("A toss of coin will decide who shall start");
        int cointoss = rand.nextInt(2);

        if (cointoss == 1) {
            while (human.healthBar > 1 && alien.healthBar > 1) {
                human.Hit(alien, human);
                alien.takeHit(human, alien);
                alien.Hit(human, alien);
                human.takeHit(alien, human);
                human.eatFood();
            }
        } else {
            while (human.healthBar > 1 && alien.healthBar > 1) {
                alien.Hit(human, alien);
                human.takeHit(alien, human);
                human.eatFood();
                human.Hit(alien, human);
                alien.takeHit(human, alien);
            }
        }

        if (human.healthBar > alien.healthBar) {
            System.out.println("Alien has been defeated, Human takes Victory");
        } else if (human.healthBar < alien.healthBar) {
            System.out.println("Human has been defeated, Alien Victory");
        }
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.Run();
    }
}
