package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> test = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        //2.Добавлен метод toString в UserMealWithExceed для тестирования
        for (UserMealWithExceed meal : test) {
            System.out.println(meal);
        }
    }


    //1.Добавлен словарь: 
    public static HashMap<LocalDate,Integer> map = new HashMap<>();

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

          return  mealList.stream().
                  peek(x -> map.merge(x.getDate(), x.getCalories(), (a, b) -> (a + b))).
                  filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime)).
                  collect(Collectors.toList()).
                  stream().
                  map(x -> new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(), caloriesPerDay < map.get(x.getDate()))).
                  collect(Collectors.toList());
    }
}
