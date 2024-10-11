package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Student(int id, String name, int age, String gender, String Dept, String city, int rank, List<String> contact){}
record Subject(String subName, int marks){}
record ClgStudent(String name, List<Subject> subjectList){}
record Employee(int id, String name, Double salary){}
public class RecordClass {
    public static void main(String[] args) {
            List<Student> stuList = Stream.of(new Student(1,"Rohit",30,"Male", "ME", "Mumbai", 26, Arrays.asList("9434232412","7352157812")),
                                                new Student(2,"Shyam",28,"Female", "CSE", "Delhi", 15, Arrays.asList("9434566259","7252418223")),
                                                new Student(3,"Ram",32,"Male", "ME", "Goa", 82, Arrays.asList("9434583227","7652418202")),
                                                new Student(4,"Mohit",30,"Male", "EE", "Mumbai", 29, Arrays.asList("9444586228","7354418215"))).collect(Collectors.toList());
            //groupby city in reverse order
            System.out.println(stuList.stream().filter(s-> s.city().equals("Mumbai")).sorted(Comparator.comparing(Student::name).reversed()).collect(Collectors.toUnmodifiableList()));

            //Find unique dept & contact list
            System.out.println(stuList.stream().map(Student::Dept).distinct().toList());
            System.out.println(stuList.stream().flatMap(e -> e.contact().stream()).toList());

            //List of student grouped by dept
            System.out.println(stuList.stream().collect(Collectors.groupingBy(Student::Dept)));
            //List of Student names in each dept
            System.out.println(stuList.stream().collect(Collectors.groupingBy(Student::Dept, Collectors.mapping(s -> s.name(), Collectors.toList()))));

            //Dept having Highest no. of student  --------------    (Map<Dept,studentCount> -- get Max studentCount value among all dept)
            System.out.println(stuList.stream().collect(Collectors.groupingBy(s -> s.Dept(), Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).get());
                                                                // Map.Entry.comparingByValue() --> Compare a map by its value
            //Highest rank in each dept and all dept -------------  (Map<Dept, DeptTopper> --- get Max Rank in each dept)
            Map<String,Optional<Student>> highestRank = stuList.stream().collect(Collectors.groupingBy(Student::Dept,Collectors.maxBy(Comparator.comparingInt(Student::rank))));
            Optional<Student> a1 = highestRank.get("EE");
            a1.ifPresent(e -> System.out.println("Student = " + e));

            Optional<Integer> highest = stuList.stream().map(e -> e.rank()).max(Integer::compare);  //Handle optional Class
            highest.ifPresent(System.out::println);

            List<Employee> employeeList = Arrays.asList(new Employee(1, "Animesh", 6000.0),
                                                        new Employee(2, "Ramesh", 5000.0),
                                                        new Employee(3, "Suresh", 7000.0),
                                                        new Employee(4, "Animesh", 2000.0));
            System.out.println(employeeList.stream().collect(Collectors.toMap(Employee::name, Employee::salary, (oldSal, newSal) -> oldSal.doubleValue() + newSal.doubleValue())));    //Merge duplicate value
            System.out.println(employeeList.stream().collect(Collectors.toMap(Employee::name, e-> new ArrayList<>(Arrays.asList(e.salary())), (oldSalList, newSalList) -> {
                                                                                                                                                                            oldSalList.addAll(newSalList);
                                                                                                                                                                            return oldSalList;  })));       // Merge values in form of List

            System.out.println(employeeList.stream().collect(Collectors.groupingBy(Employee::name, Collectors.averagingDouble(Employee::salary))));     //Get Avg of duplicate

            List<ClgStudent> clgStuList = Stream.of(new ClgStudent("Ram",Arrays.asList(new Subject("Arts", 79), new Subject("Science", 56))),
                new ClgStudent("Rahim",Arrays.asList(new Subject("Commerce", 56), new Subject("Arts", 23))),
                new ClgStudent("Shyam",Arrays.asList(new Subject("Physiology", 28), new Subject("Arts",72))),
                new ClgStudent("Sahim",Arrays.asList(new Subject("Commerce", 83), new Subject("Science", 91)))).toList();

            List<ClgStudent> abc = clgStuList.stream().filter(e -> e.subjectList().stream().anyMatch(s -> s.subName().equals("Commerce"))).toList();
            Optional<String> top = abc.stream().max(Comparator.comparingInt(e -> e.subjectList().stream().filter(s -> s.subName().equals("Commerce")).findFirst().get().marks())).map(ClgStudent::name);

            //clgStuList.stream().collect(Collectors.groupingBy(ClgStudent::subjectList, Collectors.maxBy(Comparator.comparing(e -> e.subjectList().stream().filter(s -> s.subName().equals("Commerce")).map(a -> a.marks(),Collectors.toList())))));
            /*System.out.println("args = " + clgStuList.stream()
                .max(Comparator.comparingInt(clgStudent -> clgStudent.subjectList().stream()
                        .filter(subj -> subj.subName().equalsIgnoreCase("Commerce")).findFirst().get()
                        .marks())).map(ClgStudent::name).get()); */

            top.ifPresent(s -> System.out.println("Topper = " + s));

            int[] num = {1,8,6,9,3,7,21,41,37,12};  //rotate a list from right to left
            int k =4;
            rotate(num, k);
            System.out.print(Arrays.toString(num));  //Print whole array in string
    }

    public static void rotate(int[] num, int k){
        k=k%num.length;                            //Window must not overflow than length [  if(k<num.length)  ]
        reverse(num, 0, num.length-1);   //reverse whole ist
        reverse(num, 0, k-1);           //reverse the result list upto length of window
        reverse(num, k,num.length-1);        //reverse the result list for remain length after window
    }

    public static void reverse(int[] num, int start, int end){
        while(start<end){
            int temp = num[start];
            num[start] = num[end];
            num[end] = temp;
            start ++;
            end --;
        }
    }
}
