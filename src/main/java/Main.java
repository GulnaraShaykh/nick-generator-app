import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    // Счётчики для каждой длины слова
    private static final AtomicInteger beautifulWordsLength3 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsLength4 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();

        // Генерация 100000 случайных слов
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Создание и запуск потоков для проверки каждого критерия "красоты" слова
        Thread palindromeThread = new Thread(new PalindromeChecker(texts));
        Thread sameLettersThread = new Thread(new SameLettersChecker(texts));
        Thread increasingOrderThread = new Thread(new IncreasingOrderChecker(texts));

        palindromeThread.start();
        sameLettersThread.start();
        increasingOrderThread.start();

        // Ожидание завершения всех потоков
        try {
            palindromeThread.join();
            sameLettersThread.join();
            increasingOrderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод результатов
        System.out.println("Красивых слов с длиной 3: " + beautifulWordsLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsLength5.get() + " шт");
    }

    // Генерация случайного слова заданной длины
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // Поток для проверки красоты слова по критерию "палиндром"
    public static class PalindromeChecker implements Runnable {
        private final String[] texts;

        public PalindromeChecker(String[] texts) {
            this.texts = texts;
        }

        @Override
        public void run() {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    switch (text.length()) {
                        case 3:
                            beautifulWordsLength3.incrementAndGet();
                            break;
                        case 4:
                            beautifulWordsLength4.incrementAndGet();
                            break;
                        case 5:
                            beautifulWordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        }

        private boolean isPalindrome(String text) {
            int left = 0;
            int right = text.length() - 1;
            while (left < right) {
                if (text.charAt(left++) != text.charAt(right--)) {
                    return false;
                }
            }
            return true;
        }
    }

    // Поток для проверки красоты слова по критерию "состоит из одной буквы"
    public static class SameLettersChecker implements Runnable {
        private final String[] texts;

        public SameLettersChecker(String[] texts) {
            this.texts = texts;
        }

        @Override
        public void run() {
            for (String text : texts) {
                if (isSameLetters(text)) {
                    switch (text.length()) {
                        case 3:
                            beautifulWordsLength3.incrementAndGet();
                            break;
                        case 4:
                            beautifulWordsLength4.incrementAndGet();
                            break;
                        case 5:
                            beautifulWordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        }

        private boolean isSameLetters(String text) {
            char firstChar = text.charAt(0);
            for (int i = 1; i < text.length(); i++) {
                if (text.charAt(i) != firstChar) {
                    return false;
                }
            }
            return true;
        }
    }

    // Поток для проверки красоты слова по критерию "буквы идут по возрастанию"
    public static class IncreasingOrderChecker implements Runnable {
        private final String[] texts;

        public IncreasingOrderChecker(String[] texts) {
            this.texts = texts;
        }

        @Override
        public void run() {
            for (String text : texts) {
                if (isIncreasingOrder(text)) {
                    switch (text.length()) {
                        case 3:
                            beautifulWordsLength3.incrementAndGet();
                            break;
                        case 4:
                            beautifulWordsLength4.incrementAndGet();
                            break;
                        case 5:
                            beautifulWordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        }

        private boolean isIncreasingOrder(String text) {
            for (int i = 1; i < text.length(); i++) {
                if (text.charAt(i) < text.charAt(i - 1)) {
                    return false;
                }
            }
            return true;
        }
    }
}
