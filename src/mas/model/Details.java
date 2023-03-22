package mas.model;

import java.io.Serializable;

public class Details implements Serializable {
    // Address details
    private String city, street, country, postalCode;

    // Banking details
    private String bankName, accountNumber;

    public Details(String city, String street, String country, String postalCode, String bankName, String accountNumber) {
        setCity(city);
        setStreet(street);
        setCountry(country);
        setPostalCode(postalCode);
        setBankName(bankName);
        setAccountNumber(accountNumber);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.isBlank()) throw new IllegalArgumentException("City is required");

        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.isBlank()) throw new IllegalArgumentException("Street is required");

        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null || country.isBlank()) throw new IllegalArgumentException("Country is required");

        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        // I assume any format of the postal code is viable for simplicity
        if (postalCode == null || postalCode.isBlank()) throw new IllegalArgumentException("Postal code is required");

        if (!postalCode.matches("\\d{2}-\\d{3}")) throw new IllegalArgumentException
                ("Postal code has to be in a format like XX-XXX where X is a numerical value");

        this.postalCode = postalCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        if (bankName == null || bankName.isBlank()) throw new IllegalArgumentException("Bank name is required");

        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Account number is required");

        // the regex matches one or more digits, \d+ is equal to [0-9], plus meaning however many numbers after that
        if (!accountNumber.matches("\\d+")) throw new IllegalArgumentException("Account number must consist of numerical values only");

        this.accountNumber = accountNumber;
    }
}
