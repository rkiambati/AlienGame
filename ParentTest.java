import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParentTest {
    private Parent parent;
    private Parent.Human human;
    private Parent.Alien alien;

    @Before
    public void setUp() {
        parent = new Parent();
        human = parent.new Human();
        alien = parent.new Alien();
    }

    @Test
    public void testInitialValues() {
        assertEquals(100, parent.healthBar);
        assertEquals(0, parent.numWeapons);
        assertEquals("", parent.weaponType);
        assertEquals(0, parent.weaponDamage);
        assertEquals(0, parent.numHits);
        assertFalse(parent.isHit);
    }

    @Test
    public void testDamageFinder() {
        human.DamageFinder();
        assertTrue(human.weaponType.length() > 0);
        assertTrue(human.weaponDamage > 0);
        assertEquals(1, human.numWeapons);
    }

    @Test
    public void testGetAlienType() {
        alien.getAlienType();

        assertTrue(alien.name.equals("Martian") || alien.name.equals("Xenomorph") ||
                   alien.name.equals("Neomorph") || alien.name.equals("Trilobite"));

        assertTrue(alien.healthBar == 100 || alien.healthBar == 120 || 
                   alien.healthBar == 150 || alien.healthBar == 200);

        assertTrue(alien.totalDamage >= alien.weaponDamage * alien.numWeapons &&
                   alien.totalDamage <= alien.weaponDamage * alien.numWeapons * 3);
    }

    @Test
    public void testTakeHit() {
        alien.getAlienType();
        alien.DamageFinder();
        int initialHealth = human.healthBar;
        human.takeHit(alien, human);
        assertTrue(human.isHit);
        assertTrue(human.healthBar < initialHealth);
    }

    @Test
    public void testHit() {
        human.DamageFinder();
        alien.getAlienType();
        int initialHealth = alien.healthBar;
        human.Hit(alien, human);
        assertTrue(alien.healthBar < initialHealth);
        assertEquals(1, human.numHits);
    }

    @Test
    public void testEatFood() {
        human.healthBar = 40;
        human.numHits = 2;
        human.eatFood();
        assertTrue(human.healthBar > 40);
    }

    @Test
    public void testRun() {
        Random rand = new Random();
        human.DamageFinder();
        alien.getAlienType();
        alien.DamageFinder();

        int initialHumanHealth = human.healthBar;
        int initialAlienHealth = alien.healthBar;

        while (human.healthBar > 1 && alien.healthBar > 1) {
            if (rand.nextBoolean()) {
                human.Hit(alien, human);
                alien.takeHit(human, alien);
                alien.Hit(human, alien);
                human.takeHit(alien, human);
            } else {
                alien.Hit(human, alien);
                human.takeHit(alien, human);
                human.Hit(alien, human);
                alien.takeHit(human, alien);
            }
            human.eatFood();
        }

        assertTrue(human.healthBar <= initialHumanHealth);
        assertTrue(alien.healthBar <= initialAlienHealth);
    }
}
