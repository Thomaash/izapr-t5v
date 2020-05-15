package izapr.t5v;

import java.time.Instant;

import izapr.t5v.addressDatabase.AddressDatabase;

public class Main {

    public static void main(String[] args) {
        final AddressDatabase db = new AddressDatabase();

        db.add(
                Instant.now(),
                "Saoirse",
                "Ireland",
                "Dublin",
                null,
                "Elm Mount Close",
                88
        );
        db.add(
                Instant.now(),
                "Helmut",
                "Germany",
                "Berlin",
                "Pankow",
                "Kalvinistenweg",
                88
        );

        System.out.println("House number 88:");
        for (final AddressDatabase.Address address : db.query(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                88
        )) {
            System.out.println(address);
        }
        System.out.println();

        System.out.println("Berlin in Germany:");
        for (final AddressDatabase.Address address : db.query(
                null,
                null,
                null,
                "Germany",
                "Berlin",
                null,
                null,
                null
        )) {
            System.out.println(address);
        }
        System.out.println();

        System.out.println("Berlin in Ireland:");
        for (final AddressDatabase.Address address : db.query(
                null,
                null,
                null,
                "Ireland",
                "Berlin",
                null,
                null,
                null
        )) {
            System.out.println(address);
        }
        System.out.println();
    }

}
