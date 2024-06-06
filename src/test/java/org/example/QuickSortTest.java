package org.example;

import org.example.homework1.ArrayCollection;
import org.example.homework1.QuickSort;
import org.example.homework1.User;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void shouldSortViaComparable() {
        ArrayCollection<User> users= new ArrayCollection<>();
        users.add(new User("Danya",26,"st.Kira,34"));
        users.add(new User("Anna",21,"st.Mira, 32"));
        users.add(new User("Ilya",34,"st.Lenina, 23"));
        users.add(new User("Alla",24,"st.Mira, 23"));
        String firstName="Alla";
        QuickSort.sort(users);
        System.out.println(users);
        assertEquals(users.get(0).getName(), firstName);
    }

    @Test
    void shouldSortViaComparator() {
        ArrayCollection<User> users= new ArrayCollection<>();
        users.add(new User("Danya",26,"st.Kira,34"));
        users.add(new User("Anna",21,"st.Mira, 32"));
        users.add(new User("Ilya",34,"st.Lenina, 23"));
        users.add(new User("Alla",24,"st.Mira, 23"));
        Integer age=21;
        QuickSort.sort(users, Comparator.comparing(User::getAge));
        assertEquals(users.get(0).getAge(), age);
    }
}