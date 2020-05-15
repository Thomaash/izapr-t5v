package izapr.t5v.addressDatabase;

import java.time.Instant;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

// Poznámka: Neni ideální mit tolik parametrů u metod, je to dost náchylný na
// chyby (obvlášť mezi sousedícíma parametrama stejnýho datovýho typu). Nicméně
// Java neposkytuje nic slušnýho pro snadný řešení tohodle problému. Ideální by
// bylo vytvořit buď několik tříd pro logický celky, kde by konstruktor každý
// třídy měl jenom pár parametrů (hodí se pro statičtější věci, jako třeba
// metoda AddressDatabase.add) nebo tovární třídu, která by se naplnila pomocí
// volání několika metod (lepší pro dynamičtější, jako třeba
// AddressDatabase.query).
public final class AddressDatabase {

    private final List<Address> addresses = new LinkedList<>();

    // Poznámka: Calendar je mutabilní, což porušuje jeden z požadavků zadání.
    // Neměl sem vůbec v plánu testovat vaši znalost toho bordelu, kterýmu Java
    // řiká třídy pro práci s časem. Sám sem si to neuvědomil, když sem to
    // zadával a ani když sem to opravoval. Nikdo kvůli tomu o body nepřišel.
    public void add(
            final Calendar acquisitionDateCalendar,
            final String ownerName,
            final String country,
            final String town,
            final String district,
            final String street,
            final int houseNumber
    ) {
        this.add(
                acquisitionDateCalendar.toInstant(),
                ownerName,
                country,
                town,
                district,
                street,
                houseNumber);
    }

    public void add(
            final Instant acquisitionDate,
            final String ownerName,
            final String country,
            final String town,
            final String district,
            final String street,
            final int houseNumber
    ) {
        this.addresses.add(new Address(
                Instant.now(),
                acquisitionDate,
                ownerName,
                country,
                town,
                district,
                street,
                houseNumber));
    }

    public List<Address> query(
            final Instant insertionDate,
            final Instant acquisitionDate,
            final String ownerName,
            final String country,
            final String town,
            final String district,
            final String street,
            final Integer houseNumber
    ) {
        final List<Address> results = new LinkedList<>();

        for (final Address address : this.addresses) {
            if ((insertionDate == null || insertionDate.equals(address.insertionDate))
                    && (acquisitionDate == null || acquisitionDate.equals(address.acquisitionDate))
                    && (ownerName == null || ownerName.equals(address.ownerName))
                    && (country == null || country.equals(address.country))
                    && (town == null || town.equals(address.town))
                    && (district == null || district.equals(address.district))
                    && (street == null || street.equals(address.street))
                    && (houseNumber == null || houseNumber == address.houseNumber)) {
                results.add(address);
            }
        }

        return results;
    }

    public static final class Address {

        private final Instant insertionDate;
        private final Instant acquisitionDate;
        private final String ownerName;
        private final String country;
        private final String town;
        private final String district;
        private final String street;
        private final int houseNumber;

        private Address(
                final Instant insertionDate,
                final Instant acquisitionDate,
                final String ownerName,
                final String country,
                final String town,
                final String district,
                final String street,
                final int houseNumber
        ) {
            this.throwIfAnyInvalid(
                    insertionDate,
                    acquisitionDate,
                    ownerName,
                    country,
                    town,
                    district,
                    street,
                    houseNumber);

            this.insertionDate = insertionDate;
            this.acquisitionDate = acquisitionDate;
            this.ownerName = ownerName;
            this.country = country;
            this.town = town;
            this.district = district;
            this.street = street;
            this.houseNumber = houseNumber;
        }

        private void throwIfAnyInvalid(
                final Instant insertionDate,
                final Instant acquisitionDate,
                final String ownerName,
                final String country,
                final String town,
                final String district,
                final String street,
                final int houseNumber
        ) {
            if (insertionDate == null) {
                throw new IllegalArgumentException("Insertion date can't be omitted.");
            }

            if (acquisitionDate == null) {
                throw new IllegalArgumentException("Acquisition date can't be omitted.");
            }
            if (insertionDate.isBefore(acquisitionDate)) {
                throw new IllegalArgumentException("Acquisition date can't be in the future.");
            }

            if (ownerName == null || ownerName.isEmpty()) {
                throw new IllegalArgumentException("Owner name can't be empty.");
            }

            if (country == null || country.isEmpty()) {
                throw new IllegalArgumentException("Country can't be empty.");
            }

            if (town == null || town.isEmpty()) {
                throw new IllegalArgumentException("Town can't be empty.");
            }

            if (district != null && district.isEmpty()) {
                throw new IllegalArgumentException("District can be omitted (null) but can't be empty if present.");
            }

            if (street == null || street.isEmpty()) {
                throw new IllegalArgumentException("Street can't be empty.");
            }

            if (houseNumber < 0) {
                throw new IllegalArgumentException("House number can't be negative.");
            }
        }

        public Instant getInsertionDate() {
            return this.insertionDate;
        }

        public Instant getAcquisitionDate() {
            return this.acquisitionDate;
        }

        public String getOwnerName() {
            return this.ownerName;
        }

        public String getCountry() {
            return this.country;
        }

        public String getTown() {
            return this.town;
        }

        public String getDistrict() {
            return this.district;
        }

        public String getStreet() {
            return this.street;
        }

        public int getHouseNumber() {
            return this.houseNumber;
        }

        @Override
        public String toString() {
            return "Address: "
                    + this.insertionDate + " "
                    + this.acquisitionDate + " "
                    + this.ownerName + ", "
                    + this.country + ", "
                    + this.town + ", "
                    + (this.district == null ? "" : this.district + ", ")
                    + this.street + " "
                    + this.houseNumber;
        }

    }

}
