package com.upgrade.tests;

import com.github.javafaker.Faker;
import com.upgrade.pojos.Borrower;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BorrowerDetails {

    private static final Faker faker = new Faker(new Locale("en-US"));
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public static Borrower getBorrowerPositiveCase(){
        return getBasicTestBorrower()
                .additionalIncome(generateRandomNumberFromRange(5000, 10000))
                .yearlyIncome(generateRandomNumberFromRange(150000, 200000))
                .desiredLoanAmount(generateRandomNumberFromRange(5000, 10000))
                .build();
    }

    public static Borrower getBorrowerNegativeCase(){
        return getBasicTestBorrower()
                .additionalIncome(generateRandomNumberFromRange(100, 500))
                .yearlyIncome(generateRandomNumberFromRange(100, 1000))
                .desiredLoanAmount(generateRandomNumberFromRange(5000, 10000))
                .build();
    }

    private static Borrower.BorrowerBuilder getBasicTestBorrower() {
        return Borrower.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .dob(simpleDateFormat.format(faker.date().birthday()))
                .email(String.format("coding.%s@upgrade-challenge.com", generateRandomNumberFromRange(15000000, 20000000)))
                .password("System@987")
                .city(faker.address().city())
                .zipCode(faker.address().zipCode())
                .street(faker.address().streetAddress())
                .state(faker.address().state())
                .loanPurpose("Home Improvement");


    }

    private static BigDecimal generateRandomNumberFromRange(int min, int max) {
        return BigDecimal.valueOf(Math.random() * (max - min + 1) + min).setScale(0, RoundingMode.DOWN);
    }
}
