package algorithm;

import java.util.*;

/**
 * @author pray chow
 * 输入一个字符串，由名字和逗号组成，找出“明日之星”。
 * 规则：1）票多者当选
 *       2）票数相同，根据姓名排序，字典序
 * 非法输入返回：error.0001
 * 合法输入返回：员工姓名
 * IN :
 *      Tom,Lily,Tom,Lucy,Lucy,Jack
 * OUT :
 *      Lucy
 * IN :
 *      Tom,Lily,Tom,Lucy,Lucy,Jack,Tom
 * OUT :
 *      Tom
 */
public class Huawei_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        scan.close();
        String[] employees = str.split(",");
        // check
        for (String employee : employees) {
            if (employee.charAt(0) < 'A' || employee.charAt(0) > 'Z') {
                System.out.println("error.0001");
                return;
            }
            for (int i = 1; i < employee.length(); ++i) {
                if (employee.charAt(i) < 'a' || employee.charAt(i) > 'z') {
                    System.out.println("error.0001");
                    return;
                }
            }
        }
        // cal
        Map<String, Integer> map = new HashMap<>(employees.length);
        for (String employee : employees) {
            if (map.containsKey(employee)) {
                map.put(employee, map.get(employee) + 1);
            } else {
                map.put(employee, 1);
            }
        }
        // sort
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> {
            if (o1.getValue() < o2.getValue()) {
                return 1;
            } else if (o1.getValue().equals(o2.getValue())) {
                return o1.getKey().compareTo(o2.getKey());
            } else {
                return -1;
            }
        });
        // print
        System.out.println(list.get(0).getKey());
    }
}
