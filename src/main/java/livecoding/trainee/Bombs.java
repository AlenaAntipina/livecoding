package livecoding.trainee;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Bombs {

    public static void main(String[] args) {
        int[][] test1 = new int[][]{{2, 1, 3}, {6, 1, 4}};
        int[][] test2 = new int[][]{{1, 1, 5}, {10, 10, 5}};
        int[][] test3 = new int[][]{{1, 2, 3}, {2, 3, 1}, {3, 4, 2}, {4, 5, 3}, {5, 6, 4}};
        int[][] test4 = new int[][]{{1,1,100000}, {100000,100000,1}};
        int[][] test5 = new int[][]{{2,2,3}, {7,2,2}, {3,4,1}, {4,2,3}, {9,2,1}, {7,4,1}};

        System.out.println("Test 1 = " + maximumDetonation(test1) + " answer = 2");
        System.out.println("Test 2 = " + maximumDetonation(test2) + " answer = 1");
        System.out.println("Test 3 = " + maximumDetonation(test3) + " answer = 5");
        System.out.println("Test 4 = " + maximumDetonation(test4) + " answer = 1");
        System.out.println("Test 5 = " + maximumDetonation(test5) + " answer = 6");
    }

    public static Integer maximumDetonation(int[][] bombs) {
        Map<Integer, Set<Integer>> setMap = new HashMap<>();   // key - bomb's number, value - set of detonated bombs

        for (int i = 0; i < bombs.length; i++) {
            setMap.put(i, detonatedBombs(bombs, i));     // бомбы, которые взрывает бомба с индекс i
        }

        int result = 0;
        for (int i = 0; i < bombs.length; i++) {
            setMap.put(i, countBombs(setMap, i));        // бомбы, которые взрываются по цепочке
            result = Math.max(setMap.get(i).size(), result);
//            System.out.println("bombIndex: " + i + "   |   detonated: " + setMap.get(i));
        }

        return result;
    }

    public static Set<Integer> detonatedBombs(int[][] bombs, int bombIndex) {
        Set<Integer> indexOfDetonatedBombs = new LinkedHashSet<>();

        long x1 = bombs[bombIndex][0];
        long y1 = bombs[bombIndex][1];
        long r = bombs[bombIndex][2];
        for (int i = 0; i < bombs.length; i++) {
            long x2 = bombs[i][0];
            long y2 = bombs[i][1];
            long deltaX = x1 - x2;
            long deltaY = y1 - y2;

            if (deltaX*deltaX + deltaY*deltaY <= r*r) {
                indexOfDetonatedBombs.add(i);
            }
        }

        return indexOfDetonatedBombs;
    }

    public static Set<Integer> countBombs(Map<Integer, Set<Integer>> map, int bombIndex) {
        Set<Integer> result = map.get(bombIndex);          // set with all bomb's indexes
        Set<Integer> values = new HashSet<>(result);       //  immutable set

        for (Integer value : values) {
            Set<Integer> diff = map.get(value).stream().filter(val -> !result.contains(val)).collect(Collectors.toSet());
            result.addAll(diff);
            map.put(bombIndex, result);
            if (!diff.isEmpty()) {
                result.addAll(countBombs(map, value));
            }
        }
        values.addAll(result);

        return values;
    }


    /**
     * Вам дан список бомб. Дальность действия бомбы определяется как область, где можно ощутить ее эффект.
     * Эта область имеет форму круга, в центре которого находится бомба.
     * <p>
     * Бомбы представлены двумерным целочисленным массивом с нулевым индексом,bombs где .
     * обозначают координату X и координату Y места расположения бомбы , тогда как обозначает радиус ее дальности.
     * bombs[i] = [xi, yi, ri]
     * <p>
     * Вы можете взорвать одну бомбу. Когда бомба взорвана, она взорвет все бомбы ,
     * находящиеся в радиусе ее действия. Эти бомбы в дальнейшем взорвут бомбы, находящиеся в зоне их действия.
     * <p>
     * Учитывая список bombs, верните максимальное количество бомб, которые можно взорвать, если вам
     * разрешено взорвать только одну бомбу
     * <p>
     * Ввод: бомбы = [[2,1,3],[6,1,4]]
     * Выход: 2
     * Объяснение:
     * На рисунке выше показаны позиции и дальности действия двух бомб.
     * Если мы взорвем левую бомбу, правая бомба не пострадает.
     * Но если мы взорвем нужную бомбу, взорвутся обе бомбы.
     * Таким образом, максимальное количество бомб, которые можно взорвать, равно max(1, 2) = 2.
     * <p>
     * Входные данные: бомбы = [[1,1,5],[10,10,5]]
     * Выходные данные: 1
     * Объяснение:
     * Взрыв одной бомбы не приведет к детонации другой бомбы, поэтому максимальное количество бомб, которые можно взорвать, равно 1.
     * <p>
     * Ввод: бомбы = [[1,2,3],[2,3,1],[3,4,2],[4,5,3],[5,6,4]]
     * Выход: 5
     * Объяснение:
     * Лучшей бомбой для взрыва является бомба 0, потому что:
     * - Бомба 0 взрывает бомбы 1 и 2. Красный кружок обозначает дальность действия бомбы 0.
     * - Бомба 2 взрывает бомбу 3. Синий кружок обозначает дальность действия бомбы 2.
     * - Бомба 3 взрывает бомбу 4. Зеленый кружок обозначает дальность действия бомбы 3.
     * Таким образом все 5 бомб взорваны.
     */



}
