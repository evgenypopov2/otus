package ru.otus;


public class MyTestClass {

    private String normalString = "Normal string";
    private String nullPointerString = null;
    private int zero = 0;
    private int[] intArray = new int[2];

    @Before
    public void testBeforeNormal() {
        System.out.println("testBeforeNormal");
        if (Math.random() < 0.5 && normalString.equals(nullPointerString)) {
            System.out.println("It's impossible");
        }
    }
    @Before
    public void testBeforeWithException() {
        System.out.println("testBeforeWithException");
        if (Math.random() < 0.5 && nullPointerString.equals("some string")) {
            System.out.println("This string will never printed until nullPointerString != null");
        }
    }
    @Test
    public void testTestNormal() {
        System.out.println("testTestNormal");
    }
    @Test
    public void testTestWithException() {
        System.out.println("testTestWithException");
        if (Math.random() < 0.5) {
            System.out.println("division by zero: " + 20 / zero);
        }
    }
    @After
    public void testAfterNormal() {
        System.out.println("testAfterNormal");
        intArray[1] = 11;
    }
    @After
    public void testAfterWithException() {
        System.out.println("testAfterWithException");
        if (Math.random() < 0.5) {
            intArray[4] = 44;   // ArrayOutOfBounds
        }
    }
}
