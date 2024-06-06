package org.example;

import org.example.homework1.ArrayCollection;
import org.example.homework1.User;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayCollectionTest {
    private final ArrayCollection<User> users= new ArrayCollection<>();

    @Test
    void shouldAddOneObject() {
        User user = new User("Danya",26,"st.Kira,34");
        users.add(user);
        assertEquals(1, (int) users.length());
        assertTrue(users.contains(user));
    }

    @Test
    void shouldAddOneObjectViaIndex() {
        users.add(new User("Danya",26,"st.Kira,34"));
        User user = new User("Ekaterina",45,"st.Lenina, 23");
        users.add(user,0);
        assertEquals(2, (int) users.length());
        assertTrue(users.contains(user));
        assertEquals(0, users.get(0).compareTo(user));
    }
    @Test
    void shouldAddOneObjectViaIndexTrowsException() {
        users.add(new User("Danya",26,"st.Kira,34"));
        User user = new User("Ekaterina",45,"st.Lenina, 23");
        assertThrows(ArrayIndexOutOfBoundsException.class,()->users.add(user,12));
    }
    @Test
    void shouldAddOtherArrayCollection() {
        ArrayCollection<User>  extraUsers=new ArrayCollection<>();
        extraUsers.add(new User("Julia",19,"st.Mira,11"));
        extraUsers.add(new User("Alla",24,"st.Mira, 23"));
        users.add(extraUsers);
        assertEquals(2, (int) users.length());
    }

    @Test
    void shouldSetOneObjectViaIndex() {
        users.add(new User("Danya",26,"st.Kira,34"));
        users.add(new User("Ekaterina",45,"st.Lenina, 23"));
        User user = new User("Alexander",31,"st.Victory, 77");
        users.set(1,user);
        assertEquals(0, users.get(1).compareTo(user));
        assertEquals(2, (int) users.length());
    }
    @Test
    void shouldSetOneObjectViaIndexTrowsException() {
        users.add(new User("Danya",26,"st.Kira,34"));
        users.add(new User("Ekaterina",45,"st.Lenina, 23"));
        User user = new User("Alexander",31,"st.Victory, 77");
        assertThrows(ArrayIndexOutOfBoundsException.class,()->users.set(12,user));
    }

    @Test
    void shouldGetLength() {
        users.add(new User("Danya",26,"st.Kira,34"));
        users.add(new User("Ekaterina",45,"st.Lenina, 23"));
        assertEquals(2, (int) users.length());
    }

    @Test
    void shouldRemoveObject() {
        users.add(new User("Danya",26,"st.Kira,34"));
        User user = new User("Alexander",31,"st.Victory, 77");
        users.add(user);
        users.remove(user);
        assertFalse(users.contains(user));
    }
    @Test
    void shouldRemoveObjectTrowsException() {
        users.add(new User("Danya",26,"st.Kira,34"));
        User user = new User("Alexander",31,"st.Victory, 77");
        assertThrows(NoSuchElementException.class,()->users.remove(user));
    }
    @Test
    void shouldRemoveObjectViaIndex() {
        users.add(new User("Danya",26,"st.Kira,34"));
        User user = new User("Alexander",31,"st.Victory, 77");
        users.add(user);
        users.remove(1);
        assertFalse(users.contains(user));
    }
    @Test
    void shouldRemoveObjectViaIndexThrowsException() {
        User user = new User("Danya",20,"st.Victory, 32");
        users.add(user);
        assertThrows(ArrayIndexOutOfBoundsException.class,()->users.remove(12));
    }

    @Test
    void shouldGetOneObjectViaIndex() {
        User user = new User("Alexander",31,"st.Victory, 77");
        users.add(user);
        assertEquals(0, users.get(0).compareTo(user));
    }
    @Test
    void shouldGetOneObjectViaIndexTrowsException() {
        User user = new User("Danya", 26, "st.Kira,34");
        users.add(user);
        assertThrows(ArrayIndexOutOfBoundsException.class,()->users.get(2));
    }

    @Test
    void shouldContainsObject() {
        User user =new User("Alla",24,"st.Mira, 23");
        users.add(user);
        assertTrue(users.contains(user));
        assertFalse(users.contains(new User("Angelina",12,"ul. Kira,12")));
    }

    @Test
    void shouldReturnArray() {
        User user = new User("Danya", 26, "st.Kira,34");
        users.add(user);
        Object[] arrayUsers=users.toArray();
        assertEquals(0, users.get(0).compareTo((User) arrayUsers[0]));
    }

    @Test
    void shouldClearArrayCollection() {
        users.add(new User("Julia",19,"st.Mira,11"));
        users.add(new User("Alla",24,"st.Mira, 23"));
        users.clear();
        assertEquals(0, (int) users.length());
    }
}