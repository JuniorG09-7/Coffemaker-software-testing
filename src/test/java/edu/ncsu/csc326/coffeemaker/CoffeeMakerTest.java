package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

public class CoffeeMakerTest {

	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;
	private Recipe recipe6;

	private Recipe [] stubRecipies;
	private CoffeeMaker coffeeMaker;
	private RecipeBook recipeBookStub;

	private Inventory freshInventory() {
		Inventory i = new Inventory();
		i.setCoffee(15);
		i.setMilk(15);
		i.setSugar(15);
		i.setChocolate(15);
		return i;
	}

	@Before
	public void setUp() throws RecipeException {
		recipeBookStub = mock(RecipeBook.class);

		Inventory tmp = new Inventory();
		tmp.setCoffee(15);
		tmp.setMilk(15);
		tmp.setSugar(15);
		tmp.setChocolate(15);

		coffeeMaker = new CoffeeMaker(recipeBookStub, tmp);

		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0"); recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");     recipe1.setAmtSugar("1");
		recipe1.setPrice("50");

		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20"); recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");       recipe2.setAmtSugar("1");
		recipe2.setPrice("75");

		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0"); recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");      recipe3.setAmtSugar("1");
		recipe3.setPrice("100");

		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4"); recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");      recipe4.setAmtSugar("1");
		recipe4.setPrice("65");

		recipe5 = new Recipe();
		recipe5.setName("Super Hot Chocolate");
		recipe5.setAmtChocolate("6"); recipe5.setAmtCoffee("60");
		recipe5.setAmtMilk("1");      recipe5.setAmtSugar("1");
		recipe5.setPrice("100");

		recipe6 = new Recipe();
		recipe6.setName("Super Hot Chocolate");
		recipe6.setAmtChocolate("6"); recipe6.setAmtCoffee("0");
		recipe6.setAmtMilk("1");      recipe6.setAmtSugar("40");
		recipe6.setPrice("100");

		stubRecipies = new Recipe[]{recipe1, recipe2, recipe3};
	}

	@Test
	public void testMakeCoffee() {
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertTrue(true);
	}

	// S2: overpay -> correct change
	@Test
	public void testPurchaseBeverage1(){
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(50, coffeeMaker.makeCoffee(0, 100));
	}

	// E2: not enough money -> return money
	@Test
	public void testPurchaseBeverage2(){
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(5, coffeeMaker.makeCoffee(0, 5));
	}

	// E1: not enough sugar -> return money
	@Test
	public void testPurchaseBeverage3(){
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1, recipe6, null});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(100, coffeeMaker.makeCoffee(1, 100));
	}

	// E3: null slot -> return money
	@Test
	public void testPurchaseBeverage4(){
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1, recipe6, null});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(100, coffeeMaker.makeCoffee(2, 100));
	}

	// E1: not enough coffee -> return money
	@Test
	public void testPurchaseBeverage8(){
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1, recipe5, null});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(100, coffeeMaker.makeCoffee(1, 100));
	}

	// S2: exact payment -> zero change (kills <= -> < mutant)
	@Test
	public void testPurchaseBeverageExact(){
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(0, coffeeMaker.makeCoffee(0, 50));
	}

	// KILLS the escaping mutant:
	// Mutant uses getRecipes()[0].getPrice() on line 104 instead of [recipeToPurchase]
	// slot1=recipe4(price=65), slot0=recipe1(price=50), paid=60
	// CORRECT: 65 > 60 -> E2 -> return 60
	// MUTANT:  50 <= 60 -> enters purchase -> returns something != 60
	@Test
	public void testPurchaseBeverageSlot1NotEnoughMoney(){
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1, recipe4, recipe3, null});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		// recipe4 at slot1 costs 65, paying only 60 -> should return 60
		// mutant using slot0 price (50): 50 <= 60 -> would try to purchase
		assertEquals(60, coffeeMaker.makeCoffee(1, 60));
	}

	// Additional slot1 test: verify exact change when buying slot1 successfully
	@Test
	public void testPurchaseBeverageSlot1Success(){
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1, recipe4, recipe3, null});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		// recipe4 at slot1: price=65, pay=80 -> change=15
		// mutant using slot0 price(50): change = 80-50=30 or 80-65=15 depending on line106
		assertEquals(15, coffeeMaker.makeCoffee(1, 80));
	}

	// slot2 success: getters on recipe3, never on recipe1/recipe6
	@Test
	public void testVerifyCall(){
		Recipe recipe1Spy = spy(recipe1);
		Recipe recipe6Spy = spy(recipe6);
		Recipe recipe3Spy = spy(recipe3);
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1Spy, recipe6Spy, recipe3Spy});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(0, coffeeMaker.makeCoffee(2, 100));
		verify(recipe3Spy, atLeastOnce()).getAmtChocolate();
		verify(recipe3Spy, atLeastOnce()).getAmtMilk();
		verify(recipe3Spy, atLeastOnce()).getAmtSugar();
		verify(recipe3Spy, atLeastOnce()).getAmtCoffee();
		verify(recipe3Spy, atLeastOnce()).getPrice();
		verify(recipe1Spy, times(0)).getAmtChocolate();
		verify(recipe1Spy, times(0)).getAmtMilk();
		verify(recipe1Spy, times(0)).getAmtSugar();
		verify(recipe1Spy, times(0)).getAmtCoffee();
		verify(recipe1Spy, times(0)).getPrice();
		verify(recipe6Spy, times(0)).getAmtChocolate();
		verify(recipe6Spy, times(0)).getAmtMilk();
		verify(recipe6Spy, times(0)).getAmtSugar();
		verify(recipe6Spy, times(0)).getAmtCoffee();
		verify(recipe6Spy, times(0)).getPrice();
	}

	// slot1 E1 (sugar=40>15): getters exactly once on recipe6, 0 on others
	@Test
	public void testVerifyCall1(){
		Recipe recipe1Spy = spy(recipe1);
		Recipe recipe6Spy = spy(recipe6);
		Recipe recipe3Spy = spy(recipe3);
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1Spy, recipe6Spy, recipe3Spy});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(100, coffeeMaker.makeCoffee(1, 100));
		verify(recipe6Spy, times(1)).getAmtChocolate();
		verify(recipe6Spy, times(1)).getAmtMilk();
		verify(recipe6Spy, times(1)).getAmtSugar();
		verify(recipe6Spy, times(1)).getAmtCoffee();
		verify(recipe6Spy, times(1)).getPrice();
		verify(recipe1Spy, times(0)).getAmtChocolate();
		verify(recipe1Spy, times(0)).getAmtMilk();
		verify(recipe1Spy, times(0)).getAmtSugar();
		verify(recipe1Spy, times(0)).getAmtCoffee();
		verify(recipe1Spy, times(0)).getPrice();
		verify(recipe3Spy, times(0)).getAmtChocolate();
		verify(recipe3Spy, times(0)).getAmtMilk();
		verify(recipe3Spy, times(0)).getAmtSugar();
		verify(recipe3Spy, times(0)).getAmtCoffee();
		verify(recipe3Spy, times(0)).getPrice();
	}

	// slot0 success: getters on recipe1, never on recipe6/recipe3
	@Test
	public void testVerifyCall2(){
		Recipe recipe1Spy = spy(recipe1);
		Recipe recipe6Spy = spy(recipe6);
		Recipe recipe3Spy = spy(recipe3);
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{recipe1Spy, recipe6Spy, recipe3Spy});
		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());
		assertEquals(50, coffeeMaker.makeCoffee(0, 100));
		verify(recipe1Spy, atLeastOnce()).getAmtChocolate();
		verify(recipe1Spy, atLeastOnce()).getAmtMilk();
		verify(recipe1Spy, atLeastOnce()).getAmtSugar();
		verify(recipe1Spy, atLeastOnce()).getAmtCoffee();
		verify(recipe1Spy, atLeastOnce()).getPrice();
		verify(recipe3Spy, times(0)).getAmtChocolate();
		verify(recipe3Spy, times(0)).getAmtMilk();
		verify(recipe3Spy, times(0)).getAmtSugar();
		verify(recipe3Spy, times(0)).getAmtCoffee();
		verify(recipe3Spy, times(0)).getPrice();
		verify(recipe6Spy, times(0)).getAmtChocolate();
		verify(recipe6Spy, times(0)).getAmtMilk();
		verify(recipe6Spy, times(0)).getAmtSugar();
		verify(recipe6Spy, times(0)).getAmtCoffee();
		verify(recipe6Spy, times(0)).getPrice();
	}

	@Test
	public void testPurchaseBeverage5(){
		Recipe[] recipes = new Recipe[]{recipe1, recipe6, recipe3};

		when(recipeBookStub.getRecipes()).thenReturn(recipes);

		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());

		coffeeMaker.makeCoffee(2, 100);

		verify(recipes[2], atLeastOnce()).getAmtCoffee();
		verify(recipes[2], atLeastOnce()).getAmtMilk();
		verify(recipes[2], atLeastOnce()).getAmtSugar();
		verify(recipes[2], atLeastOnce()).getAmtChocolate();
		verify(recipes[2], atLeastOnce()).getPrice();
	}

	@Test
	public void testPurchaseBeverage6(){
		Recipe[] recipes = new Recipe[]{recipe1, recipe6, recipe3};

		when(recipeBookStub.getRecipes()).thenReturn(recipes);

		coffeeMaker = new CoffeeMaker(recipeBookStub, freshInventory());

		coffeeMaker.makeCoffee(2, 100);

		verify(recipes[2], atLeastOnce()).getAmtCoffee();
		verify(recipes[2], atLeastOnce()).getAmtMilk();
		verify(recipes[2], atLeastOnce()).getAmtSugar();
		verify(recipes[2], atLeastOnce()).getAmtChocolate();
		verify(recipes[2], atLeastOnce()).getPrice();
	}

}
